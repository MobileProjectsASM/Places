package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Location;
import com.applications.asm.domain.repository.CacheRepository;
import com.applications.asm.domain.repository.LocationRepository;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import io.reactivex.rxjava3.core.Single;

public class LoadLocationUc extends SingleUseCase<Location, Object> {
    private final CacheRepository cacheRepository;

    public LoadLocationUc(UseCaseScheduler useCaseScheduler, CacheRepository cacheRepository) {
        super(useCaseScheduler);
        this.cacheRepository = cacheRepository;
    }

    @Override
    protected Single<Location> build(Object o) {
        return cacheRepository.getLocation();
    }
}
