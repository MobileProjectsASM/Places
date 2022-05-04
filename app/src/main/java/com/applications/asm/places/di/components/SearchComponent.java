package com.applications.asm.places.di.components;

import com.applications.asm.places.di.modules.UseCasesModule;
import com.applications.asm.places.di.scopes.ActivityScope;
import com.applications.asm.places.view.MainActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {UseCasesModule.class})
public interface SearchComponent {

    @Subcomponent.Factory
    interface Factory {
        SearchComponent create();
    }

    void inject(MainActivity mainActivity);
}
