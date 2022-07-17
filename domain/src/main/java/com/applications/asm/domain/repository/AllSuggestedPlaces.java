package com.applications.asm.domain.repository;

import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.entities.SuggestedPlace;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface AllSuggestedPlaces {
    Single<Response<List<SuggestedPlace>>> withThisCriteria(String word, Coordinates coordinates);
}
