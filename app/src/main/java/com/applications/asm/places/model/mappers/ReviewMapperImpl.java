package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Review;
import com.applications.asm.places.model.ReviewM;

public class ReviewMapperImpl implements ReviewMapper {
    @Override
    public ReviewM getReviewMFromReview(Review review) {
        return new ReviewM(review.getUserName(), review.getImageUrl(), review.getDate(), review.getRate(), review.getDescription());
    }
}
