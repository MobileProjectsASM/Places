package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Criterion;
import com.applications.asm.domain.repository.AllCriteria;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetCriteriaUc extends SingleUseCase<List<Criterion>, Criterion.Type> {
    private final AllCriteria allCriteria;

    public GetCriteriaUc(UseCaseScheduler useCaseScheduler, AllCriteria allCriteria) {
        super(useCaseScheduler);
        this.allCriteria = allCriteria;
    }

    @Override
    protected Single<List<Criterion>> build(Criterion.Type type) {
        return allCriteria.thatAre(type);
    }
}
