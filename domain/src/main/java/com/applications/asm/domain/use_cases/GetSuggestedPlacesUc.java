package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.SuggestedPlace;
import com.applications.asm.domain.entities.Validators;
import com.applications.asm.domain.exception.ClientException;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetSuggestedPlacesUc extends SingleUseCase<List<SuggestedPlace>, GetSuggestedPlacesUc.Params> {
    private final PlacesRepository placesRepository;
    private final Validators validators;

    public static class Params {
        private final String place;
        private final Double longitude;
        private final Double latitude;

        private Params(String place, Double longitude, Double latitude) {
            this.place = place;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public static Params forSuggestedPlaces(String place, Double longitude, Double latitude) {
            return new Params(place, longitude, latitude);
        }
    }

    public GetSuggestedPlacesUc(UseCaseScheduler useCaseScheduler, PlacesRepository placesRepository, Validators validators) {
        super(useCaseScheduler);
        this.placesRepository = placesRepository;
        this.validators = validators;
    }

    private Single<Params> validateParams(Params params) {
        return Single.fromCallable(() -> {
            if(params.place == null || params.longitude == null || params.latitude == null)
                throw new ClientException("Null value was entered");
            if(!validators.validateLatitudeRange(params.latitude) || !validators.validateLongitudeRange(params.longitude))
                throw new ClientException("Location input is invalid: " + "[" + params.latitude + ", " + params.longitude +"]");
            return params;
        });
    }

    @Override
    protected Single<List<SuggestedPlace>> build(Params params) {
        return validateParams(params)
                .flatMap(param -> placesRepository.getSuggestedPlaces(param.place, param.longitude, param.latitude));
    }
}
