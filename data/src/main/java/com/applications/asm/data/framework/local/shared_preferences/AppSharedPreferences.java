package com.applications.asm.data.framework.local.shared_preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.applications.asm.data.R;
import com.applications.asm.data.framework.local.shared_preferences.model.CoordinatesSP;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AppSharedPreferences implements LocalPersistenceClient {
    private static final String LATITUDE_KEY = "latitude";
    private static final String LONGITUDE_KEY = "longitude";
    private final SharedPreferences appPreferences;
    private final Context context;

    @Inject
    public AppSharedPreferences(SharedPreferences appPreferences, Context context) {
        this.appPreferences = appPreferences;
        this.context = context;
    }

    @Override
    public Completable saveCoordinates(CoordinatesSP coordinatesSP) {
        return Completable.create(emitter -> {
            SharedPreferences.Editor editor = appPreferences.edit();
            editor.putFloat(LATITUDE_KEY, (float) coordinatesSP.getLatitude());
            editor.putFloat(LONGITUDE_KEY, (float) coordinatesSP.getLongitude());
            editor.commit();
            emitter.onComplete();
        })
        .onErrorResumeNext(throwable -> {
            Exception exception = (Exception) throwable;
            if(exception instanceof SharedPreferencesException) {
                SharedPreferencesException sharedPreferencesException = (SharedPreferencesException) exception;
                Log.e(getClass().getName(), sharedPreferencesException.getMessage());
                return Completable.error(sharedPreferencesException);
            }
            Log.e(getClass().getName(), exception.getMessage());
            return Completable.error(new SharedPreferencesException(SharedPreferencesExceptionCodes.SHARED_PREFERENCES_ERROR, exception.getMessage()));
        });
    }

    @Override
    public Single<CoordinatesSP> getCoordinates() {
        return Single.fromCallable(() -> {
            boolean latitudeExists = appPreferences.contains(LATITUDE_KEY);
            boolean longitudeExists = appPreferences.contains(LONGITUDE_KEY);
            double latitude, longitude;
            if(latitudeExists && longitudeExists) {
                latitude = appPreferences.getFloat(LATITUDE_KEY, 0);
                longitude = appPreferences.getFloat(LONGITUDE_KEY, 0);
                return new CoordinatesSP(latitude, longitude);
            } else
                throw new SharedPreferencesException(SharedPreferencesExceptionCodes.NO_LOCATION_IN_PREFERENCES, context.getString(R.string.no_data_in_memory_error));
        })
        .onErrorResumeNext(throwable -> {
            Exception exception = (Exception) throwable;
            if(exception instanceof SharedPreferencesException) {
                SharedPreferencesException sharedPreferencesException = (SharedPreferencesException) exception;
                Log.e(getClass().getName(), sharedPreferencesException.getMessage());
                return Single.error(sharedPreferencesException);
            }
            Log.e(getClass().getName(), exception.getMessage());
            return Single.error(new SharedPreferencesException(SharedPreferencesExceptionCodes.SHARED_PREFERENCES_ERROR, exception.getMessage()));
        });
    }
}
