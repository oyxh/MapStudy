package com.oyxh.map.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oyxh.map.dao.GeometryDao;
import com.oyxh.map.domain.GeometryDO;
import com.oyxh.map.service.GeometryService;

@Service
public class GeometryServiceImpl implements GeometryService {
	
	@Autowired
	GeometryDao geometryMapper;
	
	/*@Override
	public GeometryDO get(Long Geometry_id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int count(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}*/
	@Override
	public List<GeometryDO> list(Map<String,Object> params) {
		
		List<GeometryDO> geometrys = geometryMapper.list(params);
		return geometrys;
	}

	@Override
	public int save(GeometryDO Geometry) {
		int count = geometryMapper.save(Geometry);
		
		//Long GeometryId = Geometry.getRoleId();
		
		return count;
	}
	
	@Override
	public int remove(Long geometry_id) {
		int count = geometryMapper.remove(geometry_id);
		
		return count;
	}


	@Override
	public int update(GeometryDO geometry) {
		// TODO Auto-generated method stub
		int count = geometryMapper.update(geometry);
		return count;
	}

	@Override
	public int batchRemove(Long[] geometryIds) {
		int r = geometryMapper.batchRemove(geometryIds);
		return r;
	}

	@Override
	public int batchUpdate(List<GeometryDO> Geometrys) {
		int r = geometryMapper.batchUpdate(Geometrys);
		return r;
	}


	/*
	@Override
	public int batchRemove(Long[] GeometryIds) {
		// TODO Auto-generated method stub
		return 0;
	}
*/
}
