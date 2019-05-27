package com.oyxh.map.dao;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.oyxh.map.domain.LayerDO;



/** 
* @ClassName: LayerDao 
* @Description: 图层的dao接口
* @author oyxh
* @date 2019年5月25日 下午10:18:01 
*  
*/
@Mapper
public interface LayerDao {

	/*LayerDO get(Long layer_id);
	
	
	
	int count(Map<String,Object> map);*/
	List<LayerDO> list(Map<String,Object> map);
	int save(LayerDO layer);
	int remove(Long layer_id);
	/*int update(LayerDO layer);
	
	
	
	int batchRemove(Long[] layerIds);*/
}
