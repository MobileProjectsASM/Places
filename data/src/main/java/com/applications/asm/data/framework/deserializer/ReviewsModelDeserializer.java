package com.applications.asm.data.framework.deserializer;

import android.util.Log;

import com.applications.asm.data.model.ResponseReviewsModel;
import com.applications.asm.data.model.ReviewModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReviewsModelDeserializer implements JsonDeserializer<ResponseReviewsModel> {
    private final Gson gson;

    public ReviewsModelDeserializer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public ResponseReviewsModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        Log.i(getClass().getName(), gson.toJson(json));

        JsonObject body = json.getAsJsonObject();

        JsonElement reviewsJson = body.get("reviews");

        List<ReviewModel> reviewsModel = reviewsJson != null && !reviewsJson.isJsonNull() ? deserializeReviews(reviewsJson.getAsJsonArray()) : new ArrayList<>();

        ResponseReviewsModel responseReviewsModel = new ResponseReviewsModel();
        responseReviewsModel.setReviewModels(reviewsModel);
        responseReviewsModel.setTotal(body.get("total").getAsInt());

        return responseReviewsModel;
    }

    private List<ReviewModel> deserializeReviews(JsonArray reviews) {
        List<ReviewModel> reviewsModel = new ArrayList<>();
        for(JsonElement reviewModel: reviews)
            reviewsModel.add(deserializeReviewModel(reviewModel.getAsJsonObject()));
        return reviewsModel;
    }

    private ReviewModel deserializeReviewModel(JsonObject review) {
        JsonElement userJson = review.get("user");
        JsonElement timeCreatedJson = review.get("time_created");
        JsonElement ratingJson = review.get("rating");
        JsonElement textJson = review.get("text");
        JsonObject user = userJson != null && !userJson.isJsonNull() ? userJson.getAsJsonObject() : new JsonObject();
        JsonElement nameJson = user.get("name");
        JsonElement imageUrlJson = user.get("image_url");


        String name = nameJson != null && !nameJson.isJsonNull() ? nameJson.getAsString() : "";
        String imageUrl = imageUrlJson != null && !imageUrlJson.isJsonNull() ? imageUrlJson.getAsString() : "";
        String dateCreated = timeCreatedJson != null && !timeCreatedJson.isJsonNull() ? getDateCreated(timeCreatedJson.getAsString()) : "";
        Integer rating = ratingJson != null && !ratingJson.isJsonNull() ? ratingJson.getAsInt() : 0;
        String description = textJson != null && !textJson.isJsonNull() ? textJson.getAsString() : "";

        ReviewModel reviewModel = new ReviewModel();
        reviewModel.setUserName(name);
        reviewModel.setImageUrl(imageUrl);
        reviewModel.setDateCreated(dateCreated);
        reviewModel.setRate(rating);
        reviewModel.setDescription(description);

        return reviewModel;
    }

    private String getDateCreated(String time) {
        if(time.compareTo("") != 0) {
            String[] date = time.split(" ");
            return date[0];
        }
        return "";
    }
}
