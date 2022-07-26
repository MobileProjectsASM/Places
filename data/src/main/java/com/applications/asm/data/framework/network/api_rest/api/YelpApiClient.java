package com.applications.asm.data.framework.network.api_rest.api;

import com.applications.asm.data.framework.network.api_rest.dto.AutocompleteSuggestions;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface YelpApiClient {
    @GET("autocomplete")
    Single<Response<AutocompleteSuggestions>> getAutocompleteSuggestions(@Query("text") String word, @Query("latitude") double latitude, @Query("longitude") double longitude, @Query("locale") String locale);
}
