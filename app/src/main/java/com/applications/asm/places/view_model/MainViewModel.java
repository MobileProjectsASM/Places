package com.applications.asm.places.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.use_cases.GetPlacesUc;
import com.applications.asm.domain.use_cases.base.DefaultObserver;
import com.applications.asm.places.model.PlaceM;
import com.applications.asm.places.model.mappers.PlaceMapper;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private final GetPlacesUc getPlacesUc;
    private final PlaceMapper placeMapper;

    private final MutableLiveData<List<PlaceM>> _placesMV;

    public MainViewModel(GetPlacesUc getPlacesUc, PlaceMapper placeMapper) {
        this.getPlacesUc = getPlacesUc;
        this.placeMapper = placeMapper;
        _placesMV = new MutableLiveData<>(new ArrayList<>());
    }

    public void searchNearPlaces(String placeToFind, Double latitude, Double longitude, Integer radius, List<String> categories) {
        getPlacesUc.execute(new GetPlacesObserver(), GetPlacesUc.Params.forFilterPlaces(placeToFind, latitude, longitude, radius, categories));
    }

    public LiveData<List<PlaceM>> placesMV() {
        return _placesMV;
    }

    private class GetPlacesObserver extends DefaultObserver<List<Place>> {
        @Override
        public void onNext(List<Place> places) {
            List<PlaceM> placesMV = new ArrayList<>();
            for(Place place: places)
                placesMV.add(placeMapper.getPlaceMVFromPlace(place));
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
