package com.applications.asm.data.framework.local.database;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class PlacesDbClientImpl implements PlacesDbClient {
    private final PlacesDatabase placesDatabase;

    @Inject
    public PlacesDbClientImpl(PlacesDatabase placesDatabase) {
        this.placesDatabase = placesDatabase;
    }

    @Override
    public Single<Map<String, String>> getCriteria(String criterionType, String language) {
        return placesDatabase.getCriterionDao().getCriteria(criterionType, language)
                .onErrorResumeNext(throwable -> {
                    Exception exception = (Exception) throwable;
                    return Single.error(new DatabaseException(exception.getMessage()));
                });
    }

    @Override
    public Single<Map<String, String>> getDays(String language) {
        return placesDatabase.getDayDao().getDays(language)
                .onErrorResumeNext(throwable -> {
                    Exception exception = (Exception) throwable;
                    return Single.error(new DatabaseException(exception.getMessage()));
                });
    }
}
