package com.applications.asm.data.repository;

import com.applications.asm.data.PlaceSuggestionQuery;
import com.applications.asm.data.framework.network.api_rest.PlacesRestServer;
import com.applications.asm.data.framework.network.api_rest.dto.APIError;
import com.applications.asm.data.framework.network.api_rest.dto.AutocompleteSuggestions;
import com.applications.asm.data.framework.network.api_rest.dto.Error;
import com.applications.asm.data.framework.network.graphql.GraphqlPlacesClient;
import com.applications.asm.data.mapper.SuggestedPlaceMapper;
import com.applications.asm.data.utils.ErrorUtils;
import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.entities.SuggestedPlace;
import com.applications.asm.domain.repository.AllSuggestedPlaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class AllSuggestedPlacesImpl implements AllSuggestedPlaces {
    private final PlacesRestServer placesRestServer;
    private final GraphqlPlacesClient graphqlPlacesClient;
    private final SuggestedPlaceMapper suggestedPlaceMapper;

    @Inject
    public AllSuggestedPlacesImpl(PlacesRestServer placesRestServer, GraphqlPlacesClient graphqlPlacesClient, SuggestedPlaceMapper suggestedPlaceMapper) {
        this.placesRestServer = placesRestServer;
        this.graphqlPlacesClient = graphqlPlacesClient;
        this.suggestedPlaceMapper = suggestedPlaceMapper;
    }

    @Override
    public Single<Response<List<SuggestedPlace>>> withThisCriteria(String word, Coordinates coordinates) {
        return placesRestServer.getSuggestions(word, coordinates.getLatitude(), coordinates.getLongitude(), null)
                .flatMap(response -> {
                    Single<Response<List<SuggestedPlace>>> suggestedPlacesSingle;
                    if(response.isSuccessful()) {
                        suggestedPlacesSingle = Single.just(Response.success(new ArrayList<>()));
                        AutocompleteSuggestions autocompleteSuggestions = response.body();
                        if(autocompleteSuggestions != null) {
                            suggestedPlacesSingle = Single.just(autocompleteSuggestions)
                                    .map(autocompleteSuggestions2 -> autocompleteSuggestions2.businesses)
                                    .flattenAsObservable(businesses -> businesses)
                                    .map(business -> business.id)
                                    .flatMap(id -> graphqlPlacesClient.getPlaceSuggestion(id).toObservable())
                                    .filter(dataApolloResponse -> !dataApolloResponse.hasErrors())
                                    .map(dataApolloResponse -> {
                                        PlaceSuggestionQuery.Data data = dataApolloResponse.getData();
                                        SuggestedPlace suggestedPlace = new SuggestedPlace("", "", "", "");
                                        if(data != null) suggestedPlace = suggestedPlaceMapper.businessToSuggestedPlace(data.business());
                                        return suggestedPlace;
                                    })
                                    .toList()
                                    .map(Response::success);
                        }
                    } else suggestedPlacesSingle = Single.just(ErrorUtils.getResponseError(response.code(), placesRestServer.parseError(response)));
                    return suggestedPlacesSingle;
                })
                .onErrorResumeNext(throwable -> Single.error(ErrorUtils.resolveError(throwable, getClass())));
    }
}
