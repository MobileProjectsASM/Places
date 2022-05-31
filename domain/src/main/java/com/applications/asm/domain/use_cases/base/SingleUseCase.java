package com.applications.asm.domain.use_cases.base;

import java.util.logging.Level;
import java.util.logging.Logger;

import io.reactivex.rxjava3.core.Single;


public abstract class SingleUseCase<Result, Params> extends UseCase<Single<Result>, Params> {
    private UseCaseScheduler useCaseScheduler;
    private static final Logger logger = Logger.getLogger("com.applications.asm.domain.use_cases.base.SingleUseCase");

    public SingleUseCase(UseCaseScheduler useCaseScheduler) {
        this.useCaseScheduler = useCaseScheduler;
    }

    @Override
    protected Single<Result> execute(Params params, Boolean fromUseCase) {
        return super.execute(params, fromUseCase)
                .compose(transformer -> {
                    if(fromUseCase) return transformer;
                    return transformer.subscribeOn(useCaseScheduler.getRun()).observeOn(useCaseScheduler.getPost());
                })
                .doOnError(throwable -> logger.log(Level.SEVERE, throwable.getMessage()))
                .doOnSuccess(result -> logger.log(Level.INFO, getClass().getName() + ": " + params + " => " + result));
    }

    public UseCaseScheduler getUseCaseScheduler() {
        return useCaseScheduler;
    }

    public void setUseCaseScheduler(UseCaseScheduler useCaseScheduler) {
        this.useCaseScheduler = useCaseScheduler;
    }
}
