package com.applications.asm.places.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.use_cases.GetPlaceDetailsUc;
import com.applications.asm.places.model.PlaceDetailsVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.mappers.PlaceMapper;
import com.applications.asm.places.view_model.exception.ErrorUtils;

public class PlaceDetailsViewModel extends ViewModel {
    private final GetPlaceDetailsUc getPlaceDetailsUc;
    private final PlaceMapper placeMapper;

    public PlaceDetailsViewModel(GetPlaceDetailsUc getPlaceDetailsUc, PlaceMapper placeMapper) {
        this.getPlaceDetailsUc = getPlaceDetailsUc;
        this.placeMapper = placeMapper;
    }

    public LiveData<Resource<PlaceDetailsVM>> loadPlaceDetailsVM(String placeId) {
        MediatorLiveData<Resource<PlaceDetailsVM>> liveDataPlaceDetails = new MediatorLiveData<>();
        liveDataPlaceDetails.setValue(Resource.loading());
        LiveData<Resource<PlaceDetailsVM>> sourcePlaceDetails = LiveDataReactiveStreams.fromPublisher(getPlaceDetailsUc.execute(placeId)
                .map(response -> {
                    Resource<PlaceDetailsVM> resource;
                    if(response.getError() == null || response.getError().isEmpty()) resource = Resource.success(placeMapper.getPlaceDetailsVM(response.getData()));
                    else resource = Resource.warning(response.getError());
                    return resource;
                })
                .onErrorReturn(throwable -> ErrorUtils.resolveError(throwable, PlaceDetailsViewModel.class))
                .toFlowable());
        liveDataPlaceDetails.addSource(sourcePlaceDetails, resource -> {
            liveDataPlaceDetails.setValue(resource);
            liveDataPlaceDetails.removeSource(sourcePlaceDetails);
        });
        return liveDataPlaceDetails;
    }
}
