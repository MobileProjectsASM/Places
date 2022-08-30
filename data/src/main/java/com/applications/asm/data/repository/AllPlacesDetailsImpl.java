package com.applications.asm.data.repository;

import com.apollographql.apollo.api.Error;
import com.applications.asm.data.PlaceDetailsQuery;
import com.applications.asm.data.framework.local.database.PlacesDatabase;
import com.applications.asm.data.framework.local.database.entities.CriterionEntity;
import com.applications.asm.data.framework.network.graphql.GraphqlPlacesClient;
import com.applications.asm.data.mapper.PlaceDetailsMapper;
import com.applications.asm.data.utils.ErrorUtils;
import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Price;
import com.applications.asm.domain.repository.AllPlacesDetails;
import com.applications.asm.domain.entities.Response;

import java.util.ArrayList;
import java.util.List;
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
        Single<com.apollographql.apollo.api.Response<PlaceDetailsQuery.Data>> placeDetailsSingle = graphqlPlacesClient.getPlaceDetails(placeId);
        return Single.zip(placeDetailsSingle, daysSingle, pricesSingle, (dataApolloResponse, daysMap, pricesMap) -> {
            Response<PlaceDetails> response;
            if(dataApolloResponse.hasErrors()) {
                List<Error> errors = dataApolloResponse.getErrors();
                if (errors != null) response = Response.error(ErrorUtils.getErrors(errors));
                else response = Response.error(new ArrayList<>());
            } else {
                PlaceDetailsQuery.Data data = dataApolloResponse.getData();
                if(data != null) response = Response.success(placeDetailsMapper.placeDetailsQueryToPlaceDetails(data.business(), daysMap, pricesMap));
                else response = Response.success(new PlaceDetails("", "", new Coordinates((double) 0, (double) 0), "", new ArrayList<>(), "", (double) 0, new Price("", ""), "", (int) 0, new ArrayList<>(), false));
            }
            return response;
        })
        .onErrorResumeNext(throwable -> Single.error(ErrorUtils.resolveError(throwable, getClass())));
    }

}
