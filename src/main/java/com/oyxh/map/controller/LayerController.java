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
import org.springframework.web.bind.annotation.ResponseBody;


import com.oyxh.map.common.annotation.Log;
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
	List<LayerDO> list() {
		Map<String, Object> params = new HashMap<>();
		UserDO user = (UserDO) SecurityUtils.getSubject().getPrincipal();
		System.out.println(user.getUserId());
		params.put("userId", user.getUserId());
		List<LayerDO> layers = layerService.list(params);
		return layers;
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
