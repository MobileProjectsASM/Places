package com.applications.asm.data.framework.network.graphql;

import com.apollographql.apollo.api.Response;
import com.applications.asm.data.PlaceDetailsQuery;
import com.applications.asm.data.PlaceReviewsQuery;
import com.applications.asm.data.PlaceSuggestionQuery;
import com.applications.asm.data.SearchPlacesQuery;

import io.reactivex.rxjava3.core.Single;


public interface GraphqlPlacesClient {
    Single<Response<PlaceSuggestionQuery.Data>> getPlaceSuggestion(String placeId);
    Single<Response<SearchPlacesQuery.Data>> getSearchedPlaces(String place, double latitude, double longitude, double radius, String categories, String sortBy, String price, Boolean isOpenNow, Integer initIndex, Integer limit);
    Single<Response<PlaceDetailsQuery.Data>> getPlaceDetails(String placeId);
    Single<Response<PlaceReviewsQuery.Data>> getPlaceReviews(String placeId);
}
