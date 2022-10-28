package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Review;
import com.applications.asm.places.model.ReviewVM;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ReviewMapperImpl implements ReviewMapper {

    @Inject
    public ReviewMapperImpl() {

    }

    @Override
    public ReviewVM getReviewVM(Review review) {
        return new ReviewVM(review.getUserName(), review.getImageUrl(), review.getDate(), review.getRate(), review.getDescription());
    }

    @Override
    public List<ReviewVM> getReviewsVM(List<Review> reviews) {
        List<ReviewVM> reviewsVM = new ArrayList<>();
        for(Review review: reviews)
            reviewsVM.add(getReviewVM(review));
        return reviewsVM;
    }
}
