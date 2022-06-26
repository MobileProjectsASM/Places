package com.applications.asm.data.sources;

import com.applications.asm.data.model.CoordinatesModel;

import io.reactivex.rxjava3.core.Single;

public interface LocationDataSource {
    Single<CoordinatesModel> getCurrentLocation();
}
