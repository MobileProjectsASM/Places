package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.ReviewModel;
import com.applications.asm.domain.entities.Review;

import java.util.List;

public interface ReviewModelMapper {
    Review getReview(ReviewModel reviewModel);
    List<Review> getReviews(List<ReviewModel> reviewsModel);
}
