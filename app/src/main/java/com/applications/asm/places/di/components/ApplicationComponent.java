package com.applications.asm.places.di.components;

import com.applications.asm.places.di.modules.DeserializerModule;
import com.applications.asm.places.di.modules.NetworkModule;
import com.applications.asm.places.di.modules.ServicesModule;
import com.applications.asm.places.di.modules.SubcomponentsApplicationModule;

import dagger.Component;

@Component(modules = {
    ServicesModule.class,
    SubcomponentsApplicationModule.class,
    NetworkModule.class,
    DeserializerModule.class
})
public interface ApplicationComponent {

}
