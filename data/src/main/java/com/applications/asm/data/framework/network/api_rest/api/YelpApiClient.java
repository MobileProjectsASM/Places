package com.applications.asm.data.framework.network.api_rest.api;

import com.applications.asm.data.framework.network.api_rest.dto.AutocompleteSuggestions;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface YelpApiClient {
    @Headers("Authorization: Bearer LsV0_Qd5FDylR12CXKgfDH9aRPFGyJJ1aN0htb5qTIf5Tv4k7azpMOqmSeYSsWB73cCMdLKg3sm35mmyU-5yqjglgqTVdasClXDNQhlc3nG3UUQe51idRjzSx2BwYnYx")
    @GET("autocomplete")
    Single<Response<AutocompleteSuggestions>> getAutocompleteSuggestions(@Query("text") String word, @Query("latitude") double latitude, @Query("longitude") double longitude, @Query("locale") String locale);
}
