package com.oyxh.map.dao;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.oyxh.map.domain.GeometryDO;



/** 
* @ClassName: GeometryDao 
* @Description: 图层的dao接口
* @author oyxh
* @date 2019年5月25日 下午10:18:01 
*  
*/
@Mapper
public interface GeometryDao {

	/*GeometryDO get(Long Geometry_id);
	
	
	
	int count(Map<String,Object> map);*/
	List<GeometryDO> list(Map<String,Object> map);
	int save(GeometryDO geometry);
	int remove(Long geometry_id);
	int update(GeometryDO geometry);
	
	
	
	int batchRemove(Long[] GeometryIds);
	
	int batchUpdate(List<GeometryDO> Geometrys);
}
