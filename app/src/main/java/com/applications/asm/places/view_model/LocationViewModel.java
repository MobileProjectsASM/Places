package com.applications.asm.places.view_model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.use_cases.SaveLocationUc;
import com.applications.asm.places.R;
import com.applications.asm.places.model.LocationVM;
import com.applications.asm.places.model.Resource;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class LocationViewModel extends ViewModel {
    private final SaveLocationUc saveLocationUc;
    private final CompositeDisposable composite;

    private MutableLiveData<Resource<Boolean>> saveLocationLD;

    public LocationViewModel(
        SaveLocationUc saveLocationUc,
        CompositeDisposable composite
    ) {
        this.saveLocationUc = saveLocationUc;
        this.composite = composite;
    }

    public LiveData<Resource<Boolean>> getSaveLocation() { return saveLocationLD; }

    /*public void validateFormLocation(String latitude, String longitude) {
        Disposable formLocationDisposable = validateFormLocationUc
            .execute(ValidateFormLocationUc.Params.forFormLocation(latitude, longitude))
            .map(formState -> {
                LatitudeStateVM latitudeStateVM = statesMapper.getLatitudeStateMV((LatitudeState) formState.get(StatesKey.LATITUDE_STATE_KEY));
                LongitudeStateVM longitudeStateVM = statesMapper.getLongitudeStateMV((LongitudeState) formState.get(StatesKey.LONGITUDE_STATE_KEY));
                Map<String, StateMV> formStateMV = new HashMap<>();
                formStateMV.put(StatesKeyVM.LATITUDE_STATE_MV_KEY, latitudeStateVM);
                formStateMV.put(StatesKeyVM.LONGITUDE_STATE_MV_KEY, longitudeStateVM);
                return formStateMV;
            })
            .subscribe(formStateMV -> formSateLD.setValue(Resource.success(formStateMV)), error -> {
                Exception exception = (Exception) error;
                Log.e(getClass().getName(), exception.getMessage());
                formSateLD.setValue(Resource.error(R.string.error_validate_form_location));
            });
        composite.add(formLocationDisposable);
    }*/

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

    @Override
    protected void onCleared() {
        super.onCleared();
        composite.clear();
    }
}
