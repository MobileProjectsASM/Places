package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.exception.ConnectionServer;
import com.applications.asm.domain.executor.PostExecutionThread;
import com.applications.asm.domain.executor.ThreadExecutor;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.UseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import jdk.internal.org.jline.utils.Log;

public class GetReviewsUc extends UseCase<List<Review>, String> {
    private final PlacesRepository placesRepository;
    private final String TAG = "GetReviewsUc";

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
            Log.error(TAG + " : " + connectionServer.getMessage());
            return new ArrayList<>();
        }
    }
}
