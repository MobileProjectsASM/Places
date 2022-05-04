package com.applications.asm.places.application;

import android.app.Application;

import com.applications.asm.places.R;
import com.applications.asm.places.di.components.ApplicationComponent;
import com.applications.asm.places.di.components.DaggerApplicationComponent;
import com.applications.asm.places.di.modules.NetworkModule;
import com.applications.asm.places.di.modules.ServicesModule;

public class PlacesApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                .builder()
                .networkModule(new NetworkModule(getString(R.string.places_service_base_url)))
                .servicesModule(new ServicesModule(this, getString(R.string.places_service_api_key)))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
