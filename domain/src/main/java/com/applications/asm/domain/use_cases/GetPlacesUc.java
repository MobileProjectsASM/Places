package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.ValidatorsImpl;
import com.applications.asm.domain.exception.GetPlacesError;
import com.applications.asm.domain.exception.GetPlacesException;
import com.applications.asm.domain.exception.PlacesRepositoryError;
import com.applications.asm.domain.exception.PlacesRepositoryException;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.List;
import java.util.logging.Logger;

import io.reactivex.rxjava3.core.Single;

public class GetPlacesUc extends SingleUseCase<List<Place>, GetPlacesUc.Params> {
    private final PlacesRepository placesRepository;
    private final ValidatorsImpl validatorsImpl;

    private static final Logger logger = Logger.getLogger("com.applications.asm.domain.use_cases.GetPlacesUc");

    public static class Params {
        private final String placeToFind;
        private final Double latitude;
        private final Double longitude;
        private final Integer radius;
        private final List<String> categories;
        private final Integer page;

        private Params(String placeToFind, Double latitude, Double longitude, Integer radius, List<String> categories, Integer page) {
            this.placeToFind = placeToFind;
            this.latitude = latitude;
            this.longitude = longitude;
            this.radius = radius;
            this.categories = categories;
            this.page = page;
        }

        public static Params forFilterPlaces(String placeToFind, Double latitude, Double longitude, Integer radius, List<String> categories, Integer page) {
            return new Params(placeToFind, latitude, longitude, radius, categories,page);
        }
    }

    public GetPlacesUc(UseCaseScheduler useCaseScheduler, PlacesRepository placesRepository, ValidatorsImpl validatorsImpl) {
        super(useCaseScheduler);
        this.placesRepository = placesRepository;
        this.validatorsImpl = validatorsImpl;
    }

    private Single<Params> validateParams(Params params) {
        return Single.fromCallable(() -> {
            if(params.placeToFind == null || params.latitude == null || params.longitude == null || params.radius == null || params.categories == null || params.page == null)
                throw new GetPlacesException(GetPlacesError.ANY_VALUE_IS_NULL);
            if(!validatorsImpl.validateLatitudeRange(params.latitude) || !validatorsImpl.validateLongitudeRange(params.longitude))
                throw new GetPlacesException(GetPlacesError.LAT_LON_OUT_OF_RANGE);
            if(!validatorsImpl.validateRadiusRange(params.radius))
                throw new GetPlacesException(GetPlacesError.NEGATIVE_RADIUS);
            if(!validatorsImpl.validatePage(params.page))
                throw new GetPlacesException(GetPlacesError.PAGE_OUT_OF_RANGE);
            return params;
        });
    }

    @Override
    protected Single<List<Place>> build(Params params) {
        return validateParams(params)
                .flatMap(param -> placesRepository.getPlaces(param.placeToFind, param.longitude, param.latitude, param.radius, param.categories, param.page))
                .doOnError(throwable -> {
                    Exception exception = (Exception) throwable;
                    if(exception instanceof PlacesRepositoryException) {
                        PlacesRepositoryError placesRepositoryError = ((PlacesRepositoryException) exception).getError();
                        switch (placesRepositoryError) {
                            case CONNECTION_WITH_SERVER_ERROR:
                                logger.severe(getClass().getName() + ": " + placesRepositoryError.getMessage());
                                throw new GetPlacesException(GetPlacesError.CONNECTION_WITH_SERVER_ERROR);
                            case DECODING_RESPONSE_ERROR:
                            case CREATE_REQUEST_ERROR:
                            case DO_REQUEST_ERROR:
                                logger.severe(getClass().getName() + ": " + placesRepositoryError.getMessage());
                                throw new GetPlacesException(GetPlacesError.REQUEST_RESPONSE_ERROR);
                            case PAGE_OUT_OF_RANGE:
                                logger.severe(getClass().getName() + ": " + placesRepositoryError.getMessage());
                                throw new GetPlacesException(GetPlacesError.NEGATIVE_RADIUS);
                            case RESPONSE_NULL:
                                logger.severe(getClass().getName() + ": " + placesRepositoryError.getMessage());
                                throw new GetPlacesException(GetPlacesError.RESPONSE_NULL);
                            default:
                                logger.severe(getClass().getName() + ": " + placesRepositoryError.getMessage());
                                throw new GetPlacesException(GetPlacesError.SERVER_ERROR);
                        }
                    } else throw exception;
                });
    }
}
