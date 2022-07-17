package com.applications.asm.data.framework.network.api_rest;

import com.applications.asm.data.framework.network.api_rest.api.YelpApiClient;
import com.applications.asm.data.framework.network.api_rest.dto.AutocompleteSuggestions;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;

public class PlacesRestServerImpl implements PlacesRestServer {
    private final YelpApiClient yelpApiClient;
    private final String apiKey;

    public PlacesRestServerImpl(YelpApiClient yelpApiClient, String apiKey) {
        this.yelpApiClient = yelpApiClient;
        this.apiKey = apiKey;
    }

    @Override
    public Single<Response<AutocompleteSuggestions>> getSuggestions(String word, Double latitude, Double longitude, String locale) {
        return yelpApiClient.getAutocompleteSuggestions(apiKey, word, latitude, longitude, locale);
    }
}
