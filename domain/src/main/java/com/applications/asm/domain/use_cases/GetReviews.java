package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.executor.PostExecutionThread;
import com.applications.asm.domain.executor.ThreadExecutor;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.UseCase;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class GetReviews extends UseCase<List<Review>, String> {
    private final PlacesRepository placesRepository;

    public GetReviews(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PlacesRepository placesRepository) {
        super(threadExecutor, postExecutionThread);
        this.placesRepository = placesRepository;
    }

    @Override
    public Observable<List<Review>> buildUseCaseObservable(String placeId) {
        return Observable.fromCallable(() -> placesRepository.getReviews(placeId));
    }
}
