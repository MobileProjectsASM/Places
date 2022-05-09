package com.applications.asm.data.framework.deserializer;

import android.util.Log;

import com.applications.asm.data.model.CategoryModel;
import com.applications.asm.data.model.CoordinatesModel;
import com.applications.asm.data.model.LocationModel;
import com.applications.asm.data.model.PlaceModel;
import com.applications.asm.data.model.ResponsePlacesModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlacesModelDeserializer implements JsonDeserializer<ResponsePlacesModel> {
    private final String TAG = "PlacesModelDeserializer";
    private final Gson gson;

    public PlacesModelDeserializer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public ResponsePlacesModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Log.i(TAG, gson.toJson(json));
        JsonObject body = json.getAsJsonObject();

        JsonArray businesses = body.getAsJsonArray("businesses");
        List<PlaceModel> places = deserializePlaces(businesses);

        ResponsePlacesModel responsePlacesModel = new ResponsePlacesModel();
        responsePlacesModel.setPlaces(places);
        responsePlacesModel.setTotal(body.get("total").getAsInt());
        return responsePlacesModel;
    }

    private List<PlaceModel> deserializePlaces(JsonArray businesses) {
        List<PlaceModel> placesModel = new ArrayList<>();
        for(JsonElement business: businesses)
            placesModel.add(deserializePlace(business.getAsJsonObject()));
        return placesModel;
    }

    private PlaceModel deserializePlace(JsonObject place) {
        CoordinatesModel coordinatesModel = deserializeCoordinates(place.getAsJsonObject("coordinates"));
        List<CategoryModel> categoriesModel = deserializeCategories(place.getAsJsonArray("categories"));
        LocationModel locationModel = deserializeLocation(place.getAsJsonObject("location"));

        PlaceModel placeModel = new PlaceModel();
        placeModel.setId(place.get("id").getAsString());
        placeModel.setName(place.get("name").getAsString());
        placeModel.setImageUrl(place.get("image_url").isJsonNull() ? "" : place.get("image_url").getAsString());
        placeModel.setCoordinatesModel(coordinatesModel);
        placeModel.setCategories(categoriesModel);
        placeModel.setLocationModel(locationModel);

        return placeModel;
    }

    private CoordinatesModel deserializeCoordinates(JsonObject coordinates) {
        CoordinatesModel coordinatesModel = new CoordinatesModel();
        coordinatesModel.setLatitude(coordinates.get("latitude").getAsDouble());
        coordinatesModel.setLongitude(coordinates.get("longitude").getAsDouble());
        return coordinatesModel;
    }

    private List<CategoryModel> deserializeCategories(JsonArray categories) {
        List<CategoryModel> categoriesModel = new ArrayList<>();
        for (JsonElement category: categories)
            categoriesModel.add(deserializeCategory(category.getAsJsonObject()));
        return categoriesModel;
    }

    private CategoryModel deserializeCategory(JsonObject category) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(category.get("alias").getAsString());
        categoryModel.setName(category.get("title").getAsString());
        return categoryModel;
    }

    private LocationModel deserializeLocation(JsonObject location) {
        LocationModel locationModel = new LocationModel();
        locationModel.setCountry(location.get("country").isJsonNull() ? "" : location.get("country").getAsString());
        locationModel.setState(location.get("state").isJsonNull() ? "" : location.get("state").getAsString());
        locationModel.setCity(location.get("city").isJsonNull() ? "" : location.get("city").getAsString());
        locationModel.setZipCode(location.get("zip_code").isJsonNull() ? "" : location.get("zip_code").getAsString());
        locationModel.setSuburb(location.get("address2").isJsonNull() ? "" : location.get("address2").getAsString());
        locationModel.setAddress(location.get("address1").isJsonNull() ? "" : location.get("address1").getAsString());
        return locationModel;
    }
}
