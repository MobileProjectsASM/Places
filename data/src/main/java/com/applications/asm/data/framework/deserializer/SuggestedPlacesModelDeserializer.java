package com.applications.asm.data.framework.deserializer;

import android.util.Log;

import com.applications.asm.data.model.ResponseSuggestedPlacesModel;
import com.applications.asm.data.model.SuggestedPlaceModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SuggestedPlacesModelDeserializer implements JsonDeserializer<ResponseSuggestedPlacesModel> {
    private final Gson gson;

    public SuggestedPlacesModelDeserializer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public ResponseSuggestedPlacesModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        Log.i(getClass().getName(), gson.toJson(json));
        JsonObject body = json.getAsJsonObject();
        JsonElement businessesJson = body.get("businesses");

        List<SuggestedPlaceModel> suggestedPlacesModel = businessesJson != null && !businessesJson.isJsonNull() ? deserializeSuggestedPlaces(businessesJson.getAsJsonArray()) : new ArrayList<>();

        ResponseSuggestedPlacesModel responseSuggestedPlacesModel = new ResponseSuggestedPlacesModel();
        responseSuggestedPlacesModel.setSuggestPlacesModel(suggestedPlacesModel);
        responseSuggestedPlacesModel.setTotal(suggestedPlacesModel.size());

        return responseSuggestedPlacesModel;
    }

    private List<SuggestedPlaceModel> deserializeSuggestedPlaces(JsonArray businesses) {
        if(businesses.size() > 0) {
            List<SuggestedPlaceModel> suggestedPlacesModel = new ArrayList<>();
            for(JsonElement suggestedPlaceModel: businesses)
                suggestedPlacesModel.add(deserializeSuggestedPlaceModel(suggestedPlaceModel.getAsJsonObject()));
            return suggestedPlacesModel;
        }
        return new ArrayList<>();
    }

    private SuggestedPlaceModel deserializeSuggestedPlaceModel(JsonObject suggestedPlace) {
        JsonElement idJson = suggestedPlace.get("id");
        JsonElement nameJson = suggestedPlace.get("name");

        String id = idJson != null && !idJson.isJsonNull() ? idJson.getAsString() : "";
        String name = nameJson != null && !nameJson.isJsonNull() ? nameJson.getAsString() : "";

        SuggestedPlaceModel suggestedPlaceModel = new SuggestedPlaceModel();
        suggestedPlaceModel.setId(id);
        suggestedPlaceModel.setName(name);

        return suggestedPlaceModel;
    }
}
