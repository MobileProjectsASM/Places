package com.applications.asm.data.framework.deserializer;

import android.util.Log;

import com.applications.asm.data.model.CoordinatesModel;
import com.applications.asm.data.model.LocationModel;
import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.WorkingHoursModel;
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

public class PlaceDetailsModelDeserializer implements JsonDeserializer<PlaceDetailsModel> {
    private final Gson gson;

    public PlaceDetailsModelDeserializer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public PlaceDetailsModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Log.i(getClass().getName(), gson.toJson(json));

        JsonObject body = json.getAsJsonObject();

        JsonElement idJson = body.get("id");
        JsonElement nameJson = body.get("name");
        JsonElement imageUrlJson = body.get("image_url");
        JsonElement hoursJson = body.get("hours");
        JsonElement ratingJson = body.get("rating");
        JsonElement priceJson = body.get("price");
        JsonElement phoneJson = body.get("phone");
        JsonElement reviewCountJson = body.get("review_count");
        JsonElement coordinatesJson = body.get("coordinates");
        JsonElement locationJson = body.get("location");
        JsonElement categoriesJson = body.get("categories");
        JsonArray hoursJsonArray = hoursJson != null && !hoursJson.isJsonNull() ? hoursJson.getAsJsonArray() : new JsonArray();
        JsonObject itemJson = hoursJsonArray.size() > 0 ? hoursJsonArray.get(0).getAsJsonObject() : new JsonObject();
        JsonElement isOpenNowJson = itemJson.get("is_open_now");
        JsonElement openJson = itemJson.get("open");

        String id = idJson != null && !idJson.isJsonNull() ? idJson.getAsString() : "";
        String name = nameJson != null && !nameJson.isJsonNull() ? nameJson.getAsString() : "";
        String imageUrl = imageUrlJson != null && !imageUrlJson.isJsonNull() ? imageUrlJson.getAsString() : "";
        Double rating = ratingJson != null && !ratingJson.isJsonNull() ? ratingJson.getAsDouble() : 0;
        String price = priceJson != null && !priceJson.isJsonNull() ? priceJson.getAsString() : "";
        String phone = phoneJson != null && !phoneJson.isJsonNull() ? phoneJson.getAsString() : "";
        List<String> categories = categoriesJson != null && !categoriesJson.isJsonNull() ? deserializeCategories(categoriesJson.getAsJsonArray()) : new ArrayList<>();
        LocationModel locationModel = locationJson != null && !locationJson.isJsonNull() ? deserializeLocation(locationJson.getAsJsonObject()) : new LocationModel("", "", "", "", "", "");
        Integer reviewCount = reviewCountJson != null && !reviewCountJson.isJsonNull() ? reviewCountJson.getAsInt() : 0;
        Boolean isOpen = isOpenNowJson != null && !isOpenNowJson.isJsonNull() && isOpenNowJson.getAsBoolean();
        List<WorkingHoursModel> workingHoursModelDays = openJson != null && !openJson.isJsonNull() ? deserializeWorkingHoursModelDays(openJson.getAsJsonArray()) : new ArrayList<>();

        CoordinatesModel coordinatesModel = coordinatesJson != null && !coordinatesJson.isJsonNull() ? deserializeCoordinates(coordinatesJson.getAsJsonObject()) : new CoordinatesModel(0d, 0d);

        PlaceDetailsModel placeDetailsModel = new PlaceDetailsModel();
        placeDetailsModel.setId(id);
        placeDetailsModel.setName(name);
        placeDetailsModel.setImageUrl(imageUrl);
        placeDetailsModel.setCoordinatesModel(coordinatesModel);
        placeDetailsModel.setCategories(categories);
        placeDetailsModel.setLocationModel(locationModel);
        placeDetailsModel.setRating(rating);
        placeDetailsModel.setPrice(price);
        placeDetailsModel.setPhoneNumber(phone);
        placeDetailsModel.setReviewCount(reviewCount);
        placeDetailsModel.setWorkingHoursModelDays(workingHoursModelDays);
        placeDetailsModel.setOpen(isOpen);

        return placeDetailsModel;
    }

    private CoordinatesModel deserializeCoordinates(JsonObject coordinates) {
        JsonElement latitudeJson = coordinates.get("latitude");
        JsonElement longitudeJson = coordinates.get("longitude");

        Double latitude = latitudeJson != null && !latitudeJson.isJsonNull() ? latitudeJson.getAsDouble() : 0;
        Double longitude = longitudeJson != null && !longitudeJson.isJsonNull() ? longitudeJson.getAsDouble() : 0;

        CoordinatesModel coordinatesModel = new CoordinatesModel();
        coordinatesModel.setLatitude(latitude);
        coordinatesModel.setLongitude(longitude);
        return coordinatesModel;
    }

    private List<String> deserializeCategories(JsonArray categories) {
        List<String> categoriesModel = new ArrayList<>();
        for (JsonElement category: categories)
            categoriesModel.add(deserializeCategory(category.getAsJsonObject()));
        return categoriesModel;
    }

    private String deserializeCategory(JsonObject category) {
        JsonElement titleJson = category.get("title");
        return titleJson != null && !titleJson.isJsonNull() ? titleJson.getAsString() : "";
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

    private List<WorkingHoursModel> deserializeWorkingHoursModelDays(JsonArray open) {
        List<WorkingHoursModel> workingHoursModelDays = new ArrayList<>();
        for(JsonElement workingHours: open)
            workingHoursModelDays.add(deserializeWorkingHoursModel(workingHours.getAsJsonObject()));
        return workingHoursModelDays;
    }

    private WorkingHoursModel deserializeWorkingHoursModel(JsonObject jsonObject) {
        JsonElement dayJson = jsonObject.get("day");
        JsonElement startJson = jsonObject.get("start");
        JsonElement endJson = jsonObject.get("end");

        Integer day = dayJson != null && !dayJson.isJsonNull() ? dayJson.getAsInt() : -1;
        String start = startJson != null && !startJson.isJsonNull() ? startJson.getAsString() : "";
        String end = endJson != null && !endJson.isJsonNull() ? endJson.getAsString() : "";

        WorkingHoursModel workingHoursModel = new WorkingHoursModel();
        workingHoursModel.setDay(day);
        workingHoursModel.setHourOpen(start);
        workingHoursModel.setHourClose(end);

        return workingHoursModel;
    }
}
