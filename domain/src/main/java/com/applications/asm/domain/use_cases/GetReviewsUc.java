package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.exception.ParameterException;
import com.applications.asm.domain.repository.AllReviews;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetReviewsUc extends SingleUseCase<Response<List<Review>>, GetReviewsUc.Params> {
    private final AllReviews allReviews;

    public static class Params {
        private final String placeId;
        private final String locale;

        private Params(String placeId, String locale) {
            this.placeId = placeId;
            this.locale = locale;
        }

        public static Params forGetReviews(String placeId, String locale) {
            return new Params(placeId, locale);
        }
    }

    @Inject
    public GetReviewsUc(UseCaseScheduler useCaseScheduler, AllReviews allReviews) {
        super(useCaseScheduler);
        this.allReviews = allReviews;
    }

    private Single<Params> validateParams(Params params) {
        return Single.fromCallable(() -> {
            if (params.placeId == null || params.locale == null)
                throw new ParameterException("You entered a null value");
            return params;
        });
    }

    @Override
    protected Single<Response<List<Review>>> build(Params params) {
        return validateParams(params)
                .flatMap(param -> allReviews.ofThisPlace(params.placeId, params.locale));
    }
}
