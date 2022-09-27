package com.applications.asm.places.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.use_cases.SaveCoordinatesUc;
import com.applications.asm.places.model.CoordinatesVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.mappers.CoordinatesMapper;
import com.applications.asm.places.view_model.exception.ErrorUtils;

import io.reactivex.rxjava3.core.Flowable;

public class CoordinatesViewModel extends ViewModel {
    private final SaveCoordinatesUc saveCoordinatesUc;
    private final CoordinatesMapper coordinatesMapper;

    public CoordinatesViewModel(SaveCoordinatesUc saveCoordinatesUc, CoordinatesMapper coordinatesMapper) {
        this.saveCoordinatesUc = saveCoordinatesUc;
        this.coordinatesMapper = coordinatesMapper;
    }

    public LiveData<Resource<Boolean>> saveCoordinates(CoordinatesVM coordinatesVM) {
        MediatorLiveData<Resource<Boolean>> liveDataSaveCoordinates = new MediatorLiveData<>();
        liveDataSaveCoordinates.setValue(Resource.loading());
        LiveData<Resource<Boolean>> sourceSaveCoordinates = LiveDataReactiveStreams.fromPublisher(saveCoordinatesUc.execute(coordinatesMapper.getCoordinates(coordinatesVM))
                .toSingle(() -> Resource.success(true))
                .onErrorReturn(throwable -> ErrorUtils.resolveError(throwable, AdvancedSearchViewModel.class))
                .toFlowable());
        liveDataSaveCoordinates.addSource(sourceSaveCoordinates, resource -> {
            liveDataSaveCoordinates.setValue(resource);
            liveDataSaveCoordinates.removeSource(sourceSaveCoordinates);
        });
        return liveDataSaveCoordinates;
    }
}
