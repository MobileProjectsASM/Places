package com.applications.asm.places.di.modules;

import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.places.di.scopes.ActivityScope;
import com.applications.asm.places.view_model.factories.AdvancedSearchVMFactory;
import com.applications.asm.places.view_model.factories.CoordinatesVMFactory;
import com.applications.asm.places.view_model.factories.MainVMFactory;
import com.applications.asm.places.view_model.factories.SearchCategoriesVMFactory;
import com.applications.asm.places.view_model.factories.SearchPlacesVMFactory;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelsModule {

    @ActivityScope
    @Named("coordinatesVMFactory")
    @Binds
    abstract ViewModelProvider.Factory provideCoordinatesVMFactory(CoordinatesVMFactory coordinatesVMFactory);

    @ActivityScope
    @Named("searchPlacesVMFactory")
    @Binds
    abstract ViewModelProvider.Factory provideSearchPlacesVMFactory(SearchPlacesVMFactory searchPlacesVMFactory);

    @ActivityScope
    @Named("advancedSearchVMFactory")
    @Binds
    abstract ViewModelProvider.Factory provideAdvancedSearchVMFactory(AdvancedSearchVMFactory advancedSearchVMFactory);

    @ActivityScope
    @Named("searchCategoriesVMFactory")
    @Binds
    abstract ViewModelProvider.Factory provideSearchCategoriesVMFactory(SearchCategoriesVMFactory searchCategoriesVMFactory);

    @ActivityScope
    @Named("mainVMFactory")
    @Binds
    abstract ViewModelProvider.Factory provideMainVMFactory(MainVMFactory mainVMFactory);
}
