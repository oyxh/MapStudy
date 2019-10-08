package com.oyxh.map.common.annotation;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.oyxh.map.domain.GeometryDO;
import com.oyxh.map.domain.UserDO;


public class GeometryDeserializer implements JsonDeserializer<GeometryDO> {

	@Override
	public GeometryDO deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {
		// TODO Auto-generated method stub
		
	
	
		 final JsonObject jsonObject = json.getAsJsonObject();
		 
		    final JsonElement jsongeometryId = jsonObject.get("geometryId");

		    final long geometryId = (jsongeometryId==null) ? 0:
		    	jsongeometryId.getAsLong();
		 
		    final JsonElement jsonlayerName = jsonObject.get("geometryName");
		    final String geometryName = jsonlayerName.getAsString();
		    
		    final JsonElement jsonlayerId = jsonObject.get("layerId");
		    final long layerId = jsonlayerId.getAsLong();
		    
		    final JsonElement jsongeometryDes = jsonObject.get("geometryDes");
		    final String geometryDes = (jsongeometryDes==null)||jsongeometryDes.toString().equals("null") ? null:jsongeometryDes.getAsString();
		    
		    final JsonElement jsongeometryClass = jsonObject.get("geometryClass");
		    final String geometryClass =(jsongeometryClass==null)||(jsongeometryClass.toString().equals("null")) ? null:jsongeometryClass.getAsString();
		    
		    final JsonElement jsonisBackground = jsonObject.get("isbackground");
		    final String isBackground = (jsonisBackground==null)||jsonisBackground.toString().equals("null") ? null:jsonisBackground.getAsString();
		    
		    final JsonElement jsongeometryData = jsonObject.get("geometryData");
		    final JsonArray geometryData = (jsongeometryData == null)||jsongeometryData.toString().equals("null")  ? null : jsongeometryData.getAsJsonArray();
		     List<String> geometryDataString = new ArrayList<String>();
		    for (JsonElement element : geometryData) {
		    	String elementStr = "";
		    	if(element instanceof JsonArray) {
           		 JsonArray jsonarray = element.getAsJsonArray();
   	             for (JsonElement point : jsonarray) { 
   	            	elementStr += point.toString() + ';';
   	             }
   	           elementStr = elementStr.replaceAll("[\\[\\]]", "").replaceAll("\\[", "").replaceAll("\\]", "");
   	            elementStr ="\""+elementStr.substring(0, elementStr.length() - 1)+"\"";
   	             geometryDataString.add(elementStr);
           	 }		    	
            }
		    final String geometryData1 = geometryDataString.toString();
		    System.out.println(geometryData1);
		    UserDO user = (UserDO) SecurityUtils.getSubject().getPrincipal();
		    final long userId = user.getUserId();

		    final GeometryDO layerItem = new GeometryDO();
		    layerItem.setGeometryId(geometryId);
		    layerItem.setGeometryName(geometryName);
		    layerItem.setLayerId(layerId);
		    layerItem.setGeometryDes(geometryDes);
		    layerItem.setGeometryClass(geometryClass);
		    layerItem.setIsBackground(isBackground);
		   layerItem.setGeometryData(geometryData1);
		    layerItem.setUserId(userId);
		    
		    return layerItem;
	}
	

}
