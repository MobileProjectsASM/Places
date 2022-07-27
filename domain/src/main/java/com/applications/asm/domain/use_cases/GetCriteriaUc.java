package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Criterion;
import com.applications.asm.domain.exception.ParameterError;
import com.applications.asm.domain.exception.ParameterException;
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

    private Single<Criterion.Type> validateParams(Criterion.Type type) {
        return Single.fromCallable(() -> {
            if(type == null)
                throw new ParameterException(ParameterError.NULL_VALUE);
            return type;
        });
    }

    @Override
    protected Single<List<Criterion>> build(Criterion.Type type) {
        return validateParams(type)
                .flatMap(allCriteria::thatAre);
    }
}
