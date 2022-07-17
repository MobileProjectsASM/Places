package com.applications.asm.data.repository;

import com.applications.asm.data.framework.network.api_rest.ErrorUtils;
import com.applications.asm.data.framework.network.api_rest.PlacesRestServer;
import com.applications.asm.data.framework.network.api_rest.dto.APIError;
import com.applications.asm.data.mapper.CategoryMapper;
import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.repository.AllCategories;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;

public class AllCategoriesImpl implements AllCategories {
    private final PlacesRestServer placesRestServer;
    private final CategoryMapper categoryMapper;

    public AllCategoriesImpl(PlacesRestServer placesRestServer, CategoryMapper categoryMapper) {
        this.placesRestServer = placesRestServer;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Single<Response<List<Category>>> withThisCriteria(String word, Coordinates coordinates, String locale) {
        return placesRestServer.getSuggestions(word, coordinates.getLatitude(), coordinates.getLongitude(), locale)
                .map(response -> {
                    if(response.isSuccessful()) {
                        List<Category> categories = categoryMapper.categoriesDTOToCategories(Objects.requireNonNull(response.body()).categories);
                        return Response.success(categories);
                    }
                    APIError apiError = ErrorUtils.parseError(response, new Retrofit.Builder().build());
                    List<String> errors = new ArrayList<>();
                    errors.add(apiError.error.description);
                    return Response.error(errors);
                });
    }
}
