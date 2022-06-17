package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.exception.GetCategoryError;
import com.applications.asm.domain.exception.GetCategoryException;
import com.applications.asm.domain.exception.GetPlaceDetailError;
import com.applications.asm.domain.exception.GetPlaceDetailException;
import com.applications.asm.domain.exception.GetReviewsError;
import com.applications.asm.domain.exception.GetReviewsException;
import com.applications.asm.domain.exception.PlacesRepositoryError;
import com.applications.asm.domain.exception.PlacesRepositoryException;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.logging.Logger;

import io.reactivex.rxjava3.core.Single;

public class GetPlaceDetailsUc extends SingleUseCase<PlaceDetails, String> {
    private final PlacesRepository placesRepository;
    private static final Logger log = Logger.getLogger(GetPlaceDetailsUc.class.getName());

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
                                log.warning("Connection with server error: : " + placesRepositoryError.getMessage());
                                throw new GetCategoryException(GetCategoryError.CONNECTION_WITH_SERVER_ERROR);
                            case DECODING_RESPONSE_ERROR:
                            case CREATE_REQUEST_ERROR:
                            case DO_REQUEST_ERROR:
                                log.warning("Request error: " + placesRepositoryError.getMessage());
                                throw new GetCategoryException(GetCategoryError.REQUEST_RESPONSE_ERROR);
                            case RESPONSE_NULL:
                                log.warning("Response null: " + placesRepositoryError.getMessage());
                                throw new GetCategoryException(GetCategoryError.RESPONSE_NULL);
                            case SERVER_ERROR:
                                log.warning("Server error: " + placesRepositoryError.getMessage());
                                throw new GetReviewsException(GetReviewsError.SERVER_ERROR);
                            case NETWORK_ERROR:
                                log.warning("Network error: " +placesRepositoryError.getMessage());
                                throw new GetReviewsException(GetReviewsError.NETWORK_ERROR);
                        }
                    } throw exception;
                });
    }
}
