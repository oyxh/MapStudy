package com.oyxh.map.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oyxh.map.domain.DistrictDo;
import com.oyxh.map.service.DistrictService;


@Controller
public class DistrictController {
	 @Autowired
	  private DistrictService districtService;
	 @GetMapping("/districtlist")
		@ResponseBody()
		List<Map<String,Object>> list() {
			Map<String, Object> params = new HashMap<>();
			List<DistrictDo> districts = districtService.list(params);
			Map<String,Object> currentPro = null;
			Map<String,Object> currentCity = null;
			Object currentCounty = null;
			
			long currentProId = 0;
			long currentCityId = 0;
			
			List<Map<String,Object>> res = new ArrayList<Map<String,Object>>();
			for(DistrictDo district:districts) {
				
				if(district.getCountyId()%10000==0) { //省
					Map<String,Object> newPro = new HashMap<String,Object>();
					List<Map<String,Object>> cityCollection = new ArrayList<Map<String,Object>>();
					newPro.put("title", district.getCountyName());
					newPro.put("children", cityCollection);
					res.add(newPro);
					currentPro = newPro;
					currentProId = district.getCountyId()/10000;
					
				}else if(district.getCountyId()%100==0) { //市
					if(district.getCountyId()/10000 == currentProId) {
						if(district.getCountyId()/100 != currentCityId) {  //和前面的城市不一样
							Map<String,Object> newCity = new HashMap<String,Object>();
							List<Map<String,Object>> countyCollection = new ArrayList<Map<String,Object>>();
							newCity.put("title", district.getCountyName());
							newCity.put("children", countyCollection);
							((List<Map<String, Object>>) currentPro.get("children")).add(newCity);
							currentCity = newCity;
							currentCityId = district.getCountyId()/100;
						}
					}
				}else {//县
					if(district.getCountyId()/10000 == currentProId) {
						Map<String,Object> newCounty = new HashMap<String,Object>();
						newCounty.put("title", district.getCountyName());
						if(currentCityId/100 != currentProId) { //没有地级市的省级行政区
							((List<Map<String, Object>>) currentPro.get("children")).add(newCounty);
						}else if(currentCityId/100 == currentProId) {
							((List<Map<String, Object>>) currentCity.get("children")).add(newCounty);
						}
					}
				}
				
			}
			return res;
		}
}
