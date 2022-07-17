package com.applications.asm.domain.repository;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Criterion;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.Response;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface AllPlaces {
    Single<Response<List<Place>>> withThisCriteria(String placeToFind, Coordinates coordinates, Integer radius, List<Category> categories, Criterion sortCriterion, List<Criterion> pricesCriteria, Boolean isOpenNow, Integer page);
}
