package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Price;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetPrices extends SingleUseCase<List<Price>, Object> {
    private final PlacesRepository placesRepository;

    public GetPrices(UseCaseScheduler useCaseScheduler, PlacesRepository placesRepository) {
        super(useCaseScheduler);
        this.placesRepository = placesRepository;
    }

    @Override
    protected Single<List<Price>> build(Object o) {
        return placesRepository.getPrices();
    }
}
