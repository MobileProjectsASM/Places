package com.applications.asm.data.framework.local.database;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;

public interface PlacesDbClient {
    Single<Map<String, String>> getCriteria(String criterionType, String language);
    Single<Map<String, String>> getDays(String language);
}
