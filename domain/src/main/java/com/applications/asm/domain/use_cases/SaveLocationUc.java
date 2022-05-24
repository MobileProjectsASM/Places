package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.exception.SaveLocationError;
import com.applications.asm.domain.exception.SaveLocationException;
import com.applications.asm.domain.executor.PostExecutionThread;
import com.applications.asm.domain.executor.ThreadExecutor;
import com.applications.asm.domain.repository.LocationRepository;
import com.applications.asm.domain.use_cases.base.UseCase;

import io.reactivex.rxjava3.core.Observable;

public class SaveLocationUc extends UseCase<Boolean, SaveLocationUc.Params> {
    private final LocationRepository locationRepository;

    public static class Params {
        private final Double latitude;
        private final Double longitude;

        private Params(Double longitude, Double latitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public Params forSaveLocation(Double latitude, Double longitude) {
            return new Params(longitude, latitude);
        }
    }

    public SaveLocationUc(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, LocationRepository locationRepository) {
        super(threadExecutor, postExecutionThread);
        this.locationRepository = locationRepository;
    }

    @Override
    public Observable<Boolean> buildUseCaseObservable(Params params) {
        return Observable.fromCallable(() -> saveLocation(params.latitude, params.longitude));
    }

    private Boolean saveLocation(Double latitude, Double longitude) throws SaveLocationException {
        if(latitude == null && longitude == null) throw new SaveLocationException(SaveLocationError.ANY_VALUE_IS_NULL);
        return locationRepository.saveLocation(latitude, longitude);
    }
}
