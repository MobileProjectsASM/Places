package com.applications.asm.data.framework.deserializer;

import com.applications.asm.data.model.ResponseSuggestedPlacesModel;
import com.applications.asm.data.model.SuggestedPlaceModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SuggestedPlacesModelDeserializer implements JsonDeserializer<ResponseSuggestedPlacesModel> {
    @Override
    public ResponseSuggestedPlacesModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject body = json.getAsJsonObject();

        List<SuggestedPlaceModel> suggestedPlacesModel = deserializeSuggestedPlaces(body.getAsJsonArray("businesses"));

        ResponseSuggestedPlacesModel responseSuggestedPlacesModel = new ResponseSuggestedPlacesModel();
        responseSuggestedPlacesModel.setSuggestPlacesModel(suggestedPlacesModel);
        responseSuggestedPlacesModel.setTotal(suggestedPlacesModel.size());

        return responseSuggestedPlacesModel;
    }

    private List<SuggestedPlaceModel> deserializeSuggestedPlaces(JsonArray businesses) {
        List<SuggestedPlaceModel> suggestedPlacesModel = new ArrayList<>();

        for(JsonElement suggestedPlaceModel: businesses)
            suggestedPlacesModel.add(deserializeSuggestedPlaceModel(suggestedPlaceModel.getAsJsonObject()));

        return suggestedPlacesModel;
    }

    private SuggestedPlaceModel deserializeSuggestedPlaceModel(JsonObject suggestedPlace) {
        SuggestedPlaceModel suggestedPlaceModel = new SuggestedPlaceModel();

        suggestedPlaceModel.setId(suggestedPlace.get("id").getAsString());
        suggestedPlaceModel.setName(suggestedPlace.get("name").getAsString());

        return suggestedPlaceModel;
    }
}
