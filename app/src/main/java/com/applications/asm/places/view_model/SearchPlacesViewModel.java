package com.applications.asm.places.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.use_cases.GetSuggestedPlacesUc;
import com.applications.asm.places.model.CoordinatesVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.SuggestedPlaceVM;
import com.applications.asm.places.model.mappers.CoordinatesMapper;
import com.applications.asm.places.model.mappers.PlaceMapper;
import com.applications.asm.places.view_model.exception.ErrorUtils;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class SearchPlacesViewModel extends ViewModel {
    private final GetSuggestedPlacesUc getSuggestedPlacesUc;
    private final PlaceMapper placeMapper;
    private final CoordinatesMapper coordinatesMapper;

    public SearchPlacesViewModel(GetSuggestedPlacesUc getSuggestedPlacesUc, PlaceMapper placeMapper, CoordinatesMapper coordinatesMapper) {
        this.getSuggestedPlacesUc = getSuggestedPlacesUc;
        this.placeMapper = placeMapper;
        this.coordinatesMapper = coordinatesMapper;
    }

    public LiveData<Resource<List<SuggestedPlaceVM>>> getSuggestedPlaces(String place, CoordinatesVM coordinatesVM) {
        MediatorLiveData<Resource<List<SuggestedPlaceVM>>> liveDataSuggestedPlaces = new MediatorLiveData<>();
        liveDataSuggestedPlaces.setValue(Resource.loading());
        LiveData<Resource<List<SuggestedPlaceVM>>> sourceSuggestedPlaces = LiveDataReactiveStreams.fromPublisher(getSuggestedPlacesUc.execute(GetSuggestedPlacesUc.Params.forSuggestedPlaces(place, coordinatesMapper.getCoordinates(coordinatesVM)))
                .flatMap(listResponse -> {
                    Single<Resource<List<SuggestedPlaceVM>>> suggestedPlacesResource;
                    if(listResponse.getError() == null || listResponse.getError().isEmpty()) {
                        suggestedPlacesResource = Observable.fromIterable(listResponse.getData())
                                .map(placeMapper::getSuggestedPlaceVM)
                                .toList()
                                .map(Resource::success);
                    } else {
                        String error = listResponse.getError();
                        suggestedPlacesResource = Single.just(error != null ? error : "").map(Resource::warning);
                    }
                    return suggestedPlacesResource;
                })
                .onErrorReturn(throwable -> ErrorUtils.resolveError(throwable, AdvancedSearchViewModel.class))
                .toFlowable());
        liveDataSuggestedPlaces.addSource(sourceSuggestedPlaces, resource -> {
            liveDataSuggestedPlaces.setValue(resource);
            liveDataSuggestedPlaces.removeSource(sourceSuggestedPlaces);
        });
        return liveDataSuggestedPlaces;
    }
}
