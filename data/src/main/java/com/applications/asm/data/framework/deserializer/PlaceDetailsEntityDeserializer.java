package com.applications.asm.data.framework.deserializer;

import com.applications.asm.data.entity.PlaceDetailsEntity;
import com.applications.asm.data.entity.WorkingHoursEntity;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlaceDetailsEntityDeserializer implements JsonDeserializer<PlaceDetailsEntity> {
    @Override
    public PlaceDetailsEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject body = json.getAsJsonObject();

        JsonObject hours = body.getAsJsonObject("hours");
        Boolean isOpen = hours.get("is_open_now").getAsBoolean();

        List<WorkingHoursEntity> workingHoursEntityDays = deserializeWorkingHoursEntityDays(hours.getAsJsonArray("open"));

        PlaceDetailsEntity placeDetailsEntity = new PlaceDetailsEntity();
        placeDetailsEntity.setId(body.get("id").getAsString());
        placeDetailsEntity.setName(body.get("name").getAsString());
        placeDetailsEntity.setImageUrl(body.get("image_url").getAsString());
        placeDetailsEntity.setRating(body.get("rating").getAsDouble());
        placeDetailsEntity.setPrice(body.get("price").getAsString());
        placeDetailsEntity.setPhoneNumber(body.get("phone").getAsString());
        placeDetailsEntity.setReviewCount(body.get("review_count").getAsInt());
        placeDetailsEntity.setOpen(isOpen);
        placeDetailsEntity.setWorkingHoursEntityDays(workingHoursEntityDays);

        return placeDetailsEntity;
    }

    private List<WorkingHoursEntity> deserializeWorkingHoursEntityDays(JsonArray open) {
        List<WorkingHoursEntity> workingHoursEntityDays = new ArrayList<>();

        for(JsonElement workingHoursEntity: open)
            workingHoursEntityDays.add(deserializeWorkingHoursEntity(workingHoursEntity.getAsJsonObject()));

        return workingHoursEntityDays;
    }

    private WorkingHoursEntity deserializeWorkingHoursEntity(JsonObject jsonObject) {
        WorkingHoursEntity workingHoursEntity = new WorkingHoursEntity();

        workingHoursEntity.setDay(jsonObject.get("day").getAsInt());
        workingHoursEntity.setHourOpen(jsonObject.get("start").getAsString());
        workingHoursEntity.setHourClose(jsonObject.get("end").getAsString());

        return workingHoursEntity;
    }
}
