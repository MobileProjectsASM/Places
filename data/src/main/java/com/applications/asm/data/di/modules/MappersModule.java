package com.applications.asm.data.di.modules;

import com.applications.asm.data.mapper.CategoryMapper;
import com.applications.asm.data.mapper.CategoryMapperImpl;
import com.applications.asm.data.mapper.CoordinatesMapper;
import com.applications.asm.data.mapper.CoordinatesMapperImpl;
import com.applications.asm.data.mapper.CriterionMapper;
import com.applications.asm.data.mapper.CriterionMapperImpl;
import com.applications.asm.data.mapper.PlaceDetailsMapper;
import com.applications.asm.data.mapper.PlaceDetailsMapperImpl;
import com.applications.asm.data.mapper.PlaceMapper;
import com.applications.asm.data.mapper.PlaceMapperImpl;
import com.applications.asm.data.mapper.ReviewMapper;
import com.applications.asm.data.mapper.ReviewMapperImpl;
import com.applications.asm.data.mapper.SuggestedPlaceMapper;
import com.applications.asm.data.mapper.SuggestedPlaceMapperImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MappersModule {
    @Singleton
    @Binds
    abstract PlaceMapper providePlaceMapper(PlaceMapperImpl placeMapperImpl);

    @Singleton
    @Binds
    abstract CoordinatesMapper provideCoordinatesMapper(CoordinatesMapperImpl coordinatesMapperImpl);

    @Singleton
    @Binds
    abstract SuggestedPlaceMapper provideSuggestedPlaceMapper(SuggestedPlaceMapperImpl suggestedPlaceMapperImpl);

    @Singleton
    @Binds
    abstract CriterionMapper provideCriterionMapper(CriterionMapperImpl criterionMapperImpl);

    @Singleton
    @Binds
    abstract CategoryMapper provideCategoryMapper(CategoryMapperImpl categoryMapperImpl);

    @Singleton
    @Binds
    abstract PlaceDetailsMapper providePlaceDetailsMapper(PlaceDetailsMapperImpl placeDetailsMapperImpl);

    @Singleton
    @Binds
    abstract ReviewMapper provideReviewMapper(ReviewMapperImpl reviewMapperImpl);
}
