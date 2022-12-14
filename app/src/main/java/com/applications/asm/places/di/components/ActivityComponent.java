package com.applications.asm.places.di.components;

import com.applications.asm.places.di.modules.MappersModule;
import com.applications.asm.places.di.modules.ViewModelsModule;
import com.applications.asm.places.di.scopes.ActivityScope;
import com.applications.asm.places.view.activities.MainActivity;
import com.applications.asm.places.view.fragments.CoordinatesMapFragment;
import com.applications.asm.places.view.fragments.MapPlaceFragment;
import com.applications.asm.places.view.fragments.MapPlacesFragment;
import com.applications.asm.places.view.fragments.PlaceDetailsFragment;
import com.applications.asm.places.view.fragments.PlacesFragment;
import com.applications.asm.places.view.fragments.ReviewsFragment;
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
    void inject(PlacesFragment placesFragment);
    void inject(MapPlacesFragment mapPlacesFragment);
    void inject(PlaceDetailsFragment placeDetailsFragment);
    void inject(ReviewsFragment reviewsFragment);
    void inject(MapPlaceFragment mapPlaceFragment);
    void inject(CoordinatesMapFragment coordinatesMapFragment);
}
