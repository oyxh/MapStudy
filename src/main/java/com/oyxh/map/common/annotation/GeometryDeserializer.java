package com.oyxh.map.common.annotation;

import java.lang.reflect.Type;

import org.apache.shiro.SecurityUtils;

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
		    final long geometryId = jsongeometryId.getAsLong();
		 
		    final JsonElement jsonlayerName = jsonObject.get("geometryName");
		    final String geometryName = jsonlayerName.getAsString();
		    
		    final JsonElement jsonlayerId = jsonObject.get("layerId");
		    final long layerId = jsonlayerId.getAsLong();
		    
		    final JsonElement jsongeometryDes = jsonObject.get("geometryDes");
		    final String geometryDes = (jsongeometryDes.toString().equals("null")) ? null:jsongeometryDes.getAsString();
		    
		    final JsonElement jsongeometryClass = jsonObject.get("geometryClass");
		    final String geometryClass = jsongeometryClass.getAsString();
		    
		    final JsonElement jsonisBackground = jsonObject.get("isbackground");
		    final String isBackground = jsonisBackground.getAsString();
		    
		    final JsonElement jsongeometryData = jsonObject.get("geometryData");
		    final String geometryData = (jsongeometryData == null) ? null : jsongeometryData.toString();
		    
		    UserDO user = (UserDO) SecurityUtils.getSubject().getPrincipal();
		    final long userId = user.getUserId();

		    final GeometryDO layerItem = new GeometryDO();
		    layerItem.setGeometryId(geometryId);
		    layerItem.setGeometryName(geometryName);
		    layerItem.setLayerId(layerId);
		    layerItem.setGeometryDes(geometryDes);
		    layerItem.setGeometryClass(geometryClass);
		    layerItem.setIsBackground(isBackground);
		    layerItem.setGeometryData(geometryData);
		    layerItem.setUserId(userId);
		    
		    return layerItem;
	}
	

}
