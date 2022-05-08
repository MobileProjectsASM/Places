package com.applications.asm.places.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.use_cases.GetPlacesUc;
import com.applications.asm.domain.use_cases.base.DefaultObserver;
import com.applications.asm.places.model.PlaceMV;
import com.applications.asm.places.model.mappers.PlaceMVMapper;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private final GetPlacesUc getPlacesUc;
    private final PlaceMVMapper placeMVMapper;

    private final MutableLiveData<List<PlaceMV>> _placesMV;

    public MainViewModel(GetPlacesUc getPlacesUc, PlaceMVMapper placeMVMapper) {
        this.getPlacesUc = getPlacesUc;
        this.placeMVMapper = placeMVMapper;
        _placesMV = new MutableLiveData<>(new ArrayList<>());
    }

    public void searchNearPlaces(String placeToFind, Double latitude, Double longitude, Integer radius, List<String> categories) {
        getPlacesUc.execute(new GetPlacesObserver(), GetPlacesUc.Params.forFilterPlaces(placeToFind, latitude, longitude, radius, categories));
    }

    public LiveData<List<PlaceMV>> placesMV() {
        return _placesMV;
    }

    private class GetPlacesObserver extends DefaultObserver<List<Place>> {
        @Override
        public void onNext(List<Place> places) {
            List<PlaceMV> placesMV = new ArrayList<>();
            for(Place place: places)
                placesMV.add(placeMVMapper.getPlaceMVFromPlace(place));
            _placesMV.postValue(placesMV);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }
}
