package com.oyxh.map.common.utils;

import java.util.List;
/**
 * 
* @ClassName: GridZone 
* @Description: 网格的类，gridData存放多边形的点
* @author oyxh
* @date 2019年6月22日 下午8:11:24 
*
 */
public class Polygon {
	private String polygonName;
	private String polygonMana;
	private List<Point> polygonData;
	                    

	public String getPolygonName() {
		return polygonName;
	}
	public void setPolygonName(String polygonName) {
		this.polygonName = polygonName;
	}
	public String getPolygonMana() {
		return polygonMana;
	}
	public void setPolygonMana(String polygonMana) {
		this.polygonMana = polygonMana;
	}
	public List<Point> getPolygonData() {
		return polygonData;
	}
	public void setPolygonData(List<Point> polygonData) {
		this.polygonData = polygonData;
	}
	
	
}
