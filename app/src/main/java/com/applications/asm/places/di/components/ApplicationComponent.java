package com.applications.asm.places.di.components;

import com.applications.asm.places.di.modules.ServicesModule;
import com.applications.asm.places.di.modules.SubcomponentsApplicationModule;

import dagger.Component;

@Component(modules = {
    ServicesModule.class,
    SubcomponentsApplicationModule.class
})
public interface ApplicationComponent {

}
