package com.applications.asm.data.repository;

import com.apollographql.apollo3.api.Error;
import com.applications.asm.data.framework.network.graphql.GraphqlPlacesClient;
import com.applications.asm.data.mapper.ReviewMapper;
import com.applications.asm.data.utils.ErrorUtils;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.repository.AllReviews;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                    if(dataApolloResponse.errors == null) {
                        List<Review> reviews = reviewMapper.queryReviewsToReviews(Objects.requireNonNull(dataApolloResponse.data).reviews.review);
                        response = Response.success(reviews);
                    } else {
                        List<String> listErrors = new ArrayList<>();
                        List<Error> errors = dataApolloResponse.errors;
                        for(Error error: errors)
                            listErrors.add(error.getMessage());
                        response = Response.error(listErrors);
                    }
                    return response;
                })
                .onErrorResumeNext(throwable -> Single.error(ErrorUtils.resolveError(throwable)));
    }
}
