package com.applications.asm.data.entity.mapper;

import com.applications.asm.data.entity.ReviewEntity;
import com.applications.asm.domain.entities.Review;

public class ReviewEntityMapperImpl implements ReviewEntityMapper {

    @Override
    public Review getReviewFromReviewEntity(ReviewEntity reviewEntity) {
        String name = reviewEntity.getUserName();
        String imageUrl = reviewEntity.getImageUrl();
        String date = reviewEntity.getDateCreated();
        Integer rate = reviewEntity.getRate();
        String description = reviewEntity.getDescription();
        return new Review(name, imageUrl, date, rate, description);
    }
}
