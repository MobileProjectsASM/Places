package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.exception.GetPlaceDetailError;
import com.applications.asm.domain.exception.GetPlaceDetailException;
import com.applications.asm.domain.exception.PlacesRepositoryError;
import com.applications.asm.domain.exception.PlacesRepositoryException;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.logging.Logger;

import io.reactivex.rxjava3.core.Single;

public class GetPlaceDetailsUc extends SingleUseCase<PlaceDetails, String> {
    private final PlacesRepository placesRepository;
    private static final Logger log = Logger.getLogger("com.applications.asm.domain.use_cases.GetPlaceDetailsUc");

    public GetPlaceDetailsUc(UseCaseScheduler useCaseScheduler, PlacesRepository placesRepository) {
        super(useCaseScheduler);
        this.placesRepository = placesRepository;
    }

    private Single<String> validateParams(String placeId) {
        return Single.fromCallable(() -> {
            if (placeId == null)
                throw new GetPlaceDetailException(GetPlaceDetailError.NULL_ID);
            return placeId;
        });
    }

    @Override
    protected Single<PlaceDetails> build(String placeId) {
        return validateParams(placeId)
                .flatMap(placesRepository::getPlaceDetails)
                .doOnError(throwable -> {
                    Exception exception = ((Exception) throwable);
                    if(exception instanceof PlacesRepositoryException) {
                        PlacesRepositoryError placesRepositoryError = ((PlacesRepositoryException) exception).getError();
                        switch (placesRepositoryError) {
                            case CONNECTION_WITH_SERVER_ERROR:
                                log.info(getClass().getName() + ": " + placesRepositoryError.getMessage());
                                throw new GetPlaceDetailException(GetPlaceDetailError.CONNECTION_WITH_SERVER_ERROR);
                            case DECODING_RESPONSE_ERROR:
                            case CREATE_REQUEST_ERROR:
                            case DO_REQUEST_ERROR:
                                log.info(getClass().getName() + ": " + placesRepositoryError.getMessage());
                                throw new GetPlaceDetailException(GetPlaceDetailError.REQUEST_RESPONSE_ERROR);
                            case RESPONSE_NULL:
                                log.info(getClass().getName() + ": " + placesRepositoryError.getMessage());
                                throw new GetPlaceDetailException(GetPlaceDetailError.RESPONSE_NULL);
                            default:
                                log.info(getClass().getName() + ": " + placesRepositoryError.getMessage());
                                throw new GetPlaceDetailException(GetPlaceDetailError.SERVER_ERROR);
                        }
                    } throw exception;
                });
    }
}
