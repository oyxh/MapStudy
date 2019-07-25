package com.oyxh.map.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oyxh.map.common.utils.GsonUtil;
import com.oyxh.map.common.utils.Point;
import com.oyxh.map.common.utils.Polygon;
import com.oyxh.map.domain.GeometryDO;
import com.oyxh.map.domain.UserDO;
import com.oyxh.map.service.GeometryService;


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
		System.out.println(user.getUserId());
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
			geometryTo.put("isbackground", geometry.getIsBackground());
			String gsonString2 = geometry.getGeometryData();
			//System.out.println(gsonString2);
			List<Point> geometryData = GsonUtil.GsonToList(gsonString2, Point.class);
			//GeometryTo.put("GeometryGroundData", Geometry.getGeometryGroundData());
			geometryTo.put("geometryData", geometryData);
			res.add(geometryTo);
		}
		return res;
	}

}
