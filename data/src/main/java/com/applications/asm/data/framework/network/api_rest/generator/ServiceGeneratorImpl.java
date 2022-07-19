package com.applications.asm.data.framework.network.api_rest.generator;

import retrofit2.Retrofit;

public class ServiceGeneratorImpl<T> implements ServiceGenerator<T> {
    private final Retrofit retrofit;
    private T service;

    public ServiceGeneratorImpl(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public Retrofit getRetrofit() {
        return retrofit;
    }

    @Override
    public T getService(Class<T> serviceClass) {
        if(service == null) service = retrofit.create(serviceClass);
        return service;
    }
}
