package com.oyxh.map.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oyxh.map.common.annotation.GeometryDeserializer;
import com.oyxh.map.common.annotation.LayerItemDeserializer;
import com.oyxh.map.common.annotation.Log;
import com.oyxh.map.common.utils.CoodinateConvertor;
import com.oyxh.map.common.utils.GsonUtil;
import com.oyxh.map.common.utils.Point;
import com.oyxh.map.common.utils.R;
import com.oyxh.map.domain.GeometryDO;
import com.oyxh.map.domain.LayerDO;
import com.oyxh.map.domain.UserDO;
import com.oyxh.map.service.GeometryService;
import com.oyxh.map.service.UserService;


@Controller
public class GeometryController {
	@Autowired
	  private GeometryService geometryService;
	
	/**
	 * 
	* @Title: list 
	* @Description: TODO(获取图层列表) 
	* @param @return    设定文件 
	* @return List<GeometryDO>    返回类型 
	* @throws
	 */
	@GetMapping("/geometrylist")
	@ResponseBody()
	List<Map<String, Object>> list() {
		Map<String, Object> params = new HashMap<>();
		UserDO user = (UserDO) SecurityUtils.getSubject().getPrincipal();
		params.put("userId", user.getUserId());
		List<GeometryDO> geometrys = geometryService.list(params);	
		List<Map<String,Object>> res = new ArrayList<Map<String,Object>>();	
		for(GeometryDO geometry : geometrys) {
			Map<String,Object> geometryTo = new HashMap<String,Object>();
			geometryTo.put("geometryId", geometry.getGeometryId());
			geometryTo.put("geometryName", geometry.getGeometryName());
			/*String gsonString1 = Geometry.getGeometryData();
			List<Polygon> GeometryData = GsonUtil.GsonToList(gsonString1, Polygon.class);
			GeometryTo.put("GeometryData", GeometryData);*/
			geometryTo.put("userId", geometry.getUserId());
			geometryTo.put("geometryDes", geometry.getGeometryDes());
			geometryTo.put("geometryClass", geometry.getGeometryClass());
			geometryTo.put("layerId", geometry.getLayerId());
			geometryTo.put("isBackground", geometry.getIsBackground());
			String gsonString2 = geometry.getGeometryData();
			List<String> geometryDataFromData = GsonUtil.GsonToList(gsonString2, String.class);
			// List<String> geometryData = GsonUtil.GsonToList(gsonString2, Point.class);
			//GeometryTo.put("GeometryGroundData", Geometry.getGeometryGroundData());
			// geometryTo.put("geometryData", geometryDataFromData);
			List<String> geometryData = new ArrayList<String>();
			if(geometry.getIsBackground().equals("WGS84")) {
				for(String geometryString :geometryDataFromData) {
					String resultString = "";
					String[] points = geometryString.split(";");
					for(String point:points) {
						String[] cood = point.split(",");
						double[] convecCood = CoodinateConvertor.wgs2bd(Double.parseDouble(cood[1]),Double.parseDouble(cood[0]));
						if(point!=points[points.length - 1]) {
							resultString += convecCood[1]+","+convecCood[0]+";";
						}else {
							resultString += convecCood[1]+","+convecCood[0];
						}			
					}	
					geometryData.add(resultString);
				}			
			}else {
				geometryData = geometryDataFromData;
			}
			geometryTo.put("geometryData", geometryData);
			res.add(geometryTo);
		}
		return res;
	}
	
	@Log("删除geometry")
	@PostMapping("/removegeometrys")
	@ResponseBody()
	R deleteGeometrys(@RequestBody String json) {
		List<Long> geometryIds = GsonUtil.GsonToList(json, Long.class);
		Long [] setToArray = new Long[geometryIds.size()];  
        setToArray = geometryIds.toArray(setToArray) ;  
        if(setToArray.length == 0) {
        	return R.ok();
        }
		if (geometryService.batchRemove(setToArray) > 0) {
			return R.ok();
		} else {
			return R.error(1, "删除失败");
		}
	}
	
	@Log("删除图层中的geometrys")
	@PostMapping("/removelayer/geometrys")
	@ResponseBody()
	R delete(@RequestParam(value="id",defaultValue="0") Long id) {
		int r = geometryService.removelayer(id);
		if (r >= 0) {
			return R.ok();
		} else {
			return R.error(1, "删除失败");
		}
	}
	
	
	@Log("批量更新geometry")
	@PostMapping("/editgeometrys")
	@ResponseBody()
	R editGeometrys(@RequestBody String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(GeometryDO.class, new GeometryDeserializer());
	    Gson gson = gsonBuilder.create();
	    GsonUtil.setGson(gson);
		List<GeometryDO> geometrys = GsonUtil.GsonToList(json, GeometryDO.class);
		UserDO user = (UserDO) SecurityUtils.getSubject().getPrincipal();
		if(geometrys.size() == 0) {
	        	return R.ok();
	    }
		if (geometryService.batchUpdate(geometrys,user.getUserId()) > 0) {
			return R.ok();
		} else {
			return R.error(1, "删除失败");
		}
		
	}
	
	@Log("批量增加geometry")
	@PostMapping("/addgeometrys")
	@ResponseBody()
	R addGeometrys(@RequestBody String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(GeometryDO.class, new GeometryDeserializer());
	    Gson gson = gsonBuilder.create();
	    GsonUtil.setGson(gson);
		List<GeometryDO> geometrys = GsonUtil.GsonToList(json, GeometryDO.class);
		if(geometrys.size() == 0) {
	        	return R.ok();
	    }
		if (geometryService.batchAdd(geometrys) > 0) {
			List<Long> batchid = new ArrayList<Long>();
			for(GeometryDO geometry:geometrys) {
				batchid.add(geometry.getGeometryId());
			}
			return R.ok(batchid);
		} else {
			return R.error(1, "添加失败");
		}
		
	}

}
