package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.exception.GetReviewsError;
import com.applications.asm.domain.exception.GetReviewsException;
import com.applications.asm.domain.exception.PlacesRepositoryError;
import com.applications.asm.domain.exception.PlacesRepositoryException;
import com.applications.asm.domain.executor.PostExecutionThread;
import com.applications.asm.domain.executor.ThreadExecutor;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.UseCase;

import java.util.List;
import java.util.logging.Logger;

import io.reactivex.rxjava3.core.Observable;

public class GetReviewsUc extends UseCase<List<Review>, String> {
    private final PlacesRepository placesRepository;
    private final Logger log = Logger.getLogger("com.applications.asm.domain.use_cases.GetReviewsUc");

    public GetReviewsUc(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PlacesRepository placesRepository) {
        super(threadExecutor, postExecutionThread);
        this.placesRepository = placesRepository;
    }

    @Override
    public Observable<List<Review>> buildUseCaseObservable(String placeId) {
        return Observable.fromCallable(() -> getReviews(placeId));
    }

    private List<Review> getReviews(String placeId) throws GetReviewsException {
        if (placeId == null)
            throw new GetReviewsException(GetReviewsError.NULL_ID);
        try {
            return placesRepository.getReviews(placeId);
        } catch (PlacesRepositoryException e) {
            PlacesRepositoryError placesRepositoryError = e.getError();
            String TAG = "GetReviewsUc";
            switch(placesRepositoryError) {
                case CONNECTION_WITH_SERVER_ERROR:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetReviewsException(GetReviewsError.CONNECTION_WITH_SERVER_ERROR);
                case DECODING_RESPONSE_ERROR:
                case CREATE_REQUEST_ERROR:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetReviewsException(GetReviewsError.REQUEST_RESPONSE_ERROR);
                case RESPONSE_NULL:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetReviewsException(GetReviewsError.RESPONSE_NULL);
                default:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetReviewsException(GetReviewsError.SERVER_ERROR);
            }
        }
    }
}
