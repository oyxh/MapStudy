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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

	@GetMapping("/addlayer")
	 @ResponseBody
	public List<String> AddLayer(String layername) {
	
		UserDO user = (UserDO) SecurityUtils.getSubject().getPrincipal();
		System.out.println("1"+user.toString());
		System.out.println(user.getName());
		System.out.println(user.getUsername());
		List<String> r = new ArrayList();
		r.add("user");
		return r;
		
	}
	
	
	@Log("增加图层")
	@PostMapping("/addlayer")
	@ResponseBody
	R ajaxLogin(String layerName) {
		
		System.out.println(layerName);
		UserDO user = (UserDO) SecurityUtils.getSubject().getPrincipal();
		System.out.println(user.getName());
		LayerDO layer=new LayerDO();
		layer.setLayerName(layerName);
		layer.setUserId(user.getUserId());
		layerService.save(layer);
	
			return R.ok();
		
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
		System.out.println("I got it");
		System.out.println(json);
		/*System.out.println(layerItem.getLayerName());
		System.out.println(layerItem.getLayerGround());
		 System.out.println(layerItem.getLayerGroundData().size());
		for(Polygon test :layerItem.getLayerGroundData()) {
			System.out.println(test.getPolygonName());
			for(com.oyxh.map.common.utils.Point point:test.getPolygonData()) {
				System.out.println(point.getLat()+":"+point.getLng());
			}
			
		}
        if (null != layerItem) {  
            // return login4Return(layerItem.getName(), layerItem.getPwd());  
        } else {  
            return R.error();  
        }*/
    	return R.ok();
    }  
	
	
	
	@RequestMapping(value = "/person", method = { RequestMethod.POST, RequestMethod.GET }) 

    public Map<String,Object> getPerson(@RequestBody Person person){ 
		System.out.println("I got it");
		System.out.println(person.getName());
		System.out.println(person.getSex());
        Map<String,Object> param = new HashMap<String, Object>(); 
        //String s = person.getPhones().toString(); 
        //System.out.println(s); 
        param.put("person",person); 
        return param; 
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
		System.out.println(user.getUserId());
		params.put("userId", user.getUserId());
		List<LayerDO> layers = layerService.list(params);
		List<Map<String,Object>> res = new ArrayList<Map<String,Object>>();
		String gsonString = "[{\"lng\":120.15,\"lat\":27.001},{\"lng\":120.8,\"lat\":27.9}]";
	List<Point> test = GsonUtil.GsonToList(gsonString, Point.class);
		Gson gson = new Gson();
		List<Point> stringList = gson.fromJson(gsonString, new TypeToken<List<Point>>() {}.getType());
		
		for(Point gz:test) {
			System.out.println(gz.getLat());
			System.out.println(gz.getLng());
		}
		String gsonString1 = "[{\"gridName\":\"test1\",\"gridMana\":\"lizhang\",\"gridData\":[{\"lng\":120.15,\"lat\":27.001},{\"lng\":120.8,\"lat\":27.9}]},"
				+ "{\"gridName\":\"test2\",\"gridMana\":\"lizhang222\",\"gridData\":[{\"lng\":120.15,\"lat\":27.001},{\"lng\":120.8,\"lat\":27.9},{\"lng\":120.6,\"lat\":27.9}]}]";
		//List<GridZone> stringList1 = gson.fromJson(gsonString1, new TypeToken<List<GridZone>>() {}.getType());
		List<Polygon> stringList1 = GsonUtil.GsonToList(gsonString1, Polygon.class);
		System.out.println("stringList1"+stringList1.size());
	
		for(LayerDO layer : layers) {
			Map<String,Object> layerTo = new HashMap<String,Object>();
			layerTo.put("layerId", layer.getLayerId());
			layerTo.put("layerName", layer.getLayerName());
			//List<String> polygons = new ArrayList<String>();
			
			List<Polygon> polygons = null;
			if(layer.getLayerData()!=null) {
			 polygons = GsonUtil.GsonToList(layer.getLayerData(), Polygon.class);
			 }
			layerTo.put("layerData", polygons);
			String background = new String();
			layerTo.put("layerGround", background);
			List<Polygon> backgroundPoly = null;
			if(layer.getLayerGroundData()!=null) {
				backgroundPoly = GsonUtil.GsonToList(layer.getLayerGroundData(), Polygon.class);
			 }
			 layerTo.put("layerGroundData", backgroundPoly);
			res.add(layerTo);
		}
		return res;
	}
	
	@Log("删除图层")
	@PostMapping("/removelayer")
	@ResponseBody()
	R delete(Long id) {
		System.out.println("delete layer"+id.toString());
		if (layerService.remove(id) > 0) {
			return R.ok();
		} else {
			return R.error(1, "删除失败");
		}
	}
	
	
}
