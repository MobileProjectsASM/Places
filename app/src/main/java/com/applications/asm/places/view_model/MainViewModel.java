package com.applications.asm.places.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.use_cases.GetPlaceDetailsUc;
import com.applications.asm.domain.use_cases.GetPlacesUc;
import com.applications.asm.domain.use_cases.base.DefaultObserver;
import com.applications.asm.places.model.PlaceDetailsM;
import com.applications.asm.places.model.PlaceM;
import com.applications.asm.places.model.mappers.PlaceDetailsMapper;
import com.applications.asm.places.model.mappers.PlaceMapper;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private final GetPlacesUc getPlacesUc;
    private final GetPlaceDetailsUc getPlaceDetailsUc;
    private final PlaceMapper placeMapper;
    private final PlaceDetailsMapper placeDetailsMapper;

    private final MutableLiveData<List<PlaceM>> _placesMV;
    private final MutableLiveData<PlaceDetailsM> _placeDetailsM;

    public MainViewModel(GetPlacesUc getPlacesUc, GetPlaceDetailsUc getPlaceDetailsUc, PlaceMapper placeMapper, PlaceDetailsMapper placeDetailsMapper) {
        this.getPlacesUc = getPlacesUc;
        this.getPlaceDetailsUc = getPlaceDetailsUc;
        this.placeMapper = placeMapper;
        this.placeDetailsMapper = placeDetailsMapper;
        _placesMV = new MutableLiveData<>(new ArrayList<>());
        _placeDetailsM = new MutableLiveData<>(null);
    }

    public void searchNearPlaces(String placeToFind, Double latitude, Double longitude, Integer radius, List<String> categories) {
        getPlacesUc.execute(new GetPlacesObserver(), GetPlacesUc.Params.forFilterPlaces(placeToFind, latitude, longitude, radius, categories));
    }

    public void getPlaceDetail(String placeId) {
        getPlaceDetailsUc.execute(new GetPlaceDetailObserver(), placeId);
    }

    public LiveData<List<PlaceM>> placesMV() {
        return _placesMV;
    }

    public LiveData<PlaceDetailsM> placeDetail() { return _placeDetailsM; }

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

    private class GetPlaceDetailObserver extends DefaultObserver<PlaceDetails> {
        @Override
        public void onNext(PlaceDetails placeDetails) {
            PlaceDetailsM placeDetailsM = placeDetailsMapper.getPlaceDetailsMFromPlaceDetails(placeDetails);
            _placeDetailsM.postValue(placeDetailsM);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }
}
