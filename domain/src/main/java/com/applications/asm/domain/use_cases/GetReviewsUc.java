package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.exception.ParameterError;
import com.applications.asm.domain.exception.ParameterException;
import com.applications.asm.domain.repository.AllReviews;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetReviewsUc extends SingleUseCase<Response<List<Review>>, String> {
    private final AllReviews allReviews;

    public GetReviewsUc(UseCaseScheduler useCaseScheduler, AllReviews allReviews) {
        super(useCaseScheduler);
        this.allReviews = allReviews;
    }

    private Single<String> validateParams(String placeId) {
        return Single.fromCallable(() -> {
            if (placeId == null)
                throw new ParameterException(ParameterError.NULL_VALUE);
            return placeId;
        });
    }

    @Override
    protected Single<Response<List<Review>>> build(String placeId) {
        return validateParams(placeId)
                .flatMap(allReviews::ofThisPlace);
    }
}
