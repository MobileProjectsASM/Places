package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.exception.ClientException;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetReviewsUc extends SingleUseCase<List<Review>, String> {
    private final PlacesRepository placesRepository;

    public GetReviewsUc(UseCaseScheduler useCaseScheduler, PlacesRepository placesRepository) {
        super(useCaseScheduler);
        this.placesRepository = placesRepository;
    }

    private Single<String> validateParams(String placeId) {
        return Single.fromCallable(() -> {
            if (placeId == null)
                throw new ClientException("placeId is null");
            return placeId;
        });
    }

    @Override
    protected Single<List<Review>> build(String placeId) {
        return validateParams(placeId)
                .flatMap(placesRepository::getReviews);
    }
}
