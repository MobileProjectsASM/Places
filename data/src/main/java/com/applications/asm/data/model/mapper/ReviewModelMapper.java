package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.ReviewModel;
import com.applications.asm.domain.entities.Review;

public interface ReviewModelMapper {
    Review getReviewFromReviewModel(ReviewModel reviewModel);
}
