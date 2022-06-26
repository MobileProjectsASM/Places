package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Location;
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
        private final Location location;

        private Params(String place, Location location) {
            this.place = place;
            this.location = location;
        }

        public static Params forSuggestedPlaces(String place, Location location) {
            return new Params(place, location);
        }
    }

    public GetSuggestedPlacesUc(UseCaseScheduler useCaseScheduler, PlacesRepository placesRepository, Validators validators) {
        super(useCaseScheduler);
        this.placesRepository = placesRepository;
        this.validators = validators;
    }

    private Single<Params> validateParams(Params params) {
        return Single.fromCallable(() -> {
            if(params.place == null || params.location != null)
                throw new ClientException("Null value was entered");
            if(!validators.validateLatitudeRange(params.location.getLatitude()) || !validators.validateLongitudeRange(params.location.getLongitude()))
                throw new ClientException("Location input is invalid: " + "[" + params.location.getLatitude() + ", " + params.location.getLongitude() +"]");
            return params;
        });
    }

    @Override
    protected Single<List<SuggestedPlace>> build(Params params) {
        return validateParams(params)
                .flatMap(param -> placesRepository.getSuggestedPlaces(param.place, param.location));
    }
}
