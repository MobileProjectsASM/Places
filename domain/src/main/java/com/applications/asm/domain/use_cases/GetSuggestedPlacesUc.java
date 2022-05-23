package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.SuggestedPlace;
import com.applications.asm.domain.entities.Validators;
import com.applications.asm.domain.exception.GetSuggestedPlacesError;
import com.applications.asm.domain.exception.GetSuggestedPlacesException;
import com.applications.asm.domain.exception.PlacesRepositoryError;
import com.applications.asm.domain.exception.PlacesRepositoryException;
import com.applications.asm.domain.executor.PostExecutionThread;
import com.applications.asm.domain.executor.ThreadExecutor;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.UseCase;

import java.util.List;
import java.util.logging.Logger;

import io.reactivex.rxjava3.core.Observable;

public class GetSuggestedPlacesUc extends UseCase<List<SuggestedPlace>, GetSuggestedPlacesUc.Params> {
    private final PlacesRepository placesRepository;
    private final Validators validators;
    private final Logger log = Logger.getLogger("com.applications.asm.domain.use_cases.GetSuggestedPlacesUc");

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

    public GetSuggestedPlacesUc(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PlacesRepository placesRepository, Validators validators) {
        super(threadExecutor, postExecutionThread);
        this.placesRepository = placesRepository;
        this.validators = validators;
    }

    @Override
    public Observable<List<SuggestedPlace>> buildUseCaseObservable(Params params) {
        return Observable.fromCallable(() -> getSuggestedPlaces(params.place, params.longitude, params.latitude));
    }

    private List<SuggestedPlace> getSuggestedPlaces(String place, Double longitude, Double latitude) throws GetSuggestedPlacesException {
        try {
            if(place == null || longitude == null || latitude == null)
                throw new GetSuggestedPlacesException(GetSuggestedPlacesError.ANY_VALUE_IS_NULL);
            if(!validators.validateLatitudeRange(latitude) || !validators.validateLongitudeRange(longitude))
                throw new GetSuggestedPlacesException(GetSuggestedPlacesError.LAT_LON_OUT_OF_RANGE);
            return placesRepository.getSuggestedPlaces(place, longitude, latitude);
        } catch (PlacesRepositoryException e) {
            PlacesRepositoryError placesRepositoryError = e.getError();
            String TAG = "GetSuggestedPlacesUc";
            switch (placesRepositoryError) {
                case CONNECTION_WITH_SERVER_ERROR:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetSuggestedPlacesException(GetSuggestedPlacesError.CONNECTION_WITH_SERVER_ERROR);
                case DECODING_RESPONSE_ERROR:
                case CREATE_REQUEST_ERROR:
                case DO_REQUEST_ERROR:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetSuggestedPlacesException(GetSuggestedPlacesError.REQUEST_RESPONSE_ERROR);
                case RESPONSE_NULL:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetSuggestedPlacesException(GetSuggestedPlacesError.RESPONSE_NULL);
                default:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetSuggestedPlacesException(GetSuggestedPlacesError.SERVER_ERROR);
            }
        }
    }

}
