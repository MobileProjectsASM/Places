package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Location;
import com.applications.asm.domain.executor.PostExecutionThread;
import com.applications.asm.domain.executor.ThreadExecutor;
import com.applications.asm.domain.repository.LocationRepository;
import com.applications.asm.domain.use_cases.base.UseCase;

import io.reactivex.rxjava3.core.Observable;

public class LoadLocationUc extends UseCase<Location, Object> {
    private final LocationRepository locationRepository;

    public LoadLocationUc(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, LocationRepository locationRepository) {
        super(threadExecutor, postExecutionThread);
        this.locationRepository = locationRepository;
    }

    @Override
    public Observable<Location> buildUseCaseObservable(Object o) {
        return Observable.fromCallable(locationRepository::getLocation);
    }
}
