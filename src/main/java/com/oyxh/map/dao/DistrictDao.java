package com.oyxh.map.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.oyxh.map.domain.DistrictDo;

@Mapper
public interface DistrictDao {
	List<DistrictDo> list(Map<String,Object> map);
}
