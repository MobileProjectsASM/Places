package com.applications.asm.places.view_model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.exception.GetPlacesError;
import com.applications.asm.domain.exception.GetPlacesException;
import com.applications.asm.domain.use_cases.GetPlacesUc;
import com.applications.asm.places.R;
import com.applications.asm.places.model.PlaceVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.mappers.PlaceMapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class MainViewModel extends ViewModel {
    private final GetPlacesUc getPlacesUc;
    private final PlaceMapper placeMapper;
    private final CompositeDisposable composite;

    private MutableLiveData<Resource<List<PlaceVM>>> placesLD;

    public MainViewModel(
        GetPlacesUc getPlacesUc,
        PlaceMapper placeMapper,
        CompositeDisposable composite
    ) {
        this.getPlacesUc = getPlacesUc;
        this.placeMapper = placeMapper;
        this.composite = composite;
    }

    public LiveData<Resource<List<PlaceVM>>> places() {
        return placesLD;
    }

    public void getNearPlaces(String placeToFind, Double latitude, Double longitude, Integer radius, List<String> categories) {
        Disposable placesDisposable = getPlacesUc
            .execute(GetPlacesUc.Params.forFilterPlaces(placeToFind, latitude, longitude, radius, categories, 0))
            .map(places -> {
                List<PlaceVM> placesMV = new ArrayList<>();
                for(Place place: places)
                    placesMV.add(placeMapper.getPlaceMV(place));
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
}
