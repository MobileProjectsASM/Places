package com.applications.asm.data.framework.deserializer;

import com.applications.asm.data.entity.ReviewEntity;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReviewsEntityDeserializer implements JsonDeserializer<List<ReviewEntity>> {
    @Override
    public List<ReviewEntity> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject body = json.getAsJsonObject();
        JsonArray reviews = body.getAsJsonArray("reviews");


        List<ReviewEntity> reviewsEntity = new ArrayList<>();
        for(JsonElement reviewEntity: reviews)
            reviewsEntity.add(deserializeReviewEntity(reviewEntity.getAsJsonObject()));

        return reviewsEntity;
    }

    private ReviewEntity deserializeReviewEntity(JsonObject review) {
        JsonObject user = review.getAsJsonObject("user");

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setUserName(user.get("name").getAsString());
        reviewEntity.setImageUrl(user.get("image_url").getAsString());
        reviewEntity.setDateCreated(getDateCreated(review.get("time_created").getAsString()));
        reviewEntity.setRate(review.get("rating").getAsInt());
        reviewEntity.setDescription(review.get("text").getAsString());

        return reviewEntity;
    }

    private String getDateCreated(String time) {
        String[] date = time.split(" ");
        return date[0];
    }
}
