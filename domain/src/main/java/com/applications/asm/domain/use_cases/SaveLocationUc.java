package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.exception.SaveLocationError;
import com.applications.asm.domain.exception.SaveLocationException;
import com.applications.asm.domain.repository.LocationRepository;
import com.applications.asm.domain.use_cases.base.CompletableUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class SaveLocationUc extends CompletableUseCase<SaveLocationUc.Params> {
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

    public SaveLocationUc(UseCaseScheduler useCaseScheduler, LocationRepository locationRepository) {
        super(useCaseScheduler);
        this.locationRepository = locationRepository;
    }

    private Single<Params> validateParams(Params params) {
        return Single.fromCallable(() -> {
            if(params.latitude == null || params.longitude == null) throw new SaveLocationException(SaveLocationError.ANY_VALUE_IS_NULL);
            return params;
        });
    }

    @Override
    protected Completable build(Params params) {
        return validateParams(params).flatMapCompletable(param -> locationRepository.saveLocation(param.latitude, params.longitude));
    }
}
