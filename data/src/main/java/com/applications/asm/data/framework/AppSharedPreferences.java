package com.applications.asm.data.framework;

import android.content.SharedPreferences;

import com.applications.asm.data.sources.CacheDataSourceSP;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AppSharedPreferences implements CacheDataSourceSP {
    private static final String LATITUDE_KEY = "latitude";
    private static final String LONGITUDE_KEY = "longitude";
    private final SharedPreferences appPreferences;

    public AppSharedPreferences(SharedPreferences appPreferences) {
        this.appPreferences = appPreferences;
    }

    @Override
    public Completable saveLocation(Double latitude, Double longitude) {
        return Completable.create(emitter -> {
            SharedPreferences.Editor editor = appPreferences.edit();
            editor.putFloat(LATITUDE_KEY, latitude.floatValue());
            editor.putFloat(LONGITUDE_KEY, longitude.floatValue());
            editor.commit();
            emitter.onComplete();
        });
    }

    @Override
    public Single<Double> getLatitude() {
        return Single.fromCallable(() -> Double.valueOf(appPreferences.getFloat(LATITUDE_KEY, 91)));
    }

    @Override
    public Single<Double> getLongitude() {
        return Single.fromCallable(() -> Double.valueOf(appPreferences.getFloat(LONGITUDE_KEY, 181)));
    }
}
