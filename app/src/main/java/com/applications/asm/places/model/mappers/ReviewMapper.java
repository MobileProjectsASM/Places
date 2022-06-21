package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Review;
import com.applications.asm.places.model.ReviewVM;

import java.util.List;

public interface ReviewMapper {
    ReviewVM getReview(Review review);
    List<ReviewVM> getReviews(List<Review> reviews);
}
