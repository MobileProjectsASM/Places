package com.applications.asm.places.di.components;

import com.applications.asm.places.di.modules.DeserializerModule;
import com.applications.asm.places.di.modules.NetworkModule;
import com.applications.asm.places.di.modules.ServicesModule;
import com.applications.asm.places.di.modules.SubcomponentsApplicationModule;
import com.applications.asm.places.di.modules.ThreadModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    ServicesModule.class,
    NetworkModule.class,
    DeserializerModule.class,
    ThreadModule.class,
    SubcomponentsApplicationModule.class
})
public interface ApplicationComponent {
    MainComponent.Factory mainComponentFactory();
}
