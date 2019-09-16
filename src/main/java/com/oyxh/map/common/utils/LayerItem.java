package com.oyxh.map.common.utils;

import java.util.List;

public class LayerItem {
	private long layerId;
	private String layerName;
	private String layerData;
	private long userId;
	private String layerDes;
	
	public String getLayerDes() {
		return layerDes;
	}
	public void setLayerDes(String layerDes) {
		this.layerDes = layerDes;
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
