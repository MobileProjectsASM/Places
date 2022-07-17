package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.exception.ClientException;
import com.applications.asm.domain.repository.AllPlacesDetails;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import io.reactivex.rxjava3.core.Single;

public class GetPlaceDetailsUc extends SingleUseCase<Response<PlaceDetails>, String> {
    private final AllPlacesDetails allPlacesDetails;

    public GetPlaceDetailsUc(UseCaseScheduler useCaseScheduler, AllPlacesDetails allPlacesDetails) {
        super(useCaseScheduler);
        this.allPlacesDetails = allPlacesDetails;
    }

    private Single<String> validateParams(String placeId) {
        return Single.fromCallable(() -> {
            if (placeId == null)
                throw new ClientException("Null value was entered");
            return placeId;
        });
    }

    @Override
    protected Single<Response<PlaceDetails>> build(String placeId) {
        return validateParams(placeId)
                .flatMap(allPlacesDetails::withId);
    }
}
