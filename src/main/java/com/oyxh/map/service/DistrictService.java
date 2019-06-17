package com.oyxh.map.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.oyxh.map.domain.DistrictDo;



@Service
public interface DistrictService {
	List<DistrictDo> list(Map<String,Object> map);
}
