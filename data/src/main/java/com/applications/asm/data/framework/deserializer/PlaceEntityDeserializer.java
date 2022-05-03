package com.applications.asm.data.framework.deserializer;

import com.applications.asm.data.entity.CategoryEntity;
import com.applications.asm.data.entity.CoordinatesEntity;
import com.applications.asm.data.entity.LocationEntity;
import com.applications.asm.data.entity.PlaceEntity;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlaceEntityDeserializer implements JsonDeserializer<List<PlaceEntity>> {

    @Override
    public List<PlaceEntity> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<PlaceEntity> placesEntity = new ArrayList<>();
        JsonObject body = json.getAsJsonObject();
        JsonArray businesses = body.getAsJsonArray("businesses");
        for(JsonElement business: businesses)
            placesEntity.add(deserializePlace(business.getAsJsonObject()));
        return placesEntity;
    }

    private PlaceEntity deserializePlace(JsonObject place) {
        CoordinatesEntity coordinatesEntity = deserializeCoordinates(place.getAsJsonObject("coordinates"));
        List<CategoryEntity> categoriesEntity = deserializeCategories(place.getAsJsonArray("categories"));
        LocationEntity locationEntity = deserializeLocation(place.getAsJsonObject("location"));

        PlaceEntity placeEntity = new PlaceEntity();
        placeEntity.setId(place.get("id").getAsString());
        placeEntity.setName(place.get("alias").getAsString());
        placeEntity.setImageUrl(place.get("image_url").getAsString());
        placeEntity.setCoordinates(coordinatesEntity);
        placeEntity.setCategories(categoriesEntity);
        placeEntity.setLocation(locationEntity);

        return placeEntity;
    }

    private CoordinatesEntity deserializeCoordinates(JsonObject coordinates) {
        CoordinatesEntity coordinatesEntity = new CoordinatesEntity();
        coordinatesEntity.setLatitude(coordinates.get("latitude").getAsDouble());
        coordinatesEntity.setLongitude(coordinates.get("longitude").getAsDouble());
        return coordinatesEntity;
    }

    private List<CategoryEntity> deserializeCategories(JsonArray categories) {
        List<CategoryEntity> categoriesEntity = new ArrayList<>();
        for (JsonElement category: categories)
            categoriesEntity.add(deserializeCategory(category.getAsJsonObject()));
        return categoriesEntity;
    }

    private CategoryEntity deserializeCategory(JsonObject category) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(category.get("alias").getAsString());
        categoryEntity.setName(category.get("title").getAsString());
        return categoryEntity;
    }

    private LocationEntity deserializeLocation(JsonObject location) {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setCountry(location.get("country").getAsString());
        locationEntity.setState(location.get("state").getAsString());
        locationEntity.setCity(location.get("city").getAsString());
        locationEntity.setZipCode(location.get("zip_code").getAsString());
        locationEntity.setSuburb(location.get("address2").getAsString());
        locationEntity.setAddress(location.get("address1").getAsString());
        return locationEntity;
    }
}
