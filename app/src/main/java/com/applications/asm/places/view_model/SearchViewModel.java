package com.applications.asm.places.view_model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.exception.ClientException;
import com.applications.asm.domain.exception.PlacesServiceException;
import com.applications.asm.domain.use_cases.GetSuggestedPlacesUc;
import com.applications.asm.domain.use_cases.LoadLocationUc;
import com.applications.asm.places.R;
import com.applications.asm.places.model.LocationVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.SuggestedPlaceVM;
import com.applications.asm.places.model.mappers.LocationMapper;
import com.applications.asm.places.model.mappers.PlaceMapper;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class SearchViewModel extends ViewModel {
    private final GetSuggestedPlacesUc getSuggestedPlacesUc;
    private final LoadLocationUc loadLocationUc;
    private final PlaceMapper placeMapper;
    private final LocationMapper locationMapper;
    private final CompositeDisposable composite;

    private MutableLiveData<Resource<LocationVM>> savedLocationLD;
    private MutableLiveData<Resource<List<SuggestedPlaceVM>>> suggestedPlacesLD;

    public SearchViewModel(
        GetSuggestedPlacesUc getSuggestedPlacesUc,
        LoadLocationUc loadLocationUc,
        PlaceMapper placeMapper,
        LocationMapper locationMapper,
        CompositeDisposable composite
    ) {
        this.getSuggestedPlacesUc = getSuggestedPlacesUc;
        this.loadLocationUc = loadLocationUc;
        this.placeMapper = placeMapper;
        this.locationMapper = locationMapper;
        this.composite = composite;
    }

    public LiveData<Resource<LocationVM>> getSavedLocation() {
        return savedLocationLD != null ? savedLocationLD : new MutableLiveData<>();
    }

    public LiveData<Resource<List<SuggestedPlaceVM>>> getSuggestedPlaces() {
        return suggestedPlacesLD != null ? suggestedPlacesLD : new MutableLiveData<>();
    }

    public void loadLocation() {
        savedLocationLD.setValue(Resource.loading());
        Disposable loadLocationDisposable = loadLocationUc
                .execute(null)
                .map(locationMapper::getLocationVM)
                .subscribe(locationVM -> savedLocationLD.setValue(Resource.success(locationVM)), error -> {
                    Exception exception = (Exception) error;
                    Log.e(getClass().getName(), exception.getMessage());
                    savedLocationLD.setValue(Resource.error(R.string.error_unknown));
                });
        composite.add(loadLocationDisposable);
    }

    public void getSuggestedPlaces(String place, Double latitude, Double longitude) {
        Disposable getSuggestedPlacesDisposable = getSuggestedPlacesUc
            .execute(GetSuggestedPlacesUc.Params.forSuggestedPlaces(place, latitude, longitude))
            .flattenAsObservable(suggestedPlaces -> suggestedPlaces)
            .map(placeMapper::getSuggestedPlaceVM)
            .toList()
            .subscribe(suggestedPlacesVM -> suggestedPlacesLD.setValue(Resource.success(suggestedPlacesVM)), error -> {
                Exception exception = (Exception) error;
                Log.e(getClass().getName(), exception.getMessage());
                if(exception instanceof ClientException)
                    suggestedPlacesLD.setValue(Resource.error(R.string.error_client_input_data));
                else if(exception instanceof PlacesServiceException)
                    suggestedPlacesLD.setValue(Resource.error(R.string.error_to_get_suggested_places));
                else
                    suggestedPlacesLD.setValue(Resource.error(R.string.error_unknown));
            });
        composite.add(getSuggestedPlacesDisposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        composite.clear();
    }
}
