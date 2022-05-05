package com.applications.asm.data.framework.deserializer;

import com.applications.asm.data.model.ResponseReviewsModel;
import com.applications.asm.data.model.ReviewModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReviewsModelDeserializer implements JsonDeserializer<ResponseReviewsModel> {

    @Override
    public ResponseReviewsModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject body = json.getAsJsonObject();

        JsonArray reviews = body.getAsJsonArray("reviews");
        List<ReviewModel> reviewsModel = deserializeReviews(reviews);

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
        JsonObject user = review.getAsJsonObject("user");

        ReviewModel reviewModel = new ReviewModel();
        reviewModel.setUserName(user.get("name").getAsString());
        reviewModel.setImageUrl(user.get("image_url").getAsString());
        reviewModel.setDateCreated(getDateCreated(review.get("time_created").getAsString()));
        reviewModel.setRate(review.get("rating").getAsInt());
        reviewModel.setDescription(review.get("text").getAsString());

        return reviewModel;
    }

    private String getDateCreated(String time) {
        String[] date = time.split(" ");
        return date[0];
    }
}
