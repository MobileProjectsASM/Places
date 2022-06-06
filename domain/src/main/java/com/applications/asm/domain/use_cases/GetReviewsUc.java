package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.exception.GetReviewsError;
import com.applications.asm.domain.exception.GetReviewsException;
import com.applications.asm.domain.exception.PlacesRepositoryError;
import com.applications.asm.domain.exception.PlacesRepositoryException;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.List;
import java.util.logging.Logger;

import io.reactivex.rxjava3.core.Single;

public class GetReviewsUc extends SingleUseCase<List<Review>, String> {
    private final PlacesRepository placesRepository;
    private static final Logger log = Logger.getLogger("com.applications.asm.domain.use_cases.GetReviewsUc");

    public GetReviewsUc(UseCaseScheduler useCaseScheduler, PlacesRepository placesRepository) {
        super(useCaseScheduler);
        this.placesRepository = placesRepository;
    }

    private Single<String> validateParams(String placeId) {
        return Single.fromCallable(() -> {
            if (placeId == null)
                throw new GetReviewsException(GetReviewsError.NULL_ID);
            return placeId;
        });
    }

    @Override
    protected Single<List<Review>> build(String placeId) {
        return validateParams(placeId)
                .flatMap(placesRepository::getReviews)
                .doOnError(throwable -> {
                    Exception exception = (Exception) throwable;
                    if(exception instanceof PlacesRepositoryException) {
                        PlacesRepositoryError placesRepositoryError = ((PlacesRepositoryException) exception).getError();
                        switch(placesRepositoryError) {
                            case CONNECTION_WITH_SERVER_ERROR:
                                log.info(getClass().getName() + ": " + placesRepositoryError.getMessage());
                                throw new GetReviewsException(GetReviewsError.CONNECTION_WITH_SERVER_ERROR);
                            case DECODING_RESPONSE_ERROR:
                            case CREATE_REQUEST_ERROR:
                            case DO_REQUEST_ERROR:
                                log.info(getClass().getName() + ": " + placesRepositoryError.getMessage());
                                throw new GetReviewsException(GetReviewsError.REQUEST_RESPONSE_ERROR);
                            case RESPONSE_NULL:
                                log.info(getClass().getName() + ": " + placesRepositoryError.getMessage());
                                throw new GetReviewsException(GetReviewsError.RESPONSE_NULL);
                            case SERVER_ERROR:
                                log.info(getClass().getName() + ": " + placesRepositoryError.getMessage());
                                throw new GetReviewsException(GetReviewsError.SERVER_ERROR);
                            case NETWORK_ERROR:
                                log.info(getClass().getName() + ": " +placesRepositoryError.getMessage());
                                throw new GetReviewsException(GetReviewsError.NETWORK_ERROR);
                        }
                    }
                });
    }
}
