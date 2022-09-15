package com.applications.asm.data.framework.local.hardware;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.applications.asm.data.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GpsClientImpl implements GpsClient {
    private final Context context;
    private final FusedLocationProviderClient fusedLocationProviderClient;

    @Inject
    public GpsClientImpl(Context context, FusedLocationProviderClient fusedLocationProviderClient) {
        this.context = context;
        this.fusedLocationProviderClient = fusedLocationProviderClient;
    }

    @SuppressLint("MissingPermission")
    @Override
    public Single<Location> getCurrentLocation() {
        return Single.fromCallable(() -> {
            Task<Location> locationTask = fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, new CancellationTokenSource().getToken());
            Location location = Tasks.await(locationTask);
            if(location != null)
                return location;
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(!isLocationEnabled)
                throw new HardwareException(HardwareExceptionCodes.GPS_DISABLED, context.getString(R.string.gps_disabled_error));
            throw new HardwareException(HardwareExceptionCodes.LOCATION_UNREGISTERED, context.getString(R.string.unregistered_location));
        })
        .onErrorResumeNext(throwable -> {
            Exception exception = (Exception) throwable;
            if(exception instanceof HardwareException) {
                HardwareException hardwareException = (HardwareException) exception;
                Log.e(getClass().getName(), hardwareException.getMessage());
                return Single.error(hardwareException);
            }
            Log.e(getClass().getName(), exception.getMessage());
            return Single.error(new HardwareException(HardwareExceptionCodes.HARDWARE_ERROR, exception.getMessage()));
        });
    }
}
