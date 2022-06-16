package com.applications.asm.places.di.components;

import com.applications.asm.places.di.modules.UseCasesModule;
import com.applications.asm.places.di.modules.ViewModelsModule;
import com.applications.asm.places.di.scopes.ActivityScope;
import com.applications.asm.places.view.fragments.LocationFragment;
import com.applications.asm.places.view.fragments.PlacesFragment;
import com.applications.asm.places.view.fragments.SearchFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {
    UseCasesModule.class,
    ViewModelsModule.class
})
public interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        MainComponent create();
    }

    void inject(LocationFragment locationFragment);
    void inject(SearchFragment searchFragment);
    void inject(PlacesFragment placesFragment);
}
