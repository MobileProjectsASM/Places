package com.applications.asm.places.di.components;

import com.applications.asm.places.di.modules.MappersModule;
import com.applications.asm.places.di.modules.ViewModelsModule;
import com.applications.asm.places.di.scopes.ActivityScope;
import com.applications.asm.places.view.activities.MainActivity;
import com.applications.asm.places.view.fragments.SearchCategoriesFragment;
import com.applications.asm.places.view.fragments.CoordinatesFragment;
import com.applications.asm.places.view.fragments.AdvancedSearchFragment;
import com.applications.asm.places.view.fragments.SearchPlacesFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {
    MappersModule.class,
    ViewModelsModule.class
})
public interface ActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        ActivityComponent create();
    }

    void inject(MainActivity mainActivity);
    void inject(CoordinatesFragment coordinatesFragment);
    void inject(SearchPlacesFragment searchPlacesFragment);
    void inject(AdvancedSearchFragment advancedSearchFragment);
    void inject(SearchCategoriesFragment searchCategoriesFragment);
}
