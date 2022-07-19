package com.applications.asm.data.repository;

import com.applications.asm.data.framework.network.api_rest.PlacesRestServer;
import com.applications.asm.data.framework.network.api_rest.dto.APIError;
import com.applications.asm.data.framework.network.graphql.GraphqlPlacesClient;
import com.applications.asm.data.mapper.SuggestedPlaceMapper;
import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.entities.SuggestedPlace;
import com.applications.asm.domain.exception.PlacesServiceException;
import com.applications.asm.domain.repository.AllSuggestedPlaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.core.Single;

public class AllSuggestedPlacesImpl implements AllSuggestedPlaces {
    private final PlacesRestServer placesRestServer;
    private final GraphqlPlacesClient graphqlPlacesClient;
    private final SuggestedPlaceMapper suggestedPlaceMapper;

    public AllSuggestedPlacesImpl(PlacesRestServer placesRestServer, GraphqlPlacesClient graphqlPlacesClient, SuggestedPlaceMapper suggestedPlaceMapper) {
        this.placesRestServer = placesRestServer;
        this.graphqlPlacesClient = graphqlPlacesClient;
        this.suggestedPlaceMapper = suggestedPlaceMapper;
    }

    @Override
    public Single<Response<List<SuggestedPlace>>> withThisCriteria(String word, Coordinates coordinates) {
        return placesRestServer.getSuggestions(word, coordinates.getLatitude(), coordinates.getLongitude(), null)
                .flatMap(response -> {
                    if(response.isSuccessful()) {
                        return Single.just(Objects.requireNonNull(response.body()))
                                .map(autocompleteSuggestions -> autocompleteSuggestions.businesses)
                                .flattenAsObservable(businesses -> businesses)
                                .map(business -> business.id)
                                .flatMap(id -> graphqlPlacesClient.getPlaceLocation(id).toObservable())
                                .map(dataApolloResponse -> {
                                    if(dataApolloResponse.errors == null)
                                        return suggestedPlaceMapper.placeLocationToSuggestedPlace(Objects.requireNonNull(dataApolloResponse.data).business);
                                    return new SuggestedPlace("", "", "", "");
                                })
                                .toList()
                                .map(Response::success);
                    } else {
                        APIError apiError = placesRestServer.parseError(response);
                        List<String> errors = new ArrayList<>();
                        errors.add(apiError.error.description);
                        return Single.<Response<List<SuggestedPlace>>>just(Response.error(errors));
                    }
                })
                .onErrorResumeNext(throwable -> {
                    Exception exception = (Exception) throwable;
                    if(exception instanceof IOException)
                        return Single.error(new PlacesServiceException("Internet connection error"));
                    return Single.error(new PlacesServiceException("Conversion error"));
                });
    }
}
