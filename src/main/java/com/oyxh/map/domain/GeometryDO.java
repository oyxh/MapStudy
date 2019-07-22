package com.oyxh.map.domain;

public class GeometryDO {
	private long geometryId;
	private String geometryName;
	private long layerId;
	private long userId;
	private String geometryDes;
	private String geometryData;
	private String geometryClass;
	private String isBackground;
	public long getGeometryId() {
		return geometryId;
	}
	public void setGeometryId(long geometryId) {
		this.geometryId = geometryId;
	}
	public String getGeometryName() {
		return geometryName;
	}
	public void setGeometryName(String geometryName) {
		this.geometryName = geometryName;
	}
	public long getLayerId() {
		return layerId;
	}
	public void setLayerId(long layerId) {
		this.layerId = layerId;
	}
	public String getGeometryDes() {
		return geometryDes;
	}
	public void setGeometryDes(String geometryDes) {
		this.geometryDes = geometryDes;
	}
	public String getGeometryData() {
		return geometryData;
	}
	public void setGeometryData(String geometryData) {
		this.geometryData = geometryData;
	}
	public String getGeometryClass() {
		return geometryClass;
	}
	public void setGeometryClass(String geometryClass) {
		this.geometryClass = geometryClass;
	}
	public String getIsBackground() {
		return isBackground;
	}
	public void setIsBackground(String isBackground) {
		this.isBackground = isBackground;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
