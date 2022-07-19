package com.applications.asm.data.framework.network.api_rest;

import com.applications.asm.data.framework.network.api_rest.dto.APIError;
import com.applications.asm.data.framework.network.api_rest.dto.AutocompleteSuggestions;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;

public interface PlacesRestServer {
    Single<Response<AutocompleteSuggestions>> getSuggestions(String word, Double latitude, Double longitude, String locale);
    APIError parseError(Response<?> response);
}
