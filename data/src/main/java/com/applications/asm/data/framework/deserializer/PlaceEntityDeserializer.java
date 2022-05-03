package com.applications.asm.data.framework.deserializer;

import com.applications.asm.data.entity.PlaceEntity;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class PlaceEntityDeserializer implements JsonDeserializer<PlaceEntity> {
    @Override
    public PlaceEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        return null;
    }
}
