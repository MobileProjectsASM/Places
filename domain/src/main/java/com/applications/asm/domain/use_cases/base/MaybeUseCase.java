package com.applications.asm.domain.use_cases.base;

import com.applications.asm.domain.exception.ErrorUtils;

import java.util.logging.Logger;

import io.reactivex.rxjava3.core.Maybe;

public abstract class MaybeUseCase<Result, Params> extends UseCase<Maybe<Result>, Params> {
    private UseCaseScheduler useCaseScheduler;
    private static final Logger logger = Logger.getLogger("com.applications.asm.domain.use_cases.base.MaybeUseCase");

    public MaybeUseCase(UseCaseScheduler useCaseScheduler) {
        this.useCaseScheduler = useCaseScheduler;
    }

    @Override
    protected Maybe<Result> execute(Params params, Boolean fromUseCase) {
        return super.execute(params, fromUseCase)
                .compose(transform -> {
                    if(fromUseCase) return transform;
                    else return transform.subscribeOn(useCaseScheduler.getRun()).observeOn(useCaseScheduler.getPost());
                })
                .onErrorResumeNext(throwable -> Maybe.error(ErrorUtils.resolveError(logger, throwable, getClass())));
    }

    public UseCaseScheduler getUseCaseScheduler() {
        return useCaseScheduler;
    }

    public void setUseCaseScheduler(UseCaseScheduler useCaseScheduler) {
        this.useCaseScheduler = useCaseScheduler;
    }
}
