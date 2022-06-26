package com.applications.asm.domain.repository;

import com.applications.asm.domain.entities.Location;

import io.reactivex.rxjava3.core.Single;

public interface LocationRepository {
    Single<Location> getCurrentLocation();
}
