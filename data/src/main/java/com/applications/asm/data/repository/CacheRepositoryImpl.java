package com.applications.asm.data.repository;

import com.applications.asm.data.sources.CacheDataSourceSP;
import com.applications.asm.domain.entities.Location;
import com.applications.asm.domain.repository.CacheRepository;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class CacheRepositoryImpl implements CacheRepository {
    private final CacheDataSourceSP cacheDataSourceSP;

    public CacheRepositoryImpl(CacheDataSourceSP cacheDataSourceSP) {
        this.cacheDataSourceSP = cacheDataSourceSP;
    }

    @Override
    public Completable saveLocation(Double latitude, Double longitude) {
        return cacheDataSourceSP.saveLocation(latitude, longitude);
    }

    @Override
    public Single<Location> getLocation() {
        return Single.zip(cacheDataSourceSP.getLatitude(), cacheDataSourceSP.getLongitude(), Location::new);
    }
}
