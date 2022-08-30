package com.applications.asm.data.repository;

import com.apollographql.apollo.api.Error;
import com.applications.asm.data.PlaceReviewsQuery;
import com.applications.asm.data.framework.network.graphql.GraphqlPlacesClient;
import com.applications.asm.data.mapper.ReviewMapper;
import com.applications.asm.data.utils.ErrorUtils;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.repository.AllReviews;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class AllReviewsImpl implements AllReviews {
    private final GraphqlPlacesClient graphqlPlacesClient;
    private final ReviewMapper reviewMapper;

    @Inject
    public AllReviewsImpl(GraphqlPlacesClient graphqlPlacesClient, ReviewMapper reviewMapper) {
        this.graphqlPlacesClient = graphqlPlacesClient;
        this.reviewMapper = reviewMapper;
    }

    @Override
    public Single<Response<List<Review>>> ofThisPlace(String placeId) {
        return graphqlPlacesClient.getPlaceReviews(placeId)
                .map(dataApolloResponse -> {
                    Response<List<Review>> response;
                    if(dataApolloResponse.hasErrors()) {
                        List<Error> errors = dataApolloResponse.getErrors();
                        if (errors != null) response = Response.error(ErrorUtils.getErrors(errors));
                        else response = Response.error(new ArrayList<>());
                    } else {
                        PlaceReviewsQuery.Data data = dataApolloResponse.getData();
                        if(data != null) {
                            PlaceReviewsQuery.Reviews reviews = data.reviews();
                            if(reviews != null) {
                                List<Review> reviewList = reviewMapper.queryReviewsToReviews(reviews.review());
                                response = Response.success(reviewList);
                            }
                            else response = Response.success(new ArrayList<>());
                        } else response = Response.success(new ArrayList<>());
                    }
                    return response;
                })
                .onErrorResumeNext(throwable -> Single.error(ErrorUtils.resolveError(throwable, getClass())));
    }
}
