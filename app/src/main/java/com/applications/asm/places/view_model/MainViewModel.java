package com.applications.asm.places.view_model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.exception.UseCaseException;
import com.applications.asm.domain.exception.UseCaseExceptionCodes;
import com.applications.asm.domain.use_cases.GetCoordinatesUc;
import com.applications.asm.places.model.CategoryVM;
import com.applications.asm.places.model.CoordinatesVM;
import com.applications.asm.places.model.CriterionVM;
import com.applications.asm.places.model.ParametersAdvancedSearch;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.mappers.CoordinatesMapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class MainViewModel extends ViewModel {
    public static final double INVALID_LATITUDE = 91;
    public static final double INVALID_LONGITUDE = 181;

    private final GetCoordinatesUc getCoordinatesUc;
    private final CoordinatesMapper coordinatesMapper;

    private MutableLiveData<CoordinatesVM> workCoordinates;
    private MutableLiveData<List<CategoryVM>> categoriesSelectedVM;
    private MutableLiveData<ParametersAdvancedSearch> parametersAdvancedSearchVM;

    public MainViewModel(GetCoordinatesUc getCoordinatesUc, CoordinatesMapper coordinatesMapper) {
        this.getCoordinatesUc = getCoordinatesUc;
        this.coordinatesMapper = coordinatesMapper;
    }

    public LiveData<Resource<CoordinatesVM>> loadCoordinates(CoordinatesVM.StateVM stateVM) {
        MediatorLiveData<Resource<CoordinatesVM>> liveDataCoordinates = new MediatorLiveData<>();
        liveDataCoordinates.setValue(Resource.loading());
        LiveData<Resource<CoordinatesVM>> sourceCoordinates = LiveDataReactiveStreams.fromPublisher(getCoordinatesUc.execute(coordinatesMapper.getState(stateVM))
                .map(response -> {
                    Resource<CoordinatesVM> resource;
                    if(response.getError() == null || response.getError().isEmpty())
                        resource = Resource.success(coordinatesMapper.getCoordinatesVM(response.getData()));
                    else
                        resource = Resource.warning(response.getError());
                    return resource;
                })
                .onErrorReturn(throwable -> {
                    Resource<CoordinatesVM> resource;
                    Exception exception = (Exception) throwable;
                    if(exception instanceof UseCaseException) {
                        UseCaseException useCaseException = (UseCaseException) exception;
                        if(!useCaseException.getCode().equals(UseCaseExceptionCodes.NO_DATA_IN_MEMORY)) {
                            Log.e(getClass().getName(), useCaseException.getMessage());
                            resource = Resource.error(useCaseException.getMessage());
                        } else resource = Resource.success(new CoordinatesVM(INVALID_LATITUDE, INVALID_LONGITUDE));
                    } else {
                        Log.e(getClass().getName(), exception.getMessage());
                        resource = Resource.error(exception.getMessage());
                    }
                    return resource;
                })
                .toFlowable());
        liveDataCoordinates.addSource(sourceCoordinates, resource ->  {
            liveDataCoordinates.setValue(resource);
            liveDataCoordinates.removeSource(sourceCoordinates);
        });
        return liveDataCoordinates;
    }

    public MutableLiveData<CoordinatesVM> getWorkCoordinates() {
        if(workCoordinates == null) workCoordinates = new MutableLiveData<>();
        return workCoordinates;
    }

    public MutableLiveData<List<CategoryVM>> getCategoriesSelected() {
        if(categoriesSelectedVM == null) categoriesSelectedVM = new MutableLiveData<>();
        return categoriesSelectedVM;
    }

    public MutableLiveData<ParametersAdvancedSearch> getParametersAdvancedSearchVM() {
        if(parametersAdvancedSearchVM == null) parametersAdvancedSearchVM = new MutableLiveData<>();
        return parametersAdvancedSearchVM;
    }
}
