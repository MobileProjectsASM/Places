package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.SortCriteria;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetSortCriteriaUc extends SingleUseCase<List<SortCriteria>, Object> {
    private final PlacesRepository placesRepository;

    public GetSortCriteriaUc(UseCaseScheduler useCaseScheduler, PlacesRepository placesRepository) {
        super(useCaseScheduler);
        this.placesRepository = placesRepository;
    }

    @Override
    protected Single<List<SortCriteria>> build(Object o) {
        return placesRepository.getSortCriteria();
    }
}
