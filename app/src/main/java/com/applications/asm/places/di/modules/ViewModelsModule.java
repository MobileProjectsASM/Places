package com.applications.asm.places.di.modules;

import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetCategoriesUc;
import com.applications.asm.domain.use_cases.GetPlacesUc;
import com.applications.asm.domain.use_cases.GetPricesUc;
import com.applications.asm.domain.use_cases.GetSortCriteriaUc;
import com.applications.asm.domain.use_cases.GetSuggestedPlacesUc;
import com.applications.asm.domain.use_cases.LoadLocationUc;
import com.applications.asm.domain.use_cases.SaveLocationUc;
import com.applications.asm.places.di.scopes.ActivityScope;
import com.applications.asm.places.model.mappers.CategoryMapper;
import com.applications.asm.places.model.mappers.CategoryMapperImpl;
import com.applications.asm.places.model.mappers.LocationMapper;
import com.applications.asm.places.model.mappers.LocationMapperImpl;
import com.applications.asm.places.model.mappers.PlaceMapper;
import com.applications.asm.places.model.mappers.PlaceMapperImpl;
import com.applications.asm.places.model.mappers.PriceMapper;
import com.applications.asm.places.model.mappers.PriceMapperImpl;
import com.applications.asm.places.model.mappers.SortCriteriaMapper;
import com.applications.asm.places.model.mappers.SortCriteriaMapperImpl;
import com.applications.asm.places.view_model.factories.CategoryViewModelFactory;
import com.applications.asm.places.view_model.factories.FilterViewModelFactory;
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
        CompositeDisposable composite
    ) {
        return new LocationViewModelFactory(
            saveLocationUc,
            composite
        );
    }

    @ActivityScope
    @Named("search_view_model")
    @Provides
    ViewModelProvider.Factory provideSearchViewModelFactory(
        GetSuggestedPlacesUc getSuggestedPlacesUc,
        PlaceMapper placeMapper,
        CompositeDisposable composite
    ) {
        return new SearchViewModelFactory(
            getSuggestedPlacesUc,
            placeMapper,
            composite
        );
    }

    @ActivityScope
    @Named("filter_view_model")
    @Provides
    ViewModelProvider.Factory provideFilterViewModelFactory(
        GetSortCriteriaUc getSortCriteriaUc,
        GetPricesUc getPricesUc,
        SortCriteriaMapper sortCriteriaMapper,
        PriceMapper priceMapper,
        CompositeDisposable composite
    ) {
        return new FilterViewModelFactory(
            getSortCriteriaUc,
            getPricesUc,
            sortCriteriaMapper,
            priceMapper,
            composite
        );
    }

    @ActivityScope
    @Named("category_view_model")
    @Provides
    ViewModelProvider.Factory provideCategoryViewModel(
        GetCategoriesUc getCategoriesUc,
        CategoryMapper categoryMapper,
        CompositeDisposable composite
    ) {
        return new CategoryViewModelFactory(
            getCategoriesUc,
            categoryMapper,
            composite
        );
    }

    @ActivityScope
    @Named("main_view_model")
    @Provides
    ViewModelProvider.Factory provideMainViewModelFactory(
        LoadLocationUc loadLocationUc,
        GetPlacesUc getPlacesUc,
        PlaceMapper placeMapper,
        LocationMapper locationMapper,
        CategoryMapper categoryMapper,
        SortCriteriaMapper sortCriteriaMapper,
        PriceMapper priceMapper,
        CompositeDisposable composite
    ) {
        return new MainViewModelFactory(
            loadLocationUc,
            getPlacesUc,
            placeMapper,
            locationMapper,
            categoryMapper,
            sortCriteriaMapper,
            priceMapper,
            composite
        );
    }

    //Mappers
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
    CategoryMapper provideCategoryMapper() {
        return new CategoryMapperImpl();
    }

    @ActivityScope
    @Provides
    SortCriteriaMapper sortCriteriaMapper() {
        return new SortCriteriaMapperImpl();
    }

    @ActivityScope
    @Provides
    PriceMapper providePriceMapper() {
        return new PriceMapperImpl();
    }

    //Utils
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

}
