package com.applications.asm.domain.repository;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Response;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface AllCategories {
    Single<Response<List<Category>>> withThisCriteria(String word, Coordinates coordinates, String locale);
}
