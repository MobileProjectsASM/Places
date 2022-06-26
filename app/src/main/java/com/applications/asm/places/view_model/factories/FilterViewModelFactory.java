package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetPricesUc;
import com.applications.asm.domain.use_cases.GetSortCriteriaUc;
import com.applications.asm.domain.use_cases.LoadLocationUc;
import com.applications.asm.places.model.mappers.LocationMapper;
import com.applications.asm.places.model.mappers.PriceMapper;
import com.applications.asm.places.model.mappers.SortCriteriaMapper;
import com.applications.asm.places.view_model.FilterViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class FilterViewModelFactory implements ViewModelProvider.Factory {
    private final LoadLocationUc loadLocationUc;
    private final GetSortCriteriaUc getSortCriteriaUc;
    private final GetPricesUc getPricesUc;
    private final LocationMapper locationMapper;
    private final SortCriteriaMapper sortCriteriaMapper;
    private final PriceMapper priceMapper;
    private final CompositeDisposable composite;

    public FilterViewModelFactory(
        LoadLocationUc loadLocationUc,
        GetSortCriteriaUc getSortCriteriaUc,
        GetPricesUc getPricesUc,
        LocationMapper locationMapper,
        SortCriteriaMapper sortCriteriaMapper,
        PriceMapper priceMapper,
        CompositeDisposable composite
    ) {
        this.loadLocationUc = loadLocationUc;
        this.getSortCriteriaUc = getSortCriteriaUc;
        this.getPricesUc = getPricesUc;
        this.locationMapper = locationMapper;
        this.sortCriteriaMapper = sortCriteriaMapper;
        this.priceMapper = priceMapper;
        this.composite = composite;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(FilterViewModel.class)) return (T) new FilterViewModel(loadLocationUc, getSortCriteriaUc, getPricesUc,  locationMapper, sortCriteriaMapper, priceMapper, composite);
        throw new IllegalArgumentException("ViewModel class not found");
    }
}
