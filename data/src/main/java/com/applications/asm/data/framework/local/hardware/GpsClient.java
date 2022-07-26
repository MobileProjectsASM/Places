package com.applications.asm.data.framework.local.hardware;

import android.location.Location;

import io.reactivex.rxjava3.core.Single;

public interface GpsClient {
    Single<Location> getCurrentLocation();
}
