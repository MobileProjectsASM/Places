package com.applications.asm.data.framework.network.graphql;

import android.content.Context;

import com.apollographql.apollo3.ApolloCall;
import com.apollographql.apollo3.ApolloClient;
import com.apollographql.apollo3.api.ApolloResponse;
import com.apollographql.apollo3.api.Optional;
import com.apollographql.apollo3.exception.ApolloException;
import com.apollographql.apollo3.rx3.Rx3Apollo;
import com.applications.asm.data.R;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GraphqlPlacesClientImpl implements GraphqlPlacesClient {
    private final ApolloClient apolloClient;
    private final Context context;

    @Inject
    public GraphqlPlacesClientImpl(ApolloClient apolloClient, Context context) {
        this.apolloClient = apolloClient;
        this.context = context;
    }

    @Override
    public Single<ApolloResponse<PlaceSuggestionQuery.Data>> getPlaceSuggestion(String placeId) {
        ApolloCall<PlaceSuggestionQuery.Data> placeLocationCall = apolloClient.query(new PlaceSuggestionQuery(placeId));
        return Rx3Apollo.single(placeLocationCall).onErrorResumeNext(throwable -> {
            Exception exception = (Exception) throwable;
            if(exception instanceof ApolloException) return Single.error(new GraphqlException(context.getString(R.string.network_error)));
            return Single.error(new GraphqlException(exception.getMessage()));
        });
    }

    @Override
    public Single<ApolloResponse<SearchPlacesQuery.Data>> getSearchedPlaces(String place, double latitude, double longitude, double radius, String categories, String sortBy, String price, Boolean isOpenNow, Integer initIndex, Integer limit) {
        Optional.Present<String> optionalPlace = new Optional.Present<>(place);
        Optional.Present<Double> optionalRadius = new Optional.Present<>(radius);
        Optional.Present<String> optionalSortBy = new Optional.Present<>(sortBy);
        Optional.Present<String> optionalPrice = new Optional.Present<>(price);
        Optional.Present<Boolean> optionalIsOpenNow = new Optional.Present<>(isOpenNow);
        Optional.Present<Integer> optionalInitIndex = new Optional.Present<>(initIndex);
        Optional.Present<Integer> optionalLimit = new Optional.Present<>(limit);
        ApolloCall<SearchPlacesQuery.Data> searchPlacesCall = apolloClient.query(new SearchPlacesQuery(optionalPlace, latitude, longitude, optionalRadius, categories, optionalSortBy, optionalPrice, optionalIsOpenNow, optionalInitIndex, optionalLimit));
        return Rx3Apollo.single(searchPlacesCall).onErrorResumeNext(throwable -> {
            Exception exception = (Exception) throwable;
            if(exception instanceof ApolloException) return Single.error(new GraphqlException(context.getString(R.string.network_error)));
            return Single.error(new GraphqlException(exception.getMessage()));
        });
    }

    @Override
    public Single<ApolloResponse<PlaceDetailsQuery.Data>> getPlaceDetails(String placeId) {
        ApolloCall<PlaceDetailsQuery.Data> searchPlacesCall = apolloClient.query(new PlaceDetailsQuery(placeId));
        return Rx3Apollo.single(searchPlacesCall).onErrorResumeNext(throwable -> {
            Exception exception = (Exception) throwable;
            if(exception instanceof ApolloException) return Single.error(new GraphqlException(context.getString(R.string.network_error)));
            return Single.error(new GraphqlException(exception.getMessage()));
        });
    }

    @Override
    public Single<ApolloResponse<PlaceReviewsQuery.Data>> getPlaceReviews(String placeId) {
        ApolloCall<PlaceReviewsQuery.Data> placesReviewsCall = apolloClient.query(new PlaceReviewsQuery(placeId));
        return Rx3Apollo.single(placesReviewsCall).onErrorResumeNext(throwable -> {
            Exception exception = (Exception) throwable;
            if(exception instanceof ApolloException) return Single.error(new GraphqlException(context.getString(R.string.network_error)));
            return Single.error(new GraphqlException(exception.getMessage()));
        });
    }
}
