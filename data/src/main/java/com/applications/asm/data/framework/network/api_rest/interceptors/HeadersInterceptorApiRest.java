package com.applications.asm.data.framework.network.api_rest.interceptors;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeadersInterceptorApiRest implements Interceptor {
    private final String authorization;
    private final String contentType;

    public HeadersInterceptorApiRest(String authorization, String contentType) {
        this.authorization = authorization;
        this.contentType = contentType;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .header("Authorization", authorization)
                .header("Content-Type", contentType);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
