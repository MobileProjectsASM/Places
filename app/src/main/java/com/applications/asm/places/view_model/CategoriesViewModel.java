package com.applications.asm.places.view_model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.exception.ClientException;
import com.applications.asm.domain.exception.PlacesServiceException;
import com.applications.asm.domain.use_cases.GetCategoriesUc;
import com.applications.asm.domain.use_cases.LoadLocationUc;
import com.applications.asm.places.R;
import com.applications.asm.places.model.CategoryVM;
import com.applications.asm.places.model.LocationVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.mappers.CategoryMapper;
import com.applications.asm.places.model.mappers.LocationMapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class CategoriesViewModel extends ViewModel {
    private final LoadLocationUc loadLocationUc;
    private final GetCategoriesUc getCategoriesUc;
    private final CategoryMapper categoryMapper;
    private final LocationMapper locationMapper;
    private final CompositeDisposable composite;

    private MutableLiveData<Resource<LocationVM>> savedLocationLD;
    private MutableLiveData<Resource<List<CategoryVM>>> categoriesLD;

    public CategoriesViewModel(
        LoadLocationUc loadLocationUc,
        GetCategoriesUc getCategoriesUc,
        LocationMapper locationMapper,
        CategoryMapper categoryMapper,
        CompositeDisposable composite
    ) {
        this.loadLocationUc = loadLocationUc;
        this.getCategoriesUc = getCategoriesUc;
        this.locationMapper = locationMapper;
        this.categoryMapper = categoryMapper;
        this.composite = composite;
    }

    public LiveData<Resource<LocationVM>> getSavedLocation() {
        return savedLocationLD != null ? savedLocationLD : new MutableLiveData<>();
    }

    public LiveData<Resource<List<CategoryVM>>> getCategories() {
        return categoriesLD != null ? categoriesLD : new MutableLiveData<>();
    }

    public void loadLocation() {
        savedLocationLD.setValue(Resource.loading());
        Disposable loadLocationDisposable = loadLocationUc
                .execute(null)
                .map(locationMapper::getLocationVM)
                .subscribe(locationVM -> savedLocationLD.setValue(Resource.success(locationVM)), error -> {
                    Exception exception = (Exception) error;
                    Log.e(getClass().getName(), exception.getMessage());
                    savedLocationLD.setValue(Resource.error(R.string.error_unknown));
                });
        composite.add(loadLocationDisposable);
    }

    public void getCategories(String text, Double latitude, Double longitude, String locale) {
        Disposable getCategoriesDisposable = getCategoriesUc
                .execute(GetCategoriesUc.Params.forGetCategories(text, latitude, longitude, locale))
                .map(categories -> {
                    List<CategoryVM> categoriesMV = new ArrayList<>();
                    for(Category category: categories)
                        categoriesMV.add(categoryMapper.getCategoryMV(category));
                    return categoriesMV;
                })
                .subscribe(categoriesMV -> categoriesLD.setValue(Resource.success(categoriesMV)), error -> {
                    Exception exception = (Exception) error;
                    Log.e(getClass().getName(), exception.getMessage());
                    if(exception instanceof ClientException)
                        categoriesLD.setValue(Resource.error(R.string.error_client_input_data));
                    else if(exception instanceof PlacesServiceException)
                        categoriesLD.setValue(Resource.error(R.string.error_to_get_categories));
                    else
                        categoriesLD.setValue(Resource.error(R.string.error_unknown));
                });
        composite.add(getCategoriesDisposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        composite.clear();
    }
}
