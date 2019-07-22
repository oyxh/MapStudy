package com.oyxh.map.domain;

/** 
* @ClassName: LayerDo 
* @Description: TODO(图层的实体) 
* @author oyxh
* @date 2019年5月25日 下午10:18:53 
*  
*/
public class LayerDO {
	private long layerId;
	private String layerName;
	private long userId;
	private String layerDes;
	public long getLayerId() {
		return layerId;
	}
	public void setLayerId(long layerId) {
		this.layerId = layerId;
	}
	public String getLayerName() {
		return layerName;
	}
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getLayerDes() {
		return layerDes;
	}
	public void setLayerDes(String layerDes) {
		this.layerDes = layerDes;
	}
	
	
}
