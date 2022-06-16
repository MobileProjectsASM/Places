package com.applications.asm.places.view_model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.entities.LatitudeState;
import com.applications.asm.domain.entities.LongitudeState;
import com.applications.asm.domain.entities.StatesKey;
import com.applications.asm.domain.use_cases.LoadLocationUc;
import com.applications.asm.domain.use_cases.SaveLocationUc;
import com.applications.asm.domain.use_cases.ValidateFormLocationUc;
import com.applications.asm.places.R;
import com.applications.asm.places.model.LatitudeStateMV;
import com.applications.asm.places.model.LocationVM;
import com.applications.asm.places.model.LongitudeStateMV;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.StateMV;
import com.applications.asm.places.model.StatesKeyMV;
import com.applications.asm.places.model.mappers.LocationMapper;
import com.applications.asm.places.model.mappers.StatesMapper;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class LocationViewModel extends ViewModel {
    private final ValidateFormLocationUc validateFormLocationUc;
    private final SaveLocationUc saveLocationUc;
    private final LoadLocationUc loadLocationUc;
    private final StatesMapper statesMapper;
    private final LocationMapper locationMapper;
    private final CompositeDisposable composite;

    private MutableLiveData<Resource<Map<String, StateMV>>> formSateLD;
    private MutableLiveData<Resource<Boolean>> saveLocationLD;
    private MutableLiveData<Resource<LocationVM>> loadLocationLD;

    public LocationViewModel(
        SaveLocationUc saveLocationUc,
        ValidateFormLocationUc validateFormLocationUc,
        LoadLocationUc loadLocationUc,
        StatesMapper statesMapper,
        LocationMapper locationMapper,
        CompositeDisposable composite
    ) {
        this.saveLocationUc = saveLocationUc;
        this.validateFormLocationUc = validateFormLocationUc;
        this.loadLocationUc = loadLocationUc;
        this.statesMapper = statesMapper;
        this.locationMapper = locationMapper;
        this.composite = composite;
    }

    public LiveData<Resource<Map<String, StateMV>>> getFormState() {
        return formSateLD;
    }

    public LiveData<Resource<Boolean>> getSaveLocation() { return saveLocationLD; }

    public LiveData<Resource<LocationVM>> getLoadLocation() { return loadLocationLD; }

    public void validateFormLocation(String latitude, String longitude) {
        Disposable formLocationDisposable = validateFormLocationUc
            .execute(ValidateFormLocationUc.Params.forFormLocation(latitude, longitude))
            .map(formState -> {
                LatitudeStateMV latitudeStateMV = statesMapper.getLatitudeStateMV((LatitudeState) formState.get(StatesKey.LATITUDE_STATE_KEY));
                LongitudeStateMV longitudeStateMV = statesMapper.getLongitudeStateMV((LongitudeState) formState.get(StatesKey.LONGITUDE_STATE_KEY));
                Map<String, StateMV> formStateMV = new HashMap<>();
                formStateMV.put(StatesKeyMV.LATITUDE_STATE_MV_KEY, latitudeStateMV);
                formStateMV.put(StatesKeyMV.LONGITUDE_STATE_MV_KEY, longitudeStateMV);
                return formStateMV;
            })
            .subscribe(formStateMV -> formSateLD.setValue(Resource.success(formStateMV)), error -> {
                Exception exception = (Exception) error;
                Log.e(getClass().getName(), exception.getMessage());
                formSateLD.setValue(Resource.error(R.string.error_validate_form_location));
            });
        composite.add(formLocationDisposable);
    }

    public void saveLocation(LocationVM locationVM) {
        Double latitude = locationVM.getLatitude();
        Double longitude = locationVM.getLongitude();
        saveLocationLD.setValue(Resource.loading());
        Disposable saveLocationDisposable = saveLocationUc
            .execute(SaveLocationUc.Params.forSaveLocation(latitude, longitude))
            .subscribe(() -> saveLocationLD.setValue(Resource.success(true)), error -> {
                Exception exception = (Exception) error;
                Log.e(getClass().getName(), exception.getMessage());
                saveLocationLD.setValue(Resource.error(R.string.error_save_location));
            });
        composite.add(saveLocationDisposable);
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

    @Override
    protected void onCleared() {
        super.onCleared();
        composite.clear();
    }
}
