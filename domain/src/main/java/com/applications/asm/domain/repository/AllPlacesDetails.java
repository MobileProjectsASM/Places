package com.applications.asm.domain.repository;

import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Response;

import io.reactivex.rxjava3.core.Single;

public interface AllPlacesDetails {
    Single<Response<PlaceDetails>> withId(String placeId);
}
