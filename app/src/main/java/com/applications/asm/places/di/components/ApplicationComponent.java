package com.applications.asm.places.di.components;

import com.applications.asm.data.di.components.DataComponent;
import com.applications.asm.places.di.modules.SubcomponentsApplicationModule;
import com.applications.asm.places.di.modules.ThreadsModule;
import com.applications.asm.places.di.scopes.ApplicationScope;

import dagger.Component;

@ApplicationScope
@Component(dependencies = DataComponent.class,
    modules = {
        ThreadsModule.class,
        SubcomponentsApplicationModule.class
})
public interface ApplicationComponent {
    ActivityComponent.Factory provideActivityComponentFactory();
}
