package com.applications.asm.places.view_model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.use_cases.GetPricesUc;
import com.applications.asm.domain.use_cases.GetSortCriteriaUc;
import com.applications.asm.places.R;
import com.applications.asm.places.model.PriceVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.SortCriteriaVM;
import com.applications.asm.places.model.mappers.PriceMapper;
import com.applications.asm.places.model.mappers.SortCriteriaMapper;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class FilterViewModel extends ViewModel {
    private final GetSortCriteriaUc getSortCriteriaUc;
    private final GetPricesUc getPricesUc;
    private final SortCriteriaMapper sortCriteriaMapper;
    private final PriceMapper priceMapper;
    private final CompositeDisposable composite;

    private MutableLiveData<Resource<List<SortCriteriaVM>>> sortCriteriaListLD;
    private MutableLiveData<Resource<List<PriceVM>>> pricesLD;

    public FilterViewModel(
        GetSortCriteriaUc getSortCriteriaUc,
        GetPricesUc getPricesUc,
        SortCriteriaMapper sortCriteriaMapper,
        PriceMapper priceMapper,
        CompositeDisposable composite
    ) {
        this.getSortCriteriaUc = getSortCriteriaUc;
        this.getPricesUc = getPricesUc;
        this.sortCriteriaMapper = sortCriteriaMapper;
        this.priceMapper = priceMapper;
        this.composite = composite;
    }

    public LiveData<Resource<List<SortCriteriaVM>>> getSortCriteriaLD() {
        return sortCriteriaListLD;
    }

    public LiveData<Resource<List<PriceVM>>> getPricesLD() {
        return pricesLD;
    }

    public void getSortCriteria() {
        Disposable getSortCriteriaListDisposable = getSortCriteriaUc
            .execute(null)
            .map(sortCriteriaMapper::getSortCriteriaVMList)
            .subscribe(sortCriteriaVMList -> sortCriteriaListLD.setValue(Resource.success(sortCriteriaVMList)), error -> {
                Exception exception = (Exception) error;
                Log.e(getClass().getName(), exception.getMessage());
                sortCriteriaListLD.setValue(Resource.error(R.string.error_get_sort_criteria));
            });
        composite.add(getSortCriteriaListDisposable);
    }

    public void getPrices() {
        Disposable getPricesDisposable = getPricesUc
            .execute(null)
            .map(priceMapper::getPricesVM)
            .subscribe(pricesVM -> pricesLD.setValue(Resource.success(pricesVM)), error -> {
                Exception exception = (Exception) error;
                Log.e(getClass().getName(), exception.getMessage());
                pricesLD.setValue(Resource.error(R.string.error_get_prices));
            });
        composite.add(getPricesDisposable);
    }

    /*public void validateFormSearch(String radius) {
        Disposable validateFormDisposable = validateFormSearchUc
                .execute(radius)
                .map(formState -> {
                    RadiusStateMV radiusStateMV = statesMapper.getRadiusStateMV((RadiusState) formState.get(StatesKey.RADIUS_STATE_KEY));
                    Map<String, StateMV> formStateMV = new HashMap<>();
                    formStateMV.put(StatesKeyVM.RADIUS_STATE_MV_KEY, radiusStateMV);
                    return formStateMV;
                })
                .subscribe(formStateMV -> formStateLD.setValue(Resource.success(formStateMV)), error -> {
                    Exception exception = (Exception) error;
                    Log.e(getClass().getName(), exception.getMessage());
                    formStateLD.setValue(Resource.error(R.string.error_validate_form_search));
                });
        composite.add(validateFormDisposable);
    }*/

    @Override
    protected void onCleared() {
        super.onCleared();
        composite.clear();
    }
}
