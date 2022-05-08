package com.applications.asm.places.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.entities.SuggestedPlace;
import com.applications.asm.domain.use_cases.GetSuggestedPlacesUc;
import com.applications.asm.domain.use_cases.base.DefaultObserver;
import com.applications.asm.places.model.mappers.PlaceNameMapper;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends ViewModel {
    private final GetSuggestedPlacesUc getSuggestedPlacesUc;
    private final PlaceNameMapper placeNameMapper;

    private final MutableLiveData<List<String>> _placesSuggested;
    private Double latitude;
    private Double longitude;

    public SearchViewModel(GetSuggestedPlacesUc getSuggestedPlacesUc, PlaceNameMapper placeNameMapper) {
        this.getSuggestedPlacesUc = getSuggestedPlacesUc;
        this.placeNameMapper = placeNameMapper;
        _placesSuggested = new MutableLiveData<>(new ArrayList<>());
    }

    public void getSuggestedPlaces(String word) {
        getSuggestedPlacesUc.execute(new GetSuggestedPlacesObserver(), GetSuggestedPlacesUc.Params.forSuggestedPlaces(word, longitude, latitude));
    }

    private class GetSuggestedPlacesObserver extends DefaultObserver<List<SuggestedPlace>> {
        @Override
        public void onNext(List<SuggestedPlace> suggestedPlaces) {
            List<String> placesName = new ArrayList<>();
            for(SuggestedPlace suggestedPlace: suggestedPlaces)
                placesName.add(placeNameMapper.getPlaceNameFromSuggestedPlace(suggestedPlace));
            _placesSuggested.postValue(placesName);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

    public LiveData<List<String>> suggestedPlaces() {
        return _placesSuggested;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
