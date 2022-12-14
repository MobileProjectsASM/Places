package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.exception.ParameterException;
import com.applications.asm.domain.repository.AllPlacesDetails;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetPlaceDetailsUc extends SingleUseCase<Response<PlaceDetails>, String> {
    private final AllPlacesDetails allPlacesDetails;

    @Inject
    public GetPlaceDetailsUc(UseCaseScheduler useCaseScheduler, AllPlacesDetails allPlacesDetails) {
        super(useCaseScheduler);
        this.allPlacesDetails = allPlacesDetails;
    }

    private Single<String> validateParams(String placeId) {
        return Single.fromCallable(() -> {
            if (placeId == null)
                throw new ParameterException("You entered a null value");
            return placeId;
        });
    }

    @Override
    protected Single<Response<PlaceDetails>> build(String placeId) {
        return validateParams(placeId)
                .flatMap(allPlacesDetails::withId);
    }
}
