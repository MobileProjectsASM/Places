package com.applications.asm.data.framework.local.gps;

import android.location.Location;

import io.reactivex.rxjava3.core.Single;

public interface GpsClient {
    Single<Location> getCurrentLocation();
}
