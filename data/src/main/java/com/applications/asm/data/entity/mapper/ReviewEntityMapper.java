package com.applications.asm.data.entity.mapper;

import com.applications.asm.data.entity.ReviewEntity;
import com.applications.asm.domain.entities.Review;

public interface ReviewEntityMapper {
    Review getReviewFromReviewEntity(ReviewEntity reviewEntity);
}
