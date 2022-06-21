package com.applications.asm.places.view_model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.exception.GetCategoryError;
import com.applications.asm.domain.exception.GetCategoryException;
import com.applications.asm.domain.use_cases.GetCategoriesUc;
import com.applications.asm.places.R;
import com.applications.asm.places.model.CategoryVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.mappers.CategoryMapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class CategoriesViewModel extends ViewModel {
    private final GetCategoriesUc getCategoriesUc;
    private final CategoryMapper categoryMapper;
    private final CompositeDisposable composite;

    private MutableLiveData<Resource<List<CategoryVM>>> categoriesLD;

    public CategoriesViewModel(
        GetCategoriesUc getCategoriesUc,
        CategoryMapper categoryMapper,
        CompositeDisposable composite
    ) {
        this.getCategoriesUc = getCategoriesUc;
        this.categoryMapper = categoryMapper;
        this.composite = composite;
    }

    public LiveData<Resource<List<CategoryVM>>> getCategories() {
        return categoriesLD;
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
                    int message;
                    if(exception instanceof GetCategoryException) {
                        GetCategoryException getCategoryException = (GetCategoryException) exception;
                        GetCategoryError getCategoryError = getCategoryException.getError();
                        Log.e(getClass().getName(), getCategoryError.getMessage());
                        switch (getCategoryError) {
                            case CONNECTION_WITH_SERVER_ERROR:
                                message = R.string.error_connection_with_server;
                                break;
                            case REQUEST_RESPONSE_ERROR:
                                message = R.string.error_server;
                                break;
                            case NETWORK_ERROR:
                                message = R.string.error_network_connection;
                                break;
                            default: message = R.string.error_unknown;
                        }
                    } else {
                        Log.e(getClass().getName(), exception.getMessage());
                        message = R.string.error_unknown;
                    }
                    categoriesLD.setValue(Resource.error(message));
                });
        composite.add(getCategoriesDisposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        composite.clear();
    }
}
