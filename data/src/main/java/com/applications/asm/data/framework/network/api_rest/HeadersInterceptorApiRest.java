package com.applications.asm.data.framework.network.api_rest;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeadersInterceptorApiRest implements Interceptor {
    private final String authorization;

    public HeadersInterceptorApiRest(String authorization) {
        this.authorization = authorization;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .header("Authorization", authorization);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
