package com.applications.asm.data.repository;

import com.applications.asm.data.SearchPlacesQuery;
import com.applications.asm.data.framework.network.graphql.GraphqlPlacesClient;
import com.applications.asm.data.mapper.PlaceMapper;
import com.applications.asm.data.utils.ErrorUtils;
import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Criterion;
import com.applications.asm.domain.entities.FoundPlaces;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.repository.AllPlaces;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class AllPlacesImpl implements AllPlaces {
    private final GraphqlPlacesClient graphqlPlacesClient;
    private final PlaceMapper placeMapper;

    private final static Integer PLACES_PER_PAGE = 10;

    @Inject
    public AllPlacesImpl(GraphqlPlacesClient graphqlPlacesClient, PlaceMapper placeMapper) {
        this.graphqlPlacesClient = graphqlPlacesClient;
        this.placeMapper = placeMapper;
    }

    @Override
    public Single<Response<FoundPlaces>> withThisCriteria(String placeToFind, Coordinates coordinates, Integer radius, List<Category> categories, Criterion sortCriterion, List<Criterion> pricesCriteria, Boolean isOpenNow, Integer page, String locale) {
        return graphqlPlacesClient.getSearchedPlaces(placeToFind, coordinates.getLatitude(), coordinates.getLongitude(), radius, getCategories(categories), sortCriterion.getId(), getPriceCriteria(pricesCriteria), isOpenNow, PLACES_PER_PAGE * (page - 1), PLACES_PER_PAGE, locale)
                .map(dataApolloResponse -> {
                    Response<FoundPlaces> response;
                    if(!dataApolloResponse.hasErrors()) {
                        SearchPlacesQuery.Data data = dataApolloResponse.getData();
                        if(data != null) {
                            SearchPlacesQuery.Search search = data.search();
                            if(search != null) {
                                Integer totalResults = search.total();
                                int totalPages = 0;
                                if(totalResults != null) {
                                    boolean isExact = totalResults % PLACES_PER_PAGE == 0;
                                    int auxPages = totalResults / PLACES_PER_PAGE;
                                    totalPages = isExact ? auxPages : auxPages + 1;
                                }
                                List<Place> places = placeMapper.placesQueryToPlaces(search.business());
                                response = Response.success(new FoundPlaces(places, totalPages));
                            } else
                                response = Response.success(new FoundPlaces(new ArrayList<>(), 0));
                        } else
                            response = Response.success(new FoundPlaces(new ArrayList<>(), 0));
                    } else response = ErrorUtils.getResponseError(dataApolloResponse.getErrors());
                    return response;
                })
                .onErrorResumeNext(throwable -> Single.error(ErrorUtils.resolveError(throwable, getClass())));
    }

    private String getCategories(List<Category> categories) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < categories.size(); i++) {
            if(i > 0) stringBuilder.append(",").append(categories.get(i).getId());
            else stringBuilder.append(categories.get(i).getId());
        }
        return stringBuilder.toString();
    }

    private String getPriceCriteria(List<Criterion> priceCriteria) {
        List<String> prices = getPrices(priceCriteria);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < prices.size(); i++) {
            if(i > 0) stringBuilder.append(",").append(prices.get(i));
            else stringBuilder.append(prices.get(i));
        }
        return stringBuilder.toString();
    }

    private List<String> getPrices(List<Criterion> priceCriteria) {
        List<String> priceCriteriaString = new ArrayList<>();
        for(Criterion priceCriterion : priceCriteria) {
            if(priceCriterion.getId().compareTo("$") == 0) priceCriteriaString.add("1");
            else if(priceCriterion.getId().compareTo("$$") == 0) priceCriteriaString.add("2");
            else if(priceCriterion.getId().compareTo("$$$") == 0) priceCriteriaString.add("3");
            else priceCriteriaString.add("4");
        }
        return priceCriteriaString;
    }
}
