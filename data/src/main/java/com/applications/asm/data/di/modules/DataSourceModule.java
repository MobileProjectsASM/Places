package com.applications.asm.data.di.modules;

import com.applications.asm.data.framework.local.database.PlacesDbClient;
import com.applications.asm.data.framework.local.database.PlacesDbClientImpl;
import com.applications.asm.data.framework.local.hardware.GpsClient;
import com.applications.asm.data.framework.local.hardware.GpsClientImpl;
import com.applications.asm.data.framework.local.shared_preferences.AppSharedPreferences;
import com.applications.asm.data.framework.local.shared_preferences.LocalPersistenceClient;
import com.applications.asm.data.framework.network.api_rest.PlacesRestServer;
import com.applications.asm.data.framework.network.api_rest.PlacesRestServerImpl;
import com.applications.asm.data.framework.network.graphql.GraphqlPlacesClient;
import com.applications.asm.data.framework.network.graphql.GraphqlPlacesClientImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract PlacesRestServer providePlacesRestServer(PlacesRestServerImpl placesRestServerImpl);

    @Singleton
    @Binds
    abstract GraphqlPlacesClient provideGraphqlPlacesClient(GraphqlPlacesClientImpl graphqlPlacesClientImpl);

    @Singleton
    @Binds
    abstract LocalPersistenceClient provideLocalPersistenceClient(AppSharedPreferences appSharedPreferences);

    @Singleton
    @Binds
    abstract GpsClient provideGpsClient(GpsClientImpl gpsClientImpl);

    @Singleton
    @Binds
    abstract PlacesDbClient providePlacesDbClient(PlacesDbClientImpl placesDbClient);

}
