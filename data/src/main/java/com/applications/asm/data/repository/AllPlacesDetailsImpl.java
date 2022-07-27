package com.applications.asm.data.repository;

import com.apollographql.apollo3.api.ApolloResponse;
import com.applications.asm.data.framework.local.database.PlacesDatabase;
import com.applications.asm.data.framework.local.database.entities.CriterionEntity;
import com.applications.asm.data.framework.network.graphql.GraphqlPlacesClient;
import com.applications.asm.data.framework.network.graphql.PlaceDetailsQuery;
import com.applications.asm.data.mapper.PlaceDetailsMapper;
import com.applications.asm.data.utils.ErrorUtils;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.repository.AllPlacesDetails;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class AllPlacesDetailsImpl implements AllPlacesDetails {
    private final GraphqlPlacesClient graphqlPlacesClient;
    private final PlacesDatabase placesDatabase;
    private final PlaceDetailsMapper placeDetailsMapper;

    @Inject
    public AllPlacesDetailsImpl(GraphqlPlacesClient graphqlPlacesClient, PlacesDatabase placesDatabase, PlaceDetailsMapper placeDetailsMapper) {
        this.graphqlPlacesClient = graphqlPlacesClient;
        this.placesDatabase = placesDatabase;
        this.placeDetailsMapper = placeDetailsMapper;
    }

    @Override
    public Single<Response<PlaceDetails>> withId(String placeId) {
        //String currentLanguage = Locale.getDefault().getLanguage();
        Single<Map<String, String>> daysSingle = placesDatabase.getDayDao().getDays("es");
        Single<Map<String, String>> pricesSingle = placesDatabase.getCriterionDao().getCriteria(CriterionEntity.priceCriterion, "es");
        Single<ApolloResponse<PlaceDetailsQuery.Data>> placeDetailsSingle = graphqlPlacesClient.getPlaceDetails(placeId);
        return Single.zip(placeDetailsSingle, daysSingle, pricesSingle, (dataApolloResponse, daysMap, pricesMap) -> {
            Response<PlaceDetails> response;
            if(dataApolloResponse.errors == null) {
                if(dataApolloResponse.data != null)
                    response = Response.success(placeDetailsMapper.placeDetailsQueryToPlaceDetails(dataApolloResponse.data.business, daysMap, pricesMap));
                throw new Exception("null response");
            } else
                response = Response.error(ErrorUtils.getErrors(dataApolloResponse.errors));
            return response;
        })
        .onErrorResumeNext(throwable -> Single.error(ErrorUtils.resolveError(throwable, getClass())));
    }

}
