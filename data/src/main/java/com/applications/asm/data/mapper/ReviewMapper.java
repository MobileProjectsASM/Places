package com.applications.asm.data.mapper;

import com.applications.asm.data.framework.network.graphql.PlaceReviewsQuery;
import com.applications.asm.domain.entities.Review;

import java.util.List;

public interface ReviewMapper {
    List<Review> queryReviewsToReviews(List<PlaceReviewsQuery.Review> reviews);
    Review queryReviewToReview(PlaceReviewsQuery.Review review);
}
