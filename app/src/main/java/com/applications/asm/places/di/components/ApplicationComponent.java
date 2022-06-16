package com.applications.asm.places.di.components;

import com.applications.asm.places.di.modules.DataSourcesModule;
import com.applications.asm.places.di.modules.ServicesModule;
import com.applications.asm.places.di.modules.SubcomponentsApplicationModule;
import com.applications.asm.places.di.modules.ThreadModule;
import com.applications.asm.places.di.modules.UtilsModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    ServicesModule.class,
    DataSourcesModule.class,
    UtilsModule.class,
    ThreadModule.class,
    SubcomponentsApplicationModule.class
})
public interface ApplicationComponent {
    MainComponent.Factory mainComponentFactory();
}
