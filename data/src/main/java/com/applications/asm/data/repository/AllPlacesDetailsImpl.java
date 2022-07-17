package com.applications.asm.data.repository;

import com.apollographql.apollo3.api.Error;
import com.applications.asm.data.framework.network.graphql.GraphqlPlacesClient;
import com.applications.asm.data.mapper.PlaceDetailsMapper;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.repository.AllPlacesDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.core.Single;

public class AllPlacesDetailsImpl implements AllPlacesDetails {
    private final GraphqlPlacesClient graphqlPlacesClient;
    private final PlaceDetailsMapper placeDetailsMapper;

    public AllPlacesDetailsImpl(GraphqlPlacesClient graphqlPlacesClient, PlaceDetailsMapper placeDetailsMapper) {
        this.graphqlPlacesClient = graphqlPlacesClient;
        this.placeDetailsMapper = placeDetailsMapper;
    }

    @Override
    public Single<Response<PlaceDetails>> withId(String placeId) {
        return graphqlPlacesClient.getPlaceDetails(placeId)
                .map(dataApolloResponse -> {
                    if(dataApolloResponse.errors == null) {
                        PlaceDetails placeDetails = placeDetailsMapper.placeDetailsQueryToPlaceDetails(Objects.requireNonNull(dataApolloResponse.data).business);
                        return Response.success(placeDetails);
                    }
                    List<String> listErrors = new ArrayList<>();
                    List<Error> errors = dataApolloResponse.errors;
                    for(Error error: errors)
                        listErrors.add(error.getMessage());
                    return Response.error(listErrors);
                });
    }
}
