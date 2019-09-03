package com.oyxh.map.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.oyxh.map.domain.GeometryDO;

@Service
public interface GeometryService {
	

	
/*LayerDo get(Long layer_id);

	
	
	int count(Map<String,Object> map);*/
	List<GeometryDO> list(Map<String,Object> map);
	int save(GeometryDO geometry);
	int remove(Long geometry_id);
	int update(GeometryDO geometry);
	
	
	
	int batchRemove(Long[] geometryIds);
	int batchUpdate(List<GeometryDO> Geometrys);
	
	int removelayer(long layerId);

}
