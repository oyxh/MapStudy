package com.oyxh.map.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.oyxh.map.domain.LayerDO;




@Service
public interface LayerService {
	

	
/*LayerDo get(Long layer_id);

	
	
	int count(Map<String,Object> map);*/
	List<LayerDO> list(Map<String,Object> map);
	int save(LayerDO layer);
	
	/*int update(LayerDO layer);
	
	int remove(Long layer_id);
	
	int batchRemove(Long[] layerIds);*/


}
