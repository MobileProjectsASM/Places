package com.applications.asm.places.view_model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.exception.ClientException;
import com.applications.asm.domain.exception.DeviceException;
import com.applications.asm.domain.use_cases.GetCurrentLocationUc;
import com.applications.asm.domain.use_cases.LoadLocationUc;
import com.applications.asm.domain.use_cases.SaveLocationUc;
import com.applications.asm.places.R;
import com.applications.asm.places.model.LocationVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.mappers.LocationMapper;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class LocationViewModel extends ViewModel {
    private final LoadLocationUc loadLocationUc;
    private final SaveLocationUc saveLocationUc;
    private final GetCurrentLocationUc getCurrentLocationUc;
    private final LocationMapper locationMapper;
    private final CompositeDisposable composite;

    private MutableLiveData<Resource<Boolean>> saveLocationLD;
    private MutableLiveData<Resource<LocationVM>> savedLocationLD;
    private MutableLiveData<Resource<LocationVM>> currentLocationLD;

    public LocationViewModel(
        LoadLocationUc loadLocationUc,
        SaveLocationUc saveLocationUc,
        GetCurrentLocationUc getCurrentLocationUc,
        LocationMapper locationMapper,
        CompositeDisposable composite
    ) {
        this.loadLocationUc = loadLocationUc;
        this.saveLocationUc = saveLocationUc;
        this.getCurrentLocationUc = getCurrentLocationUc;
        this.locationMapper = locationMapper;
        this.composite = composite;
    }

    public LiveData<Resource<Boolean>> getSaveLocation() {
        return saveLocationLD != null ? saveLocationLD : new MutableLiveData<>();
    }

    public LiveData<Resource<LocationVM>> getLoadLocation() {
        return savedLocationLD != null ? savedLocationLD : new MutableLiveData<>();
    }

    public LiveData<Resource<LocationVM>> getCurrentLocation() {
        return currentLocationLD != null ? currentLocationLD : new MutableLiveData<>();
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
                if(exception instanceof ClientException)
                    savedLocationLD.setValue(Resource.error(R.string.error_client_input_data));
                else
                    savedLocationLD.setValue(Resource.error(R.string.error_unknown));
            });
        composite.add(saveLocationDisposable);
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

    public void getMyLocation() {
        currentLocationLD.setValue(Resource.loading());
        Disposable currentLocationDisposable = getCurrentLocationUc
            .execute(null)
            .map(locationMapper::getLocationVM)
            .subscribe(locationVM -> currentLocationLD.setValue(Resource.success(locationVM)), error -> {
                Exception exception = (Exception) error;
                Log.e(getClass().getName(), exception.getMessage());
                if(exception instanceof DeviceException)
                    currentLocationLD.setValue(Resource.error(R.string.error_location_is_disabled));
                else
                    currentLocationLD.setValue(Resource.error(R.string.error_unknown));
            });
        composite.add(currentLocationDisposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        composite.clear();
    }
}
