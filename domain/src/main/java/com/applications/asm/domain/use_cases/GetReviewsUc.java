package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.exception.ConnectionServer;
import com.applications.asm.domain.executor.PostExecutionThread;
import com.applications.asm.domain.executor.ThreadExecutor;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.UseCase;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class GetReviewsUc extends UseCase<List<Review>, String> {
    private final PlacesRepository placesRepository;
    private final String TAG = "GetReviewsUc";
    private final static Logger logger = LoggerFactory.getLogger(GetReviewsUc.class);


    public GetReviewsUc(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PlacesRepository placesRepository) {
        super(threadExecutor, postExecutionThread);
        this.placesRepository = placesRepository;
    }

    @Override
    public Observable<List<Review>> buildUseCaseObservable(String placeId) {
        return Observable.fromCallable(() -> getReviews(placeId));
    }

    private List<Review> getReviews(String placeId) {
        try {
            return placesRepository.getReviews(placeId);
        } catch (ConnectionServer connectionServer) {
            logger.error(TAG, connectionServer);
            return new ArrayList<>();
        }
    }
}
