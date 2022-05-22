package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.exception.GetPlaceDetailError;
import com.applications.asm.domain.exception.GetPlaceDetailException;
import com.applications.asm.domain.exception.PlacesRepositoryError;
import com.applications.asm.domain.exception.PlacesRepositoryException;
import com.applications.asm.domain.executor.PostExecutionThread;
import com.applications.asm.domain.executor.ThreadExecutor;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.UseCase;

import java.util.logging.Logger;

import io.reactivex.rxjava3.core.Observable;
import jdk.internal.org.jline.utils.Log;

public class GetPlaceDetailsUc extends UseCase<PlaceDetails, String> {
    private final PlacesRepository placesRepository;
    private final Logger log = Logger.getLogger("com.applications.asm.domain.use_cases.GetPlaceDetailsUc");

    public GetPlaceDetailsUc(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PlacesRepository placesRepository) {
        super(threadExecutor, postExecutionThread);
        this.placesRepository = placesRepository;
    }

    @Override
    public Observable<PlaceDetails> buildUseCaseObservable(String placeId) {
        return Observable.fromCallable(() -> getDetails(placeId));
    }

    private PlaceDetails getDetails(String placeId) throws GetPlaceDetailException {
        if (placeId == null)
            throw new GetPlaceDetailException(GetPlaceDetailError.NULL_ID);
        try {
            return placesRepository.getPlaceDetails(placeId);
        } catch (PlacesRepositoryException e) {
            PlacesRepositoryError placesRepositoryError = e.getError();
            String TAG = "GetPlaceDetailsUc";
            switch (placesRepositoryError) {
                case CONNECTION_WITH_SERVER_ERROR:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetPlaceDetailException(GetPlaceDetailError.CONNECTION_WITH_SERVER_ERROR);
                case DECODING_RESPONSE_ERROR:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetPlaceDetailException(GetPlaceDetailError.REQUEST_RESPONSE_ERROR);
                default:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetPlaceDetailException(GetPlaceDetailError.RESPONSE_NULL);
            }
        }
    }
}
