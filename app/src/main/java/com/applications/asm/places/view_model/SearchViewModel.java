package com.applications.asm.places.view_model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.entities.RadiusState;
import com.applications.asm.domain.entities.StatesKey;
import com.applications.asm.domain.entities.SuggestedPlace;
import com.applications.asm.domain.exception.GetSuggestedPlacesError;
import com.applications.asm.domain.exception.GetSuggestedPlacesException;
import com.applications.asm.domain.use_cases.GetSuggestedPlacesUc;
import com.applications.asm.domain.use_cases.LoadLocationUc;
import com.applications.asm.domain.use_cases.ValidateFormSearchUc;
import com.applications.asm.places.R;
import com.applications.asm.places.model.LocationVM;
import com.applications.asm.places.model.RadiusStateMV;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.StateMV;
import com.applications.asm.places.model.StatesKeyMV;
import com.applications.asm.places.model.SuggestedPlaceVM;
import com.applications.asm.places.model.mappers.LocationMapper;
import com.applications.asm.places.model.mappers.PlaceMapper;
import com.applications.asm.places.model.mappers.StatesMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class SearchViewModel extends ViewModel {
    private final LoadLocationUc loadLocationUc;
    private final ValidateFormSearchUc validateFormSearchUc;
    private final GetSuggestedPlacesUc getSuggestedPlacesUc;
    private final LocationMapper locationMapper;
    private final StatesMapper statesMapper;
    private final PlaceMapper placeMapper;
    private final CompositeDisposable composite;

    private MutableLiveData<Resource<LocationVM>> currentLocationLD;
    private MutableLiveData<Resource<Map<String, StateMV>>> formStateLD;
    private MutableLiveData<Resource<List<SuggestedPlaceVM>>> suggestedPlacesLD;

    public SearchViewModel(
        LoadLocationUc loadLocationUc,
        ValidateFormSearchUc validateFormSearchUc,
        GetSuggestedPlacesUc getSuggestedPlacesUc,
        StatesMapper statesMapper,
        LocationMapper locationMapper,
        PlaceMapper placeMapper,
        CompositeDisposable composite
    ) {
        this.loadLocationUc = loadLocationUc;
        this.validateFormSearchUc = validateFormSearchUc;
        this.getSuggestedPlacesUc = getSuggestedPlacesUc;
        this.locationMapper = locationMapper;
        this.statesMapper = statesMapper;
        this.placeMapper = placeMapper;
        this.composite = composite;
    }

    public LiveData<Resource<LocationVM>> getCurrentLocation() {
        return currentLocationLD;
    }

    public LiveData<Resource<Map<String, StateMV>>> getFormState() {
        return formStateLD;
    }

    public LiveData<Resource<List<SuggestedPlaceVM>>> getSuggestedPlaces() {
        return suggestedPlacesLD;
    }

    public void loadLocation() {
        Disposable loadLocationDisposable = loadLocationUc
            .execute(null)
            .map(locationMapper::getLocationVM)
            .subscribe(locationVM -> currentLocationLD.setValue(Resource.success(locationVM)), error -> {
                Exception exception = (Exception) error;
                Log.e(getClass().getName(), exception.getMessage());
                currentLocationLD.setValue(Resource.error(R.string.error_load_location));
            });
        composite.add(loadLocationDisposable);
    }

    public void validateFormSearch(String radius) {
        Disposable validateFormDisposable = validateFormSearchUc
            .execute(radius)
            .map(formState -> {
                RadiusStateMV radiusStateMV = statesMapper.getRadiusStateMV((RadiusState) formState.get(StatesKey.RADIUS_STATE_KEY));
                Map<String, StateMV> formStateMV = new HashMap<>();
                formStateMV.put(StatesKeyMV.RADIUS_STATE_MV_KEY, radiusStateMV);
                return formStateMV;
            })
            .subscribe(formStateMV -> formStateLD.setValue(Resource.success(formStateMV)), error -> {
                Exception exception = (Exception) error;
                Log.e(getClass().getName(), exception.getMessage());
                formStateLD.setValue(Resource.error(R.string.error_validate_form_search));
            });
        composite.add(validateFormDisposable);
    }

    public void getSuggestedPlaces(String place, Double latitude, Double longitude) {
        Disposable getSuggestedPlacesDisposable = getSuggestedPlacesUc
            .execute(GetSuggestedPlacesUc.Params.forSuggestedPlaces(place, latitude, longitude))
            .map(suggestedPlaces -> {
                List<SuggestedPlaceVM> suggestedPlacesVM = new ArrayList<>();
                for(SuggestedPlace suggestedPlace: suggestedPlaces)
                    suggestedPlacesVM.add(placeMapper.getSuggestedPlaceVM(suggestedPlace));
                return suggestedPlacesVM;
            })
            .subscribe(suggestedPlacesVM -> suggestedPlacesLD.setValue(Resource.success(suggestedPlacesVM)), error -> {
                Exception exception = (Exception) error;
                int message;
                if(exception instanceof GetSuggestedPlacesException) {
                    GetSuggestedPlacesException getSuggestedPlacesException = (GetSuggestedPlacesException) exception;
                    GetSuggestedPlacesError suggestedPlacesError = getSuggestedPlacesException.getError();
                    Log.e(getClass().getName(), suggestedPlacesError.getMessage());
                    switch (suggestedPlacesError) {
                        case LAT_LON_OUT_OF_RANGE:
                            message = R.string.error_location_invalid;
                            break;
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
                    Log.e(getClass().getName(), exception.getMessage());
                    message = R.string.error_unknown;
                }
                suggestedPlacesLD.setValue(Resource.error(message));
            });
        composite.add(getSuggestedPlacesDisposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        composite.clear();
    }
}
