package com.oyxh.map.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.oyxh.map.common.annotation.LayerItemDeserializer;
import com.oyxh.map.common.annotation.Log;
import com.oyxh.map.common.utils.Polygon;
import com.oyxh.map.common.utils.GsonUtil;
import com.oyxh.map.common.utils.LayerItem;
import com.oyxh.map.common.utils.Person;
import com.oyxh.map.common.utils.Point;
import com.oyxh.map.common.utils.R;
import com.oyxh.map.domain.LayerDO;
import com.oyxh.map.domain.UserDO;
import com.oyxh.map.service.LayerService;


@Controller
public class LayerController {
	 @Autowired
	  private LayerService layerService;

	
	
	@Log("增加图层")
	@PostMapping("/addlayer")
	@ResponseBody
	R ajaxLogin(String layerName) {
		UserDO user = (UserDO) SecurityUtils.getSubject().getPrincipal();
		LayerDO layer=new LayerDO();
		layer.setLayerName(layerName);
		layer.setUserId(user.getUserId());
		layerService.save(layer);
	
			return R.ok(layer.getLayerId());
		
	}
	
	@Log("保存图层")
	  /** 
     * 请求内容是一个json串,spring会自动把他和我们的参数bean对应起来,不过要加@RequestBody注解 
     *  
     * @param name 
     * @param pwd 
     * @return 
     */  
    @PostMapping(value = "/savelayer")  
	@ResponseBody
    public R  saveLayer(@RequestBody String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(LayerDO.class, new LayerItemDeserializer());
	    Gson gson = gsonBuilder.create();
	    LayerDO layerItem = gson.fromJson(json, LayerDO.class);
	    layerService.update(layerItem);
        if (null != layerItem) {  
            // return login4Return(layerItem.getName(), layerItem.getPwd());  
        } else {  
            return R.error();  
        }
    	return R.ok();
    }  
	
	

	
	/**
	 * 
	* @Title: list 
	* @Description: TODO(获取图层列表) 
	* @param @return    设定文件 
	* @return List<RoleDO>    返回类型 
	* @throws
	 */
	@GetMapping("/layerlist")
	@ResponseBody()
	List<Map<String, Object>> list() {
		Map<String, Object> params = new HashMap<>();
		UserDO user = (UserDO) SecurityUtils.getSubject().getPrincipal();
		params.put("userId", user.getUserId());
		List<LayerDO> layers = layerService.list(params);	
		List<Map<String,Object>> res = new ArrayList<Map<String,Object>>();	
		for(LayerDO layer : layers) {
			Map<String,Object> layerTo = new HashMap<String,Object>();
			layerTo.put("layerId", layer.getLayerId());
			layerTo.put("layerName", layer.getLayerName());
			/*String gsonString1 = layer.getLayerData();
			List<Polygon> layerData = GsonUtil.GsonToList(gsonString1, Polygon.class);
			layerTo.put("layerData", layerData);*/
			layerTo.put("userId", layer.getUserId());
			layerTo.put("layerDes", layer.getLayerDes());
			String gsonString2 = layer.getLayerData();
			List<String> layerData = GsonUtil.GsonToList(gsonString2, String.class);
			layerTo.put("layerData", layerData);
			/*layerTo.put("layerGround", layer.getLayerGround());
			System.out.println(layer.getLayerGroundData());
			String gsonString2 = layer.getLayerGroundData();
			System.out.println(gsonString2);
			List<Polygon> layerGroundData = GsonUtil.GsonToList(gsonString2, Polygon.class);
			//layerTo.put("layerGroundData", layer.getLayerGroundData());
			layerTo.put("layerGroundData", layerGroundData);*/
			res.add(layerTo);
		}
		return res;
	}
	
	@Log("删除图层")
	@PostMapping("/removelayer")
	@ResponseBody()
	R delete(@RequestParam(value="id",defaultValue="0") Long id) {
		System.out.println("delete layer"+id.toString());
		if (layerService.remove(id) > 0) {
			return R.ok();
		} else {
			return R.error(1, "删除失败");
		}
	}
	
	
}
