package com.applications.asm.data.framework.local.gps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import io.reactivex.rxjava3.core.Single;

public class GpsClientImpl implements GpsClient {
    private final Context context;
    private final CancellationTokenSource cancellationTokenSource;

    public GpsClientImpl(Context context, CancellationTokenSource cancellationTokenSource) {
        this.context = context;
        this.cancellationTokenSource = cancellationTokenSource;
    }

    @SuppressLint("MissingPermission")
    @Override
    public Single<Location> getCurrentLocation() {
        return Single.fromCallable(() -> {
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
            Task<Location> locationTask = fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.getToken());
            Location location = Tasks.await(locationTask);
            if(location != null)
                return location;
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(!isLocationEnabled)
                throw new Exception("Location is null because the GPS is disabled");
            throw new Exception("No registered location");
        });
    }
}
