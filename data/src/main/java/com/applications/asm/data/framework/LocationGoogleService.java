package com.applications.asm.data.framework;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.applications.asm.data.R;
import com.applications.asm.data.exception.LocationException;
import com.applications.asm.data.model.CoordinatesModel;
import com.applications.asm.data.sources.LocationDataSource;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import io.reactivex.rxjava3.core.Single;

public class LocationGoogleService implements LocationDataSource {
    private final Context context;
    private final CancellationTokenSource cancellationTokenSource;

    public LocationGoogleService(Context context, CancellationTokenSource cancellationTokenSource) {
        this.context = context;
        this.cancellationTokenSource = cancellationTokenSource;
    }

    @SuppressLint("MissingPermission")
    @Override
    public Single<CoordinatesModel> getCurrentLocation() {
        return Single.fromCallable(() -> {
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
            Task<Location> locationTask = fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.getToken());
            Location location = Tasks.await(locationTask);
            if(location != null)
                return new CoordinatesModel(location.getLatitude(), location.getLongitude());
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(!isLocationEnabled)
                throw new LocationException("Location is null because the GPS is disabled");
            throw new LocationException("No registered location");
        });
    }
}
