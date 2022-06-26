package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.exception.ClientException;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import io.reactivex.rxjava3.core.Single;

public class GetPlaceDetailsUc extends SingleUseCase<PlaceDetails, String> {
    private final PlacesRepository placesRepository;

    public GetPlaceDetailsUc(UseCaseScheduler useCaseScheduler, PlacesRepository placesRepository) {
        super(useCaseScheduler);
        this.placesRepository = placesRepository;
    }

    private Single<String> validateParams(String placeId) {
        return Single.fromCallable(() -> {
            if (placeId == null)
                throw new ClientException("Null value was entered");
            return placeId;
        });
    }

    @Override
    protected Single<PlaceDetails> build(String placeId) {
        return validateParams(placeId)
                .flatMap(placesRepository::getPlaceDetails);
    }
}
