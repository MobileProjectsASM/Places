package com.applications.asm.data.di.components;

import android.content.Context;

import com.applications.asm.data.di.modules.DataSourceModule;
import com.applications.asm.data.di.modules.MappersModule;
import com.applications.asm.data.di.modules.RepositoryModule;
import com.applications.asm.data.di.modules.UtilsModule;
import com.applications.asm.domain.repository.AllCategories;
import com.applications.asm.domain.repository.AllCoordinates;
import com.applications.asm.domain.repository.AllCriteria;
import com.applications.asm.domain.repository.AllPlaces;
import com.applications.asm.domain.repository.AllPlacesDetails;
import com.applications.asm.domain.repository.AllReviews;
import com.applications.asm.domain.repository.AllSuggestedPlaces;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {RepositoryModule.class, DataSourceModule.class, MappersModule.class, UtilsModule.class})
public interface DataComponent {
    @Component.Factory
    interface Factory {
        DataComponent create(@BindsInstance Context context);
    }

    AllCategories provideAllCategories();
    AllCoordinates provideAllCoordinates();
    AllCriteria provideAllCriteria();
    AllPlacesDetails provideAllPlacesDetails();
    AllPlaces provideAllPlaces();
    AllReviews provideAllReviews();
    AllSuggestedPlaces provideAllSuggestedPlaces();
}
