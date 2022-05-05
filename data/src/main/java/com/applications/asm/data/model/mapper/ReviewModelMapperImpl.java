package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.ReviewModel;
import com.applications.asm.domain.entities.Review;

public class ReviewModelMapperImpl implements ReviewModelMapper {

    @Override
    public Review getReviewFromReviewModel(ReviewModel reviewModel) {
        String name = reviewModel.getUserName();
        String imageUrl = reviewModel.getImageUrl();
        String date = reviewModel.getDateCreated();
        Integer rate = reviewModel.getRate();
        String description = reviewModel.getDescription();
        return new Review(name, imageUrl, date, rate, description);
    }
}
