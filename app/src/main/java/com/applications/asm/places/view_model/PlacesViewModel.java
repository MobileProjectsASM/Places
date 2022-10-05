package com.applications.asm.places.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.use_cases.GetPlacesUc;
import com.applications.asm.places.model.ParametersAdvancedSearch;
import com.applications.asm.places.model.PlaceVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.mappers.CategoryMapper;
import com.applications.asm.places.model.mappers.CoordinatesMapper;
import com.applications.asm.places.model.mappers.CriterionMapper;
import com.applications.asm.places.model.mappers.PlaceMapper;
import com.applications.asm.places.view_model.exception.ErrorUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class PlacesViewModel extends ViewModel {
    private final GetPlacesUc getPlacesUc;
    private final PlaceMapper placeMapper;
    private final CoordinatesMapper coordinatesMapper;
    private final CategoryMapper categoryMapper;
    private final CriterionMapper criterionMapper;

    private MutableLiveData<List<PlaceVM>> placesVM;

    public PlacesViewModel(GetPlacesUc getPlacesUc, PlaceMapper placeMapper, CoordinatesMapper coordinatesMapper, CategoryMapper categoryMapper, CriterionMapper criterionMapper) {
        this.getPlacesUc = getPlacesUc;
        this.placeMapper = placeMapper;
        this.coordinatesMapper = coordinatesMapper;
        this.categoryMapper = categoryMapper;
        this.criterionMapper = criterionMapper;
    }

    public LiveData<Resource<List<PlaceVM>>> getPlaces(ParametersAdvancedSearch parametersAdvancedSearch) {
        MediatorLiveData<Resource<List<PlaceVM>>> liveDataPlaces = new MediatorLiveData<>();
        liveDataPlaces.setValue(Resource.loading());
        GetPlacesUc.Params params = GetPlacesUc.Params.forFilterPlaces(parametersAdvancedSearch.getPlace(), coordinatesMapper.getCoordinates(parametersAdvancedSearch.getCoordinatesVM()), parametersAdvancedSearch.getRadius(), categoryMapper.getCategories(parametersAdvancedSearch.getCategories()), criterionMapper.getCriterion(parametersAdvancedSearch.getSortCriterion()), criterionMapper.getCriteria(parametersAdvancedSearch.getPricesCriterion()), parametersAdvancedSearch.getOpenNow(), parametersAdvancedSearch.getPage());
        LiveData<Resource<List<PlaceVM>>> sourcePlaces = LiveDataReactiveStreams.fromPublisher(getPlacesUc.execute(params)
            .flatMap(listResponse ->  {
                Single<Resource<List<PlaceVM>>> placesResource;
                if(listResponse.getError() == null || listResponse.getError().isEmpty()) {
                    placesResource = Observable.fromIterable(listResponse.getData())
                        .map(placeMapper::getPlaceVM)
                        .toList()
                        .map(Resource::success);
                } else {
                    String error = listResponse.getError();
                    placesResource = Single.just(Resource.warning(error));
                }
                return placesResource;
            })
            .onErrorReturn(throwable -> ErrorUtils.resolveError(throwable, PlacesViewModel.class))
            .toFlowable());
        liveDataPlaces.addSource(sourcePlaces, resource -> {
            liveDataPlaces.setValue(resource);
            liveDataPlaces.removeSource(sourcePlaces);
        });
        return liveDataPlaces;
    }

    public MutableLiveData<List<PlaceVM>> getPlaces() {
        if(placesVM == null) placesVM = new MutableLiveData<>(new ArrayList<>());
        return placesVM;
    }
}
