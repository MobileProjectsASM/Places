package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Location;
import com.applications.asm.domain.repository.LocationRepository;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.logging.Logger;

import io.reactivex.rxjava3.core.Single;

public class LoadLocationUc extends SingleUseCase<Location, Object> {
    private final LocationRepository locationRepository;
    private static final Logger logger = Logger.getLogger("com.applications.asm.domain.use_cases.LoadLocationUc");

    public LoadLocationUc(UseCaseScheduler useCaseScheduler, LocationRepository locationRepository) {
        super(useCaseScheduler);
        this.locationRepository = locationRepository;
    }

    @Override
    protected Single<Location> build(Object o) {
        return locationRepository.getLocation();
    }
}
