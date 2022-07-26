package com.applications.asm.data.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.apollographql.apollo3.ApolloClient;
import com.applications.asm.data.R;
import com.applications.asm.data.framework.local.database.PlacesDatabase;
import com.applications.asm.data.framework.network.api_rest.HeadersInterceptorApiRest;
import com.applications.asm.data.framework.network.api_rest.api.YelpApiClient;
import com.applications.asm.data.framework.network.api_rest.generator.ServiceGenerator;
import com.applications.asm.data.framework.network.api_rest.generator.ServiceGeneratorImpl;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class UtilsModule {

    @Named("database_name")
    @Provides
    String provideDatabaseName(Context context) {
        return context.getString(R.string.database_name);
    }

    @Named("preferences_name")
    @Provides
    String providePreferencesName(Context context) {
        return context.getString(R.string.preferences_name);
    }

    @Named("yelp_api_base_url")
    @Provides
    String provideApiBaseUrl(Context context) {
        return context.getString(R.string.yelp_api_base_url);
    }

    @Named("yelp_graphql_base_url")
    @Provides
    String provideGraphqlBaseUrl(Context context) {
        return context.getString(R.string.yelp_graphql_base_url);
    }

    @Named("api_authorization")
    @Provides
    String provideApiAuthorization(Context context) {
        return context.getString(R.string.api_authorization);
    }

    //REST SERVER
    @Provides
    ServiceGenerator<YelpApiClient> provideServiceGenerator(Retrofit retrofit) {
        return new ServiceGeneratorImpl<>(retrofit);
    }

    @Provides
    Retrofit provideRetrofit(Retrofit.Builder builder) {
        return builder.build();
    }

    @Provides
    Retrofit.Builder provideRetrofitBuilder(
            @Named("yelp_api_base_url") String yelpApiBaseUrl,
            GsonConverterFactory gsonConverterFactory,
            CallAdapter.Factory callAdapterFactory,
            OkHttpClient okHttpClient
    ) {
        return new Retrofit.Builder()
                .baseUrl(yelpApiBaseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(okHttpClient);
    }

    @Provides
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    CallAdapter.Factory provideCallAdapterFactory() {
        return RxJava3CallAdapterFactory.create();
    }

    @Provides
    OkHttpClient provideHttpClientBuilder(OkHttpClient.Builder okHttpClientBuilder) {
        return okHttpClientBuilder.build();
    }

    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder(@Named("interceptor_api_rest") Interceptor headerInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(headerInterceptor);
    }

    @Named("interceptor_api_rest")
    @Provides
    Interceptor provideHeaderInterceptorApiRest(@Named("api_authorization") String authorization) {
        return new HeadersInterceptorApiRest(authorization);
    }

    //GRAPHQL
    @Provides
    ApolloClient provideApolloClient(
            @Named("yelp_graphql_base_url") String yelpGraphqlBaseUrl,
            @Named("api_authorization") String apiKey
    ) {
        return new ApolloClient.Builder()
                .serverUrl(yelpGraphqlBaseUrl)
                .addHttpHeader("Authorization", apiKey)
                .addHttpHeader("Content-Type:", "application/graphql")
                .build();
    }

    //Database
    @Provides
    PlacesDatabase provideClientDb(RoomDatabase.Builder<PlacesDatabase> databaseBuilder) {
        return databaseBuilder.build();
    }

    @Provides
    RoomDatabase.Builder<PlacesDatabase> provideDatabaseBuilder(
            Context context,
            @Named("database_name") String databaseName
    ) {
        return Room.databaseBuilder(context, PlacesDatabase.class, databaseName)
                .createFromAsset("database/places.db");
    }

    //GPS
    @Provides
    FusedLocationProviderClient provideCancellationTokenSource(Context context) {
        return LocationServices.getFusedLocationProviderClient(context);
    }

    //SHARED PREFERENCES
    @Provides
    SharedPreferences provideSharedPreferences(
            Context context,
            @Named("preferences_name") String namePreferences
    ) {
        return context.getSharedPreferences(namePreferences, Context.MODE_PRIVATE);
    }
}
