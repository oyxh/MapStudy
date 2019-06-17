package com.oyxh.map.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oyxh.map.dao.DistrictDao;
import com.oyxh.map.domain.DistrictDo;
import com.oyxh.map.service.DistrictService;
@Service
public class DistrictServiceImpl implements DistrictService{
	@Autowired
	DistrictDao districtMapper;

	@Override
	public List<DistrictDo> list(Map<String, Object> params) {
		// TODO Auto-generated method stub
		List<DistrictDo> districts = districtMapper.list(params);
		return districts;
	}
}
