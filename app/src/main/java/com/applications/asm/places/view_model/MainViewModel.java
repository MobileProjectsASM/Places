package com.applications.asm.places.view_model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.Price;
import com.applications.asm.domain.exception.GetPlacesError;
import com.applications.asm.domain.exception.GetPlacesException;
import com.applications.asm.domain.use_cases.GetPlacesUc;
import com.applications.asm.domain.use_cases.LoadLocationUc;
import com.applications.asm.places.R;
import com.applications.asm.places.model.CategoryVM;
import com.applications.asm.places.model.LocationVM;
import com.applications.asm.places.model.PlaceVM;
import com.applications.asm.places.model.PriceVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.SortCriteriaVM;
import com.applications.asm.places.model.mappers.CategoryMapper;
import com.applications.asm.places.model.mappers.LocationMapper;
import com.applications.asm.places.model.mappers.PlaceMapper;
import com.applications.asm.places.model.mappers.PriceMapper;
import com.applications.asm.places.model.mappers.SortCriteriaMapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class MainViewModel extends ViewModel {
    private final LoadLocationUc loadLocationUc;
    private final GetPlacesUc getPlacesUc;
    private final PlaceMapper placeMapper;
    private final LocationMapper locationMapper;
    private final CategoryMapper categoryMapper;
    private final SortCriteriaMapper sortCriteriaMapper;
    private final PriceMapper priceMapper;
    private final CompositeDisposable composite;

    private MutableLiveData<Resource<List<PlaceVM>>> placesLD;
    private MutableLiveData<Resource<LocationVM>> loadLocationLD;

    public MainViewModel(
        LoadLocationUc loadLocationUc,
        GetPlacesUc getPlacesUc,
        PlaceMapper placeMapper,
        LocationMapper locationMapper,
        CategoryMapper categoryMapper,
        SortCriteriaMapper sortCriteriaMapper,
        PriceMapper priceMapper,
        CompositeDisposable composite
    ) {
        this.loadLocationUc = loadLocationUc;
        this.getPlacesUc = getPlacesUc;
        this.placeMapper = placeMapper;
        this.locationMapper = locationMapper;
        this.categoryMapper = categoryMapper;
        this.sortCriteriaMapper = sortCriteriaMapper;
        this.priceMapper = priceMapper;
        this.composite = composite;
    }

    public LiveData<Resource<List<PlaceVM>>> places() {
        return placesLD;
    }

    public LiveData<Resource<LocationVM>> getLoadLocation() { return loadLocationLD; }

    public void getNearPlaces(String placeToFind, Double latitude, Double longitude, Integer radius, List<CategoryVM> categoriesVM, SortCriteriaVM sortCriteriaVM, List<PriceVM> pricesVM, Boolean isOpenNow) {
        Disposable placesDisposable = getPlacesUc
            .execute(GetPlacesUc.Params.forFilterPlaces(placeToFind, latitude, longitude, radius, getCategories(categoriesVM), sortCriteriaMapper.getSortCriteria(sortCriteriaVM), getPrices(pricesVM), isOpenNow, 0))
            .map(places -> {
                List<PlaceVM> placesMV = new ArrayList<>();
                for(Place place: places)
                    placesMV.add(placeMapper.getPlaceVM(place));
                return placesMV;
            })
            .subscribe(placesMV -> placesLD.setValue(Resource.success(placesMV)), error -> {
                Exception exception = (Exception) error;
                int message;
                if(exception instanceof GetPlacesException) {
                    GetPlacesException getPlacesException = (GetPlacesException) exception;
                    GetPlacesError getPlacesError = getPlacesException.getPlaceError();
                    Log.e(getClass().getName(), getPlacesError.getMessage());
                    switch (getPlacesError) {
                        case CONNECTION_WITH_SERVER_ERROR:
                            message = R.string.error_connection_with_server;
                            break;
                        case REQUEST_RESPONSE_ERROR:
                            message = R.string.error_server;
                            break;
                        case NETWORK_ERROR:
                            message = R.string.error_network_connection;
                            break;
                        default: message = R.string.error_unknown;
                    }
                } else {
                    Log.e(getClass().getName(), error.getMessage());
                    message = R.string.error_unknown;
                }
                placesLD.setValue(Resource.error(message));
            });
        composite.add(placesDisposable);
    }

    public void loadLocation() {
        loadLocationLD.setValue(Resource.loading());
        Disposable loadLocationDisposable = loadLocationUc
                .execute(null)
                .map(locationMapper::getLocationVM)
                .subscribe(locationVM -> loadLocationLD.setValue(Resource.success(locationVM)), error -> {
                    Exception exception = (Exception) error;
                    Log.e(getClass().getName(), exception.getMessage());
                    loadLocationLD.setValue(Resource.error(R.string.error_load_location));
                });
        composite.add(loadLocationDisposable);
    }

    public List<Category> getCategories(List<CategoryVM> categoriesVM) {
        List<Category> categories = new ArrayList<>();
        for(CategoryVM categoryVM: categoriesVM)
            categories.add(categoryMapper.getCategory(categoryVM));
        return categories;
    }

    public List<Price> getPrices(List<PriceVM> pricesVM) {
        List<Price> prices = new ArrayList<>();
        for(PriceVM priceVM: pricesVM)
            prices.add(priceMapper.getPrice(priceVM));
        return prices;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        composite.clear();
    }
}
