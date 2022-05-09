package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Review;
import com.applications.asm.places.model.ReviewM;

public interface ReviewMapper {
    ReviewM getReviewMFromReview(Review review);
}
