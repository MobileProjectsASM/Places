package com.applications.asm.data.mapper;

import com.applications.asm.data.PlaceReviewsQuery;
import com.applications.asm.domain.entities.Review;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ReviewMapperImpl implements ReviewMapper {

    @Inject
    public ReviewMapperImpl() {

    }

    @Override
    public List<Review> queryReviewsToReviews(List<PlaceReviewsQuery.Review> reviewsQuery) {
        List<Review> reviews = new ArrayList<>();
        for(PlaceReviewsQuery.Review review : reviewsQuery)
            reviews.add(queryReviewToReview(review));
        return reviews;
    }

    @Override
    public Review queryReviewToReview(PlaceReviewsQuery.Review review) {

        String userName = "";
        PlaceReviewsQuery.User user = review.user();
        if(user != null) userName = user.name();

        String imageUrl = "";
        PlaceReviewsQuery.User user2 = review.user();
        if(user2 != null) imageUrl = user2.image_url() != null ? user2.image_url() : "";

        String date = "";
        String timeCreated = review.time_created();
        if(timeCreated != null) date = getDateCreated(timeCreated);

        Integer rate = 0;
        Integer rating = review.rating();
        if(rating != null) rate = rating;

        String description = review.text() != null ? review.text() : "";

        return new Review(userName, imageUrl, date, rate, description);
    }

    private String getDateCreated(String time) {
        if(time.compareTo("") != 0) {
            String[] date = time.split(" ");
            return date[0];
        }
        return "";
    }
}
