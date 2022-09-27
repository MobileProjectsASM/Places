package com.applications.asm.places.di.modules;

import com.applications.asm.places.di.scopes.ActivityScope;
import com.applications.asm.places.model.mappers.CategoryMapper;
import com.applications.asm.places.model.mappers.CategoryMapperImpl;
import com.applications.asm.places.model.mappers.CoordinatesMapper;
import com.applications.asm.places.model.mappers.CoordinatesMapperImpl;
import com.applications.asm.places.model.mappers.CriterionMapper;
import com.applications.asm.places.model.mappers.CriterionMapperImpl;
import com.applications.asm.places.model.mappers.PlaceMapper;
import com.applications.asm.places.model.mappers.PlaceMapperImpl;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MappersModule {
    @ActivityScope
    @Binds
    abstract CoordinatesMapper provideLocationMapper(CoordinatesMapperImpl coordinatesMapperImpl);

    @ActivityScope
    @Binds
    abstract PlaceMapper providePlaceMapper(PlaceMapperImpl placeMapperImpl);

    @ActivityScope
    @Binds
    abstract CategoryMapper provideCategoryMapper(CategoryMapperImpl categoryMapperImpl);

    @ActivityScope
    @Binds
    abstract CriterionMapper sortCriteriaMapper(CriterionMapperImpl criterionMapperImpl);
}
