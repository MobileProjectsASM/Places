package com.applications.asm.places.view_model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.exception.GetSuggestedPlacesError;
import com.applications.asm.domain.exception.GetSuggestedPlacesException;
import com.applications.asm.domain.use_cases.GetSuggestedPlacesUc;
import com.applications.asm.places.R;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.SuggestedPlaceVM;
import com.applications.asm.places.model.mappers.PlaceMapper;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class SearchViewModel extends ViewModel {
    private final GetSuggestedPlacesUc getSuggestedPlacesUc;
    private final PlaceMapper placeMapper;
    private final CompositeDisposable composite;

    private MutableLiveData<Resource<List<SuggestedPlaceVM>>> suggestedPlacesLD;

    public SearchViewModel(
        GetSuggestedPlacesUc getSuggestedPlacesUc,
        PlaceMapper placeMapper,
        CompositeDisposable composite
    ) {
        this.getSuggestedPlacesUc = getSuggestedPlacesUc;
        this.placeMapper = placeMapper;
        this.composite = composite;
    }

    public LiveData<Resource<List<SuggestedPlaceVM>>> getSuggestedPlaces() {
        return suggestedPlacesLD;
    }

    public void getSuggestedPlaces(String place, Double latitude, Double longitude) {
        Disposable getSuggestedPlacesDisposable = getSuggestedPlacesUc
            .execute(GetSuggestedPlacesUc.Params.forSuggestedPlaces(place, latitude, longitude))
            .flattenAsObservable(suggestedPlaces -> suggestedPlaces)
            .map(placeMapper::getSuggestedPlaceVM)
            .toList()
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
