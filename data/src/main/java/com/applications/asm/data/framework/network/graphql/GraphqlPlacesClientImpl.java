package com.applications.asm.data.framework.network.graphql;

import android.content.Context;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.rx3.Rx3Apollo;
import com.applications.asm.data.PlaceDetailsQuery;
import com.applications.asm.data.PlaceReviewsQuery;
import com.applications.asm.data.PlaceSuggestionQuery;
import com.applications.asm.data.R;
import com.applications.asm.data.SearchPlacesQuery;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
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
    public Single<Response<PlaceSuggestionQuery.Data>> getPlaceSuggestion(String placeId) {
        ApolloCall<PlaceSuggestionQuery.Data> apolloCall = apolloClient.query(PlaceSuggestionQuery.builder().id(placeId).build());
        Observable<Response<PlaceSuggestionQuery.Data>> obsPlaceSuggestion = Rx3Apollo.from(apolloCall);
        return Single.fromObservable(obsPlaceSuggestion).onErrorResumeNext(throwable -> { //Handle protocol error
                Exception exception = (Exception) throwable;
                Log.e(getClass().getName(), exception.getMessage());
                if (exception instanceof ApolloException)
                    return Single.error(new GraphqlException(context.getString(R.string.network_error)));
                return Single.error(new GraphqlException(exception.getMessage()));
            }
        );
    }

    @Override
    public Single<Response<SearchPlacesQuery.Data>> getSearchedPlaces(String place, double latitude, double longitude, double radius, String categories, String sortBy, String price, Boolean isOpenNow, Integer initIndex, Integer limit) {
        SearchPlacesQuery searchPlacesQuery = SearchPlacesQuery.builder()
                .term(place)
                .latitude(latitude)
                .longitude(longitude)
                .radius(radius)
                .categories(categories)
                .sortBy(sortBy)
                .price(price)
                .isOpenNow(isOpenNow)
                .offSet(initIndex)
                .isOpenNow(isOpenNow)
        .build();
        ApolloCall<SearchPlacesQuery.Data> searchPlacesCall = apolloClient.query(searchPlacesQuery);
        Observable<Response<SearchPlacesQuery.Data>> obsSearchPlaces = Rx3Apollo.from(searchPlacesCall);
        return Single.fromObservable(obsSearchPlaces).onErrorResumeNext(throwable -> { //Handle protocol error
            Exception exception = (Exception) throwable;
            Log.e(getClass().getName(), exception.getMessage());
            if(exception instanceof ApolloException) return Single.error(new GraphqlException(context.getString(R.string.network_error)));
            return Single.error(new GraphqlException(exception.getMessage()));
        });
    }

    @Override
    public Single<Response<PlaceDetailsQuery.Data>> getPlaceDetails(String placeId) {
        ApolloCall<PlaceDetailsQuery.Data> searchPlacesCall = apolloClient.query(PlaceDetailsQuery.builder().id(placeId).build());
        Observable<Response<PlaceDetailsQuery.Data>> obsPlaceDetails = Rx3Apollo.from(searchPlacesCall);
        return Single.fromObservable(obsPlaceDetails).onErrorResumeNext(throwable -> {
            Exception exception = (Exception) throwable;
            Log.e(getClass().getName(), exception.getMessage());
            if(exception instanceof ApolloException) return Single.error(new GraphqlException(context.getString(R.string.network_error)));
            return Single.error(new GraphqlException(exception.getMessage()));
        });
    }

    @Override
    public Single<Response<PlaceReviewsQuery.Data>> getPlaceReviews(String placeId) {
        ApolloCall<PlaceReviewsQuery.Data> placesReviewsCall = apolloClient.query(PlaceReviewsQuery.builder().placeId(placeId).build());
        Observable<Response<PlaceReviewsQuery.Data>> obsPlaceReviews = Rx3Apollo.from(placesReviewsCall);
        return Single.fromObservable(obsPlaceReviews).onErrorResumeNext(throwable -> {
            Exception exception = (Exception) throwable;
            Log.e(getClass().getName(), exception.getMessage());
            if(exception instanceof ApolloException) return Single.error(new GraphqlException(context.getString(R.string.network_error)));
            return Single.error(new GraphqlException(exception.getMessage()));
        });
    }
}
