package com.applications.asm.domain.repository;

import com.applications.asm.domain.entities.Location;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface CacheRepository {
    Completable saveLocation(Double latitude, Double longitude);
    Single<Location> getLocation();
}
