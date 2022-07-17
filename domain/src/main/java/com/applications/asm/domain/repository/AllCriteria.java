package com.applications.asm.domain.repository;

import com.applications.asm.domain.entities.Criterion;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface AllCriteria {
    Single<List<Criterion>> thatAre(Criterion.Type type);
}
