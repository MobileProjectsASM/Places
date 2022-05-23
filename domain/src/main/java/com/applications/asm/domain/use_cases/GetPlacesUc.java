package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.Validators;
import com.applications.asm.domain.exception.GetPlacesError;
import com.applications.asm.domain.exception.GetPlacesException;
import com.applications.asm.domain.exception.PlacesRepositoryError;
import com.applications.asm.domain.exception.PlacesRepositoryException;
import com.applications.asm.domain.executor.PostExecutionThread;
import com.applications.asm.domain.executor.ThreadExecutor;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.UseCase;

import java.util.List;
import java.util.logging.Logger;

import io.reactivex.rxjava3.core.Observable;

public class GetPlacesUc extends UseCase<List<Place>, GetPlacesUc.Params> {
    private final PlacesRepository placesRepository;
    private final Validators validators;
    private final Logger log = Logger.getLogger("com.applications.asm.domain.use_cases.GetPlacesUc");

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

        public static Params forFilterPlaces(String placeToFind, Double latitude, Double longitude, Integer radius, List<String> categories, Integer init) {
            return new Params(placeToFind, latitude, longitude, radius, categories, init);
        }
    }

    public GetPlacesUc(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PlacesRepository placesRepository, Validators validators) {
        super(threadExecutor, postExecutionThread);
        this.placesRepository = placesRepository;
        this.validators = validators;
    }

    @Override
    public Observable<List<Place>> buildUseCaseObservable(Params params) {
        return Observable.fromCallable(() -> getPlaces(params.placeToFind, params.latitude, params.longitude, params.radius, params.categories, params.page));
    }

    private List<Place> getPlaces(String placeToFind, Double latitude, Double longitude, Integer radius, List<String> categories, Integer page) throws GetPlacesException {
        if(placeToFind == null || latitude == null || longitude == null || radius == null || categories == null || page == null)
            throw new GetPlacesException(GetPlacesError.ANY_VALUE_IS_NULL);
        if(!validators.validateLatitudeRange(latitude) || !validators.validateLongitudeRange(longitude))
            throw new GetPlacesException(GetPlacesError.LAT_LON_OUT_OF_RANGE);
        if(!validators.validateRadiusRange(radius))
            throw new GetPlacesException(GetPlacesError.NEGATIVE_RADIUS);
        if(!validators.validatePage(page))
            throw new GetPlacesException(GetPlacesError.PAGE_OUT_OF_RANGE);
        try {
            return placesRepository.getPlaces(placeToFind, longitude, latitude, radius, categories, page);
        } catch (PlacesRepositoryException e) {
            PlacesRepositoryError placesRepositoryError = e.getError();
            String TAG = "GetPlacesUc";
            switch (placesRepositoryError) {
                case CONNECTION_WITH_SERVER_ERROR:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetPlacesException(GetPlacesError.CONNECTION_WITH_SERVER_ERROR);
                case DECODING_RESPONSE_ERROR:
                case CREATE_REQUEST_ERROR:
                case DO_REQUEST_ERROR:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetPlacesException(GetPlacesError.REQUEST_RESPONSE_ERROR);
                case PAGE_OUT_OF_RANGE:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetPlacesException(GetPlacesError.NEGATIVE_RADIUS);
                case RESPONSE_NULL:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetPlacesException(GetPlacesError.RESPONSE_NULL);
                default:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetPlacesException(GetPlacesError.SERVER_ERROR);
            }
        }
    }
}
