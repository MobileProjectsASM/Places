package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.ReviewModel;
import com.applications.asm.domain.entities.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewModelMapperImpl implements ReviewModelMapper {
    @Override
    public Review getReview(ReviewModel reviewModel) {
        String name = reviewModel.getUserName();
        String imageUrl = reviewModel.getImageUrl();
        String date = reviewModel.getDateCreated();
        Integer rate = reviewModel.getRate();
        String description = reviewModel.getDescription();
        return new Review(name, imageUrl, date, rate, description);
    }

    @Override
    public List<Review> getReviews(List<ReviewModel> reviewsModel) {
        List<Review> reviews = new ArrayList<>();
        for(ReviewModel reviewModel: reviewsModel)
            reviews.add(getReview(reviewModel));
        return reviews;
    }
}
