package com.applications.asm.data.sources;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface LocationDataSourceSP {
    Completable saveLocation(Double latitude, Double longitude);
    Single<Double> getLatitude();
    Single<Double> getLongitude();
}
