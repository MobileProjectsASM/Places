package com.applications.asm.data.framework.network.api_rest.generator;

import retrofit2.Retrofit;

public interface ServiceGenerator<T> {
    Retrofit getRetrofit();
    T getService(Class<T> serviceClass);
}
