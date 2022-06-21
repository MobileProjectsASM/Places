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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlacesModelDeserializer implements JsonDeserializer<ResponsePlacesModel> {
    private final Gson gson;

    public PlacesModelDeserializer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public ResponsePlacesModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        Log.i(getClass().getName(), gson.toJson(json));
        JsonObject body = json.getAsJsonObject();
        JsonElement totalJson = body.get("total");
        JsonElement businessesJson = body.get("businesses");

        int total = totalJson != null && !totalJson.isJsonNull() ? totalJson.getAsInt() : 0;
        List<PlaceModel> places = total > 0 && businessesJson != null && !businessesJson.isJsonNull() ? deserializePlaces(businessesJson.getAsJsonArray()) : new ArrayList<>();

        ResponsePlacesModel responsePlacesModel = new ResponsePlacesModel();
        responsePlacesModel.setPlaces(places);
        responsePlacesModel.setTotal(total);

        return responsePlacesModel;
    }

    private List<PlaceModel> deserializePlaces(JsonArray businesses) {
        List<PlaceModel> placesModel = new ArrayList<>();
        for(JsonElement business: businesses)
            placesModel.add(deserializePlace(business.getAsJsonObject()));
        return placesModel;
    }

    private PlaceModel deserializePlace(JsonObject place) {
        JsonElement idJson = place.get("id");
        JsonElement nameJson = place.get("name");
        JsonElement imageUrlJson = place.get("image_url");
        JsonElement coordinatesJson = place.get("coordinates");
        JsonElement categoriesJson = place.get("categories");
        JsonElement locationJson = place.get("location");

        String id = idJson != null && !idJson.isJsonNull() ? idJson.getAsString() : "";
        String name = nameJson != null && !nameJson.isJsonNull() ? nameJson.getAsString() : "";
        String imageUrl = imageUrlJson != null && !imageUrlJson.isJsonNull() ? imageUrlJson.getAsString() : "";
        CoordinatesModel coordinatesModel = coordinatesJson != null && !coordinatesJson.isJsonNull() ? deserializeCoordinates(coordinatesJson.getAsJsonObject()) : new CoordinatesModel(0d, 0d);
        List<CategoryModel> categories = categoriesJson != null && !categoriesJson.isJsonNull() ? deserializeCategories(categoriesJson.getAsJsonArray()) : new ArrayList<>();
        LocationModel locationModel = locationJson != null && !locationJson.isJsonNull() ? deserializeLocation(locationJson.getAsJsonObject()) : new LocationModel("", "", "", "", "", "");

        PlaceModel placeModel = new PlaceModel();
        placeModel.setId(id);
        placeModel.setName(name);
        placeModel.setImageUrl(imageUrl);
        placeModel.setCoordinatesModel(coordinatesModel);
        placeModel.setCategories(categories);
        placeModel.setLocationModel(locationModel);

        return placeModel;
    }

    private CoordinatesModel deserializeCoordinates(JsonObject coordinatesJson) {
        JsonElement latitudeJson = coordinatesJson.get("latitude");
        JsonElement longitudeJson = coordinatesJson.get("longitude");

        double latitude = latitudeJson != null && !latitudeJson.isJsonNull() ? latitudeJson.getAsDouble() : 0;
        double longitude = longitudeJson != null && !longitudeJson.isJsonNull() ? longitudeJson.getAsDouble() : 0;

        CoordinatesModel coordinatesModel = new CoordinatesModel();
        coordinatesModel.setLatitude(latitude);
        coordinatesModel.setLongitude(longitude);
        return coordinatesModel;
    }

    private List<CategoryModel> deserializeCategories(JsonArray categories) {
        List<CategoryModel> categoriesModel = new ArrayList<>();
        for (JsonElement category: categories)
            categoriesModel.add(deserializeCategory(category.getAsJsonObject()));
        return categoriesModel;
    }

    private CategoryModel deserializeCategory(JsonObject category) {
        JsonElement aliasJson = category.get("alias");
        JsonElement titleJson = category.get("title");

        String alias = aliasJson != null && !aliasJson.isJsonNull() ? aliasJson.getAsString() : "";
        String title = titleJson != null && !titleJson.isJsonNull() ? titleJson.getAsString() : "";
        return new CategoryModel(alias, title);
    }

    private LocationModel deserializeLocation(JsonObject locationJson) {
        JsonElement countryJson = locationJson.get("country");
        JsonElement stateJson = locationJson.get("state");
        JsonElement cityJson = locationJson.get("city");
        JsonElement zipCodeJson = locationJson.get("zip_code");
        JsonElement suburbJson = locationJson.get("address2");
        JsonElement addressJson = locationJson.get("address1");

        String country = countryJson != null && !countryJson.isJsonNull() ? countryJson.getAsString() : "";
        String state = stateJson != null && !stateJson.isJsonNull() ? stateJson.getAsString() : "";
        String city = cityJson != null && !cityJson.isJsonNull() ? cityJson.getAsString() : "";
        String zipCode = zipCodeJson != null && !zipCodeJson.isJsonNull() ? zipCodeJson.getAsString() : "";
        String suburb = suburbJson != null && !suburbJson.isJsonNull() ? suburbJson.getAsString() : "";
        String address = addressJson != null && !addressJson.isJsonNull() ? addressJson.getAsString() : "";

        LocationModel locationModel = new LocationModel();
        locationModel.setCountry(country);
        locationModel.setState(state);
        locationModel.setCity(city);
        locationModel.setZipCode(zipCode);
        locationModel.setSuburb(suburb);
        locationModel.setAddress(address);
        return locationModel;
    }
}
