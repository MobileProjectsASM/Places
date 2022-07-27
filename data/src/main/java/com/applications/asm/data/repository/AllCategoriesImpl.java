package com.applications.asm.data.repository;

import com.applications.asm.data.framework.network.api_rest.PlacesRestServer;
import com.applications.asm.data.framework.network.api_rest.dto.APIError;
import com.applications.asm.data.mapper.CategoryMapper;
import com.applications.asm.data.utils.ErrorUtils;
import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.repository.AllCategories;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class AllCategoriesImpl implements AllCategories {
    private final PlacesRestServer placesRestServer;
    private final CategoryMapper categoryMapper;

    @Inject
    public AllCategoriesImpl(PlacesRestServer placesRestServer, CategoryMapper categoryMapper) {
        this.placesRestServer = placesRestServer;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Single<Response<List<Category>>> withThisCriteria(String word, Coordinates coordinates, String locale) {
        return placesRestServer.getSuggestions(word, coordinates.getLatitude(), coordinates.getLongitude(), locale)
                .map(responseApiRest -> {
                    Response<List<Category>> response;
                    if(responseApiRest.isSuccessful()) {
                        List<Category> categories = categoryMapper.categoriesDTOToCategories(Objects.requireNonNull(responseApiRest.body()).categories);
                        response = Response.success(categories);
                    } else {
                        APIError apiError = placesRestServer.parseError(responseApiRest);
                        List<String> errors = new ArrayList<>();
                        errors.add(apiError.error.description);
                        response = Response.error(errors);
                    }
                    return response;
                })
                .onErrorResumeNext(throwable -> Single.error(ErrorUtils.resolveError(throwable, getClass())));
    }
}
