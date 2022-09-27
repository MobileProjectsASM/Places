package com.applications.asm.places.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.use_cases.GetCategoriesUc;
import com.applications.asm.places.model.CategoryVM;
import com.applications.asm.places.model.CoordinatesVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.mappers.CategoryMapper;
import com.applications.asm.places.model.mappers.CoordinatesMapper;
import com.applications.asm.places.view_model.exception.ErrorUtils;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class SearchCategoriesViewModel extends ViewModel {
    private final GetCategoriesUc getCategoriesUc;
    private final CategoryMapper categoryMapper;
    private final CoordinatesMapper coordinatesMapper;

    public SearchCategoriesViewModel(GetCategoriesUc getCategoriesUc, CategoryMapper categoryMapper, CoordinatesMapper coordinatesMapper) {
        this.getCategoriesUc = getCategoriesUc;
        this.categoryMapper = categoryMapper;
        this.coordinatesMapper = coordinatesMapper;
    }

    public LiveData<Resource<List<CategoryVM>>> getCategories(String category, String locale, CoordinatesVM coordinatesVM) {
        Flowable<Resource<List<CategoryVM>>> flowableCategories = getCategoriesUc.execute(GetCategoriesUc.Params.forGetCategories(category, coordinatesMapper.getCoordinates(coordinatesVM), locale))
                .flatMap(response -> {
                    Single<Resource<List<CategoryVM>>> categoriesResource;
                    if(response.getError().isEmpty()) {
                        categoriesResource = Observable.fromIterable(response.getData())
                            .map(categoryMapper::getCategoryMV)
                            .toList()
                            .map(Resource::success);
                    } else
                        categoriesResource = Single.just(response.getError()).map(Resource::warning);
                    return categoriesResource;
                })
                .onErrorReturn(throwable -> ErrorUtils.resolveError(throwable, AdvancedSearchViewModel.class))
                .toFlowable();
        return LiveDataReactiveStreams.fromPublisher(flowableCategories);
    }
}
