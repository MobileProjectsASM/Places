package com.applications.asm.places.di.modules;

import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetPlacesUc;
import com.applications.asm.domain.use_cases.GetSuggestedPlacesUc;
import com.applications.asm.places.di.scopes.ActivityScope;
import com.applications.asm.places.model.mappers.PlaceMapper;
import com.applications.asm.places.model.mappers.PlaceMapperImpl;
import com.applications.asm.places.model.mappers.PlaceNameMapper;
import com.applications.asm.places.model.mappers.PlaceNameMapperImpl;
import com.applications.asm.places.view_model.factories.MainViewModelFactory;
import com.applications.asm.places.view_model.factories.SearchViewModelFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelsModule {

    @ActivityScope
    @Named("search_view_model")
    @Provides
    ViewModelProvider.Factory provideSearchViewModel(GetSuggestedPlacesUc getSuggestedPlacesUc, PlaceNameMapper placeNameMapper) {
        return new SearchViewModelFactory(getSuggestedPlacesUc, placeNameMapper);
    }

    @Provides
    PlaceNameMapper providePlaceNameMapper() {
        return new PlaceNameMapperImpl();
    }

    @ActivityScope
    @Named("main_view_model")
    @Provides
    ViewModelProvider.Factory provideMainViewModel(GetPlacesUc getPlacesUc, PlaceMapper placeMapper) {
        return new MainViewModelFactory(getPlacesUc, placeMapper);
    }

    @Provides
    PlaceMapper providePlaceMVMapper() {
        return new PlaceMapperImpl();
    }
}
