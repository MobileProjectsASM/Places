package com.applications.asm.places.application;

import android.app.Application;

import com.applications.asm.places.R;
import com.applications.asm.places.di.components.ApplicationComponent;
import com.applications.asm.places.di.components.DaggerApplicationComponent;
import com.applications.asm.places.di.modules.UtilsModule;

public class PlacesApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                .builder()
                .utilsModule(new UtilsModule(this, "app_preferences", getString(R.string.places_service_api_key), getString(R.string.places_service_base_url)))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
