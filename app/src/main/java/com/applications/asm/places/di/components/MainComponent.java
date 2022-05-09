package com.applications.asm.places.di.components;

import com.applications.asm.places.di.modules.UseCasesModule;
import com.applications.asm.places.di.modules.ViewModelsModule;
import com.applications.asm.places.di.scopes.ActivityScope;
import com.applications.asm.places.model.mappers.PlaceDetailsMapper;
import com.applications.asm.places.view.activities.MainActivity;
import com.applications.asm.places.view.fragments.ListFragment;
import com.applications.asm.places.view.fragments.PlaceDetailsFragment;
import com.applications.asm.places.view.fragments.PlacesMapFragment;
import com.applications.asm.places.view.fragments.ReviewsFragment;
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

    void inject(SearchFragment searchFragment);
    void inject(ListFragment listFragment);
    void inject(PlaceDetailsFragment placeDetailsFragment);
    void inject(PlacesMapFragment placesMapFragment);
    void inject(ReviewsFragment reviewsFragment);
}
