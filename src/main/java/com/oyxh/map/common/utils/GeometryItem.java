package com.oyxh.map.common.utils;

import java.util.List;

public class GeometryItem {
	private long geometryId;
	private String geometryName;
	private long layerId;
	private long userId;
	private String geometryDes;
	private List<Point> geometryData;
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
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getGeometryDes() {
		return geometryDes;
	}
	public void setGeometryDes(String geometryDes) {
		this.geometryDes = geometryDes;
	}

	public List<Point> getGeometryData() {
		return geometryData;
	}
	public void setGeometryData(List<Point> geometryData) {
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
}
