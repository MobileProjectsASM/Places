package com.applications.asm.data.framework.network.graphql;

import com.apollographql.apollo3.api.ApolloResponse;

import io.reactivex.rxjava3.core.Single;


public interface GraphqlPlacesClient {
    Single<ApolloResponse<PlaceSuggestionQuery.Data>> getPlaceSuggestion(String placeId);
    Single<ApolloResponse<SearchPlacesQuery.Data>> getSearchedPlaces(String place, double latitude, double longitude, double radius, String categories, String sortBy, String price, Boolean isOpenNow, Integer initIndex, Integer limit);
    Single<ApolloResponse<PlaceDetailsQuery.Data>> getPlaceDetails(String placeId);
    Single<ApolloResponse<PlaceReviewsQuery.Data>> getPlaceReviews(String placeId);
}
