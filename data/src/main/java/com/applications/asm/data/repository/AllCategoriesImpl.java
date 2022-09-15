package com.applications.asm.data.repository;

import com.applications.asm.data.framework.network.api_rest.PlacesRestServer;
import com.applications.asm.data.framework.network.api_rest.dto.AutocompleteSuggestions;
import com.applications.asm.data.mapper.CategoryMapper;
import com.applications.asm.data.utils.ErrorUtils;
import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.repository.AllCategories;

import java.util.ArrayList;
import java.util.List;

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
                        AutocompleteSuggestions autocompleteSuggestions = responseApiRest.body();
                        if(autocompleteSuggestions != null)  {
                            List<Category> categories = categoryMapper.categoriesDTOToCategories(autocompleteSuggestions.categories);
                            response = Response.success(categories);
                        } else response = Response.success(new ArrayList<>());
                    } else response = ErrorUtils.getResponseError(responseApiRest.code(), placesRestServer.parseError(responseApiRest));
                    return response;
                })
                .onErrorResumeNext(throwable -> Single.error(ErrorUtils.resolveError(throwable, getClass())));
    }
}
