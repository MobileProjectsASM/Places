package com.applications.asm.data.mapper;

import com.applications.asm.data.framework.network.graphql.PlaceReviewsQuery;
import com.applications.asm.domain.entities.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewMapperImpl implements ReviewMapper {
    @Override
    public List<Review> queryReviewsToReviews(List<PlaceReviewsQuery.Review> reviewsQuery) {
        List<Review> reviews = new ArrayList<>();
        for(PlaceReviewsQuery.Review review : reviewsQuery)
            reviews.add(queryReviewToReview(review));
        return reviews;
    }

    @Override
    public Review queryReviewToReview(PlaceReviewsQuery.Review review) {
        String userName = review.user != null && review.user.name != null ? review.user.name : "";
        String imageUrl = review.user != null && review.user.image_url != null ? review.user.image_url : "";
        String date = review.time_created != null ? getDateCreated(review.time_created) : "";
        Integer rate = review.rating != null ? review.rating : 0;
        String description = review.text != null ? review.text : "";
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
