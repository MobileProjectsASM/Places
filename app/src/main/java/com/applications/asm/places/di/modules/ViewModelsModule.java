package com.applications.asm.places.di.modules;

import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetCategoriesUc;
import com.applications.asm.domain.use_cases.GetPlacesUc;
import com.applications.asm.domain.use_cases.GetSuggestedPlacesUc;
import com.applications.asm.domain.use_cases.LoadLocationUc;
import com.applications.asm.domain.use_cases.SaveLocationUc;
import com.applications.asm.domain.use_cases.ValidateFormLocationUc;
import com.applications.asm.domain.use_cases.ValidateFormSearchUc;
import com.applications.asm.places.di.scopes.ActivityScope;
import com.applications.asm.places.model.mappers.CategoryMapper;
import com.applications.asm.places.model.mappers.CategoryMapperImpl;
import com.applications.asm.places.model.mappers.LocationMapper;
import com.applications.asm.places.model.mappers.LocationMapperImpl;
import com.applications.asm.places.model.mappers.PlaceMapper;
import com.applications.asm.places.model.mappers.PlaceMapperImpl;
import com.applications.asm.places.model.mappers.StatesMapper;
import com.applications.asm.places.model.mappers.StatesMapperImpl;
import com.applications.asm.places.view_model.factories.LocationViewModelFactory;
import com.applications.asm.places.view_model.factories.MainViewModelFactory;
import com.applications.asm.places.view_model.factories.SearchViewModelFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@Module
public class ViewModelsModule {

    @ActivityScope
    @Named("location_view_model")
    @Provides
    ViewModelProvider.Factory provideLocationViewModelFactory(
        SaveLocationUc saveLocationUc,
        ValidateFormLocationUc validateFormLocationUc,
        LoadLocationUc loadLocationUc,
        StatesMapper statesMapper,
        LocationMapper locationMapper,
        CompositeDisposable composite
    ) {
        return new LocationViewModelFactory(
            saveLocationUc,
            validateFormLocationUc,
            loadLocationUc,
            statesMapper,
            locationMapper,
            composite
        );
    }

    @ActivityScope
    @Named("search_view_model")
    @Provides
    ViewModelProvider.Factory provideSearchViewModelFactory(
        LoadLocationUc loadLocationUc,
        ValidateFormSearchUc validateFormSearchUc,
        GetSuggestedPlacesUc getSuggestedPlacesUc,
        GetCategoriesUc getCategoriesUc,
        StatesMapper statesMapper,
        LocationMapper locationMapper,
        PlaceMapper placeMapper,
        CategoryMapper categoryMapper,
        CompositeDisposable composite
    ) {
        return new SearchViewModelFactory(
            loadLocationUc,
            validateFormSearchUc,
            getSuggestedPlacesUc,
            getCategoriesUc,
            statesMapper,
            locationMapper,
            placeMapper,
            categoryMapper,
            composite
        );
    }

    @ActivityScope
    @Named("main_view_model")
    @Provides
    ViewModelProvider.Factory provideMainViewModelFactory(
        GetPlacesUc getPlacesUc,
        PlaceMapper placeMapper,
        CompositeDisposable composite
    ) {
        return new MainViewModelFactory(
            getPlacesUc,
            placeMapper,
            composite
        );
    }

    //Mappers
    @ActivityScope
    @Provides
    StatesMapper providerStatesMapper() {
        return new StatesMapperImpl();
    }

    @ActivityScope
    @Provides
    LocationMapper provideLocationMapper() {
        return new LocationMapperImpl();
    }

    @ActivityScope
    @Provides
    PlaceMapper providePlaceMapper() {
        return new PlaceMapperImpl();
    }

    @ActivityScope
    @Provides
    CategoryMapper categoryMapper() {
        return new CategoryMapperImpl();
    }

    //Utils
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

}
