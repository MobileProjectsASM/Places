package com.applications.asm.data.framework.network.api_rest;

import com.applications.asm.data.framework.network.api_rest.dto.APIError;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ErrorUtils {
    public static APIError parseError(Response<?> response, Retrofit retrofit) {
        Converter<ResponseBody, APIError> converter = retrofit.responseBodyConverter(APIError.class, new Annotation[0]);

        try {
            return converter.convert(Objects.requireNonNull(response.errorBody()));
        } catch (IOException e) {
            return new APIError();
        }
    }
}
