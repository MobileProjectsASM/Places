package com.applications.asm.domain.repository;

import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.entities.Review;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface AllReviews {
    Single<Response<List<Review>>> ofThisPlace(String placeId);
}
