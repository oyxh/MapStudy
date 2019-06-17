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
	private String layerData;
	private long userId;
	private String layerGround;
	public String getLayerGround() {
		return layerGround;
	}
	public void setLayerGround(String layerGround) {
		this.layerGround = layerGround;
	}
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
	public String getLayerData() {
		return layerData;
	}
	public void setLayerData(String layerData) {
		this.layerData = layerData;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
