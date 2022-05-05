package com.applications.asm.data.framework.deserializer;

import com.applications.asm.data.model.CoordinatesModel;
import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.WorkingHoursModel;
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
    @Override
    public PlaceDetailsModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject body = json.getAsJsonObject();

        JsonObject hours = body.getAsJsonObject("hours");
        Boolean isOpen = hours.get("is_open_now").getAsBoolean();

        List<WorkingHoursModel> workingHoursModelDays = deserializeWorkingHoursModelDays(hours.getAsJsonArray("open"));

        CoordinatesModel coordinatesModel = deserializeCoordinatesModel(body.getAsJsonObject("coordinates"));

        PlaceDetailsModel placeDetailsModel = new PlaceDetailsModel();
        placeDetailsModel.setId(body.get("id").getAsString());
        placeDetailsModel.setName(body.get("name").getAsString());
        placeDetailsModel.setImageUrl(body.get("image_url").getAsString());
        placeDetailsModel.setCoordinatesModel(coordinatesModel);
        placeDetailsModel.setRating(body.get("rating").getAsDouble());
        placeDetailsModel.setPrice(body.get("price").getAsString());
        placeDetailsModel.setPhoneNumber(body.get("phone").getAsString());
        placeDetailsModel.setReviewCount(body.get("review_count").getAsInt());
        placeDetailsModel.setOpen(isOpen);
        placeDetailsModel.setWorkingHoursModelDays(workingHoursModelDays);

        return placeDetailsModel;
    }

    private CoordinatesModel deserializeCoordinatesModel(JsonObject coordinates) {
        CoordinatesModel coordinatesModel = new CoordinatesModel();
        coordinatesModel.setLongitude(coordinates.get("longitude").getAsDouble());
        coordinatesModel.setLatitude(coordinates.get("latitude").getAsDouble());
        return coordinatesModel;
    }

    private List<WorkingHoursModel> deserializeWorkingHoursModelDays(JsonArray open) {
        List<WorkingHoursModel> workingHoursModelDays = new ArrayList<>();

        for(JsonElement workingHours: open)
            workingHoursModelDays.add(deserializeWorkingHoursModel(workingHours.getAsJsonObject()));

        return workingHoursModelDays;
    }

    private WorkingHoursModel deserializeWorkingHoursModel(JsonObject jsonObject) {
        WorkingHoursModel workingHoursModel = new WorkingHoursModel();

        workingHoursModel.setDay(jsonObject.get("day").getAsInt());
        workingHoursModel.setHourOpen(jsonObject.get("start").getAsString());
        workingHoursModel.setHourClose(jsonObject.get("end").getAsString());

        return workingHoursModel;
    }
}
