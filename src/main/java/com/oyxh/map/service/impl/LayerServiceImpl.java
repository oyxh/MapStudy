package com.oyxh.map.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.oyxh.map.dao.LayerDao;
import com.oyxh.map.domain.LayerDO;
import com.oyxh.map.service.LayerService;

@Service
public class LayerServiceImpl implements LayerService {
	
	@Autowired
	LayerDao layerMapper;
	
	/*@Override
	public LayerDO get(Long layer_id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int count(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}*/
	@Override
	public List<LayerDO> list(Map<String,Object> params) {
		
		List<LayerDO> layers = layerMapper.list(params);
		return layers;
	}

	@Override
	public int save(LayerDO layer) {
		int count = layerMapper.save(layer);
		
		//Long layerId = layer.getRoleId();
		
		return count;
	}
	
	@Override
	public int remove(Long layer_id) {
		int count = layerMapper.remove(layer_id);
		
		return count;
	}


	@Override
	public int update(LayerDO layer) {
		// TODO Auto-generated method stub
		int count = layerMapper.update(layer);
		return count;
	}

	@Override
	public int batchRemove(Long[] layerIds) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	@Override
	public int batchRemove(Long[] layerIds) {
		// TODO Auto-generated method stub
		return 0;
	}
*/
}
