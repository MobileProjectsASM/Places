package com.applications.asm.places.application;

import android.app.Application;

import com.applications.asm.data.di.components.DaggerDataComponent;
import com.applications.asm.data.di.components.DataComponent;
import com.applications.asm.places.di.components.ApplicationComponent;
import com.applications.asm.places.di.components.DaggerApplicationComponent;

public class PlacesApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        DataComponent dataComponent = DaggerDataComponent.factory().create(this);
        applicationComponent = DaggerApplicationComponent.builder()
                .dataComponent(dataComponent)
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
