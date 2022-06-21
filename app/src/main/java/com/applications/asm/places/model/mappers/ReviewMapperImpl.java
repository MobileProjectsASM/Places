package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Review;
import com.applications.asm.places.model.ReviewVM;

import java.util.ArrayList;
import java.util.List;

public class ReviewMapperImpl implements ReviewMapper {
    @Override
    public ReviewVM getReview(Review review) {
        return new ReviewVM(review.getUserName(), review.getImageUrl(), review.getDate(), review.getRate(), review.getDescription());
    }

    @Override
    public List<ReviewVM> getReviews(List<Review> reviews) {
        List<ReviewVM> reviewsVM = new ArrayList<>();
        for(Review review: reviews)
            reviewsVM.add(getReview(review));
        return reviewsVM;
    }
}
