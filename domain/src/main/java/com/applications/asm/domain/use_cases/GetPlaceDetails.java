package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Date;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.executor.PostExecutionThread;
import com.applications.asm.domain.executor.ThreadExecutor;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.repository.System;
import com.applications.asm.domain.use_cases.base.UseCase;

import io.reactivex.rxjava3.core.Observable;

public class GetPlaceDetails extends UseCase<PlaceDetails, String> {
    private final PlacesRepository placesRepository;
    private final System system;

    public GetPlaceDetails(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PlacesRepository placesRepository, System system) {
        super(threadExecutor, postExecutionThread);
        this.placesRepository = placesRepository;
        this.system = system;
    }

    @Override
    public Observable<PlaceDetails> buildUseCaseObservable(String placeId) {
        return Observable.fromCallable(() -> getDetails(placeId));
    }

    private PlaceDetails getDetails(String placeId) {
        Date currentDate = system.getCurrentDate();
        PlaceDetails placeDetails = placesRepository.getPlaceDetails(placeId);
        placeDetails.setIsOpen(currentDate);
        return placeDetails;
    }
}
