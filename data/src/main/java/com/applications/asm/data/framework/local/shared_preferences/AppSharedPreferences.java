package com.applications.asm.data.framework.local.shared_preferences;

import android.content.SharedPreferences;

import com.applications.asm.data.framework.local.shared_preferences.model.CoordinatesSP;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AppSharedPreferences implements LocalPersistenceClient {
    private static final String LATITUDE_KEY = "latitude";
    private static final String LONGITUDE_KEY = "longitude";
    private final SharedPreferences appPreferences;

    public AppSharedPreferences(SharedPreferences appPreferences) {
        this.appPreferences = appPreferences;
    }

    @Override
    public Completable saveCoordinates(CoordinatesSP coordinatesSP) {
        return Completable.create(emitter -> {
            SharedPreferences.Editor editor = appPreferences.edit();
            editor.putFloat(LATITUDE_KEY, (float) coordinatesSP.getLatitude());
            editor.putFloat(LONGITUDE_KEY, (float) coordinatesSP.getLongitude());
            editor.commit();
            emitter.onComplete();
        });
    }

    @Override
    public Single<CoordinatesSP> getCoordinates() {
        return Single.fromCallable(() -> {
            double latitude = appPreferences.getFloat(LATITUDE_KEY, 91);
            double longitude = appPreferences.getFloat(LONGITUDE_KEY, 181);
            return new CoordinatesSP(latitude, longitude);
        });
    }
}
