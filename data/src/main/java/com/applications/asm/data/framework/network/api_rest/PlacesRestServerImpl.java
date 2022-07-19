package com.applications.asm.data.framework.network.api_rest;

import com.applications.asm.data.framework.network.api_rest.api.YelpApiClient;
import com.applications.asm.data.framework.network.api_rest.dto.APIError;
import com.applications.asm.data.framework.network.api_rest.dto.AutocompleteSuggestions;
import com.applications.asm.data.framework.network.api_rest.generator.ServiceGenerator;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Objects;

import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class PlacesRestServerImpl implements PlacesRestServer {
    private final ServiceGenerator<YelpApiClient> serviceGenerator;

    public PlacesRestServerImpl(ServiceGenerator<YelpApiClient> serviceGenerator) {
        this.serviceGenerator = serviceGenerator;
    }

    @Override
    public Single<Response<AutocompleteSuggestions>> getSuggestions(String word, Double latitude, Double longitude, String locale) {
        return serviceGenerator.getService(YelpApiClient.class).getAutocompleteSuggestions(word, latitude, longitude, locale);
    }

    @Override
    public APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter = serviceGenerator.getRetrofit().responseBodyConverter(APIError.class, new Annotation[0]);

        try {
            return converter.convert(Objects.requireNonNull(response.errorBody()));
        } catch (IOException e) {
            return new APIError();
        }
    }
}
