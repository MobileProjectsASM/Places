package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.SuggestedPlace;
import com.applications.asm.domain.entities.Validators;
import com.applications.asm.domain.exception.GetReviewsError;
import com.applications.asm.domain.exception.GetReviewsException;
import com.applications.asm.domain.exception.GetSuggestedPlacesError;
import com.applications.asm.domain.exception.GetSuggestedPlacesException;
import com.applications.asm.domain.exception.PlacesRepositoryError;
import com.applications.asm.domain.exception.PlacesRepositoryException;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.List;
import java.util.logging.Logger;

import io.reactivex.rxjava3.core.Single;

public class GetSuggestedPlacesUc extends SingleUseCase<List<SuggestedPlace>, GetSuggestedPlacesUc.Params> {
    private final PlacesRepository placesRepository;
    private final Validators validators;
    private static final Logger log = Logger.getLogger(GetSuggestedPlacesUc.class.getName());

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
                throw new GetSuggestedPlacesException(GetSuggestedPlacesError.ANY_VALUE_IS_NULL);
            if(!validators.validateLatitudeRange(params.latitude) || !validators.validateLongitudeRange(params.longitude))
                throw new GetSuggestedPlacesException(GetSuggestedPlacesError.LAT_LON_OUT_OF_RANGE);
            return params;
        });
    }

    @Override
    protected Single<List<SuggestedPlace>> build(Params params) {
        return validateParams(params)
                .flatMap(param -> placesRepository.getSuggestedPlaces(param.place, param.longitude, param.latitude))
                .doOnError(throwable -> {
                    Exception exception = (Exception) throwable;
                    if(exception instanceof PlacesRepositoryException) {
                        PlacesRepositoryError placesRepositoryError = ((PlacesRepositoryException) exception).getError();
                        switch (placesRepositoryError) {
                            case CONNECTION_WITH_SERVER_ERROR:
                                log.warning("Connection with server error: " + placesRepositoryError.getMessage());
                                throw new GetSuggestedPlacesException(GetSuggestedPlacesError.CONNECTION_WITH_SERVER_ERROR);
                            case DECODING_RESPONSE_ERROR:
                            case CREATE_REQUEST_ERROR:
                            case DO_REQUEST_ERROR:
                                log.warning( "Request error: " + placesRepositoryError.getMessage());
                                throw new GetSuggestedPlacesException(GetSuggestedPlacesError.REQUEST_RESPONSE_ERROR);
                            case RESPONSE_NULL:
                                log.warning( "Response null: " + placesRepositoryError.getMessage());
                                throw new GetSuggestedPlacesException(GetSuggestedPlacesError.RESPONSE_NULL);
                            case SERVER_ERROR:
                                log.warning("Server error: " + placesRepositoryError.getMessage());
                                throw new GetReviewsException(GetReviewsError.SERVER_ERROR);
                            case NETWORK_ERROR:
                                log.warning("Network error: " +placesRepositoryError.getMessage());
                                throw new GetReviewsException(GetReviewsError.NETWORK_ERROR);
                        }
                    } else throw exception;
                });
    }
}
