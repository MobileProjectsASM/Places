package com.applications.asm.data.framework.network.graphql;

import com.apollographql.apollo3.ApolloCall;
import com.apollographql.apollo3.ApolloClient;
import com.apollographql.apollo3.api.ApolloResponse;
import com.apollographql.apollo3.api.Optional;
import com.apollographql.apollo3.rx3.Rx3Apollo;

import io.reactivex.rxjava3.core.Single;

public class GraphqlPlacesClientImpl implements GraphqlPlacesClient {
    private final ApolloClient apolloClient;

    public GraphqlPlacesClientImpl(ApolloClient apolloClient) {
        this.apolloClient = apolloClient;
    }

    @Override
    public Single<ApolloResponse<PlaceLocationQuery.Data>> getPlaceLocation(String placeId) {
        ApolloCall<PlaceLocationQuery.Data> placeLocationCall = apolloClient.query(new PlaceLocationQuery(placeId));
        return Rx3Apollo.single(placeLocationCall);
    }

    @Override
    public Single<ApolloResponse<SearchPlacesQuery.Data>> getSearchedPlaces(String place, double latitude, double longitude, int radius, String categories, String sortBy, String price, Boolean isOpenNow, Integer initIndex, Integer limit) {
        Optional.Present<String> optionalPlace = new Optional.Present<>(place);
        Optional.Present<Integer> optionalRadius = new Optional.Present<>(radius);
        Optional.Present<String> optionalSortBy = new Optional.Present<>(sortBy);
        Optional.Present<String> optionalPrice = new Optional.Present<>(price);
        Optional.Present<Boolean> optionalIsOpenNow = new Optional.Present<>(isOpenNow);
        Optional.Present<Integer> optionalInitIndex = new Optional.Present<>(initIndex);
        Optional.Present<Integer> optionalLimit = new Optional.Present<>(limit);
        ApolloCall<SearchPlacesQuery.Data> searchPlacesCall = apolloClient.query(new SearchPlacesQuery(optionalPlace, latitude, longitude, optionalRadius, categories, optionalSortBy, optionalPrice, optionalIsOpenNow, optionalInitIndex, optionalLimit));
        return Rx3Apollo.single(searchPlacesCall);
    }

    @Override
    public Single<ApolloResponse<PlaceDetailsQuery.Data>> getPlaceDetails(String placeId) {
        ApolloCall<PlaceDetailsQuery.Data> searchPlacesCall = apolloClient.query(new PlaceDetailsQuery(placeId));
        return Rx3Apollo.single(searchPlacesCall);
    }

    @Override
    public Single<ApolloResponse<PlaceReviewsQuery.Data>> getPlaceReviews(String placeId) {
        ApolloCall<PlaceReviewsQuery.Data> placesReviewsCall = apolloClient.query(new PlaceReviewsQuery(placeId));
        return Rx3Apollo.single(placesReviewsCall);
    }
}
