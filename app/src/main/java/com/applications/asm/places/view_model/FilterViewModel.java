package com.applications.asm.places.view_model;

import android.util.ArrayMap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.use_cases.GetPricesUc;
import com.applications.asm.domain.use_cases.GetSortCriteriaUc;
import com.applications.asm.domain.use_cases.LoadLocationUc;
import com.applications.asm.places.R;
import com.applications.asm.places.model.LocationVM;
import com.applications.asm.places.model.PriceVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.SortCriteriaVM;
import com.applications.asm.places.model.mappers.LocationMapper;
import com.applications.asm.places.model.mappers.PriceMapper;
import com.applications.asm.places.model.mappers.SortCriteriaMapper;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class FilterViewModel extends ViewModel {
    private static final String CURRENT_LOCATION = "current_location";
    private static final String SORT_CRITERIA_LIST = "sort_criteria_list";
    private static final String PRICES_LIST = "prices_list";
    private final LoadLocationUc loadLocationUc;
    private final GetSortCriteriaUc getSortCriteriaUc;
    private final GetPricesUc getPricesUc;
    private final LocationMapper locationMapper;
    private final SortCriteriaMapper sortCriteriaMapper;
    private final PriceMapper priceMapper;
    private final CompositeDisposable composite;

    private MutableLiveData<Resource<Map<String, Object>>> dataLD;

    public FilterViewModel(
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
        this.sortCriteriaMapper = sortCriteriaMapper;
        this.priceMapper = priceMapper;
        this.locationMapper = locationMapper;
        this.composite = composite;
    }

    public LiveData<Resource<Map<String, Object>>> getData() {
        return dataLD != null ? dataLD : new MutableLiveData<>();
    }

    public void loadData() {
        dataLD.setValue(Resource.loading());
        Single<LocationVM> locationSingle = loadLocationUc.execute(null).map(locationMapper::getLocationVM);
        Single<List<SortCriteriaVM>> sortCriteriaListSingle = getSortCriteriaUc.execute(null).map(sortCriteriaMapper::getSortCriteriaVMList);
        Single<List<PriceVM>> pricesListSingle = getPricesUc.execute(null).map(priceMapper::getPricesVM);
        Disposable loadDataDisposable = Single.zip(locationSingle, sortCriteriaListSingle, pricesListSingle, ((locationVM, sortCriteriaVMList, priceVMList) -> {
            Map<String, Object> data = new ArrayMap<>();
            data.put(CURRENT_LOCATION, locationVM);
            data.put(SORT_CRITERIA_LIST, sortCriteriaVMList);
            data.put(PRICES_LIST, priceVMList);
            return data;
        }))
        .subscribe(data -> dataLD.setValue(Resource.success(data)), error -> {
            Exception exception = (Exception) error;
            Log.e(getClass().getName(), exception.getMessage());
            dataLD.setValue(Resource.error(R.string.error_unknown));
        });
        composite.add(loadDataDisposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        composite.clear();
    }
}
