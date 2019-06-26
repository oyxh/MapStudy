package com.oyxh.map.common.annotation;

import java.lang.reflect.Type;

import org.apache.shiro.SecurityUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.oyxh.map.domain.LayerDO;
import com.oyxh.map.domain.UserDO;

public class LayerItemDeserializer implements JsonDeserializer<LayerDO> {

	@Override
	public LayerDO deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {
		// TODO Auto-generated method stub
		
		 final JsonObject jsonObject = json.getAsJsonObject();
		 
		    final JsonElement jsonlayerId = jsonObject.get("layerId");
		    final long layerId = jsonlayerId.getAsLong();
		 
		    final JsonElement jsonlayerName = jsonObject.get("layerName");
		    final String layerName = jsonlayerName.getAsString();
		 
		    
		    final JsonElement jsonlayerData = jsonObject.get("layerData");
		    final String layerData = jsonlayerData.getAsString();
		    
		    UserDO user = (UserDO) SecurityUtils.getSubject().getPrincipal();
		    final long userId = user.getUserId();
		    
		    final JsonElement jsonlayerGround = jsonObject.get("layerGround");
		    final String layerGround = jsonlayerGround.getAsString();
		    
		    final JsonElement jsonlayerGroundData = jsonObject.get("layerGroundData");
		    final String layerGroundData = jsonlayerGroundData.toString();
		 
		    final LayerDO layerItem = new LayerDO();
		    layerItem.setLayerData(layerData);;
		    layerItem.setLayerGround(layerGround);;
		    layerItem.setLayerGroundData(layerGroundData);;
		    layerItem.setLayerId(layerId);
		    layerItem.setLayerName(layerName);
		    layerItem.setUserId(userId);
		    
		    return layerItem;
	}
	

}