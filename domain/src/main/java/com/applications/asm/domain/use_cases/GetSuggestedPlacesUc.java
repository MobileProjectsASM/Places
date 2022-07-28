package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.entities.SuggestedPlace;
import com.applications.asm.domain.exception.ParameterException;
import com.applications.asm.domain.repository.Validators;
import com.applications.asm.domain.repository.AllSuggestedPlaces;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetSuggestedPlacesUc extends SingleUseCase<Response<List<SuggestedPlace>>, GetSuggestedPlacesUc.Params> {
    private final AllSuggestedPlaces allSuggestedPlaces;
    private final Validators validators;

    public static class Params {
        private final String place;
        private final Coordinates coordinates;

        private Params(String place, Coordinates coordinates) {
            this.place = place;
            this.coordinates = coordinates;
        }

        public static Params forSuggestedPlaces(String place, Coordinates coordinates) {
            return new Params(place, coordinates);
        }
    }

    public GetSuggestedPlacesUc(UseCaseScheduler useCaseScheduler, AllSuggestedPlaces allSuggestedPlaces, Validators validators) {
        super(useCaseScheduler);
        this.allSuggestedPlaces = allSuggestedPlaces;
        this.validators = validators;
    }

    private Single<Params> validateParams(Params params) {
        return Single.fromCallable(() -> {
            if(params.place == null || params.coordinates == null)
                throw new ParameterException("You entered a null value");
            if(!validators.validateLatitudeRange(params.coordinates.getLatitude()) || !validators.validateLongitudeRange(params.coordinates.getLongitude()))
                throw new ParameterException("Coordinates out of range");
            return params;
        });
    }

    @Override
    protected Single<Response<List<SuggestedPlace>>> build(Params params) {
        return validateParams(params)
                .flatMap(param -> allSuggestedPlaces.withThisCriteria(param.place, param.coordinates));
    }
}
