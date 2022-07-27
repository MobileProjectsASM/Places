package com.applications.asm.domain.use_cases.base;

import com.applications.asm.domain.exception.ParameterError;
import com.applications.asm.domain.exception.ParameterException;
import com.applications.asm.domain.exception.RepositoryException;

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
                .doOnError(throwable -> {
                    Exception exception = (Exception) throwable;
                    if(exception instanceof ParameterException)
                        logger.log(Level.SEVERE, getClass().getName() + ": Error en algun parametro");
                    else if(exception instanceof RepositoryException) {
                        RepositoryException repositoryException = (RepositoryException) exception;
                        logger.log(Level.SEVERE, repositoryException.getMessage());
                    } else {
                        logger.log(Level.SEVERE, exception.getMessage());
                    }
                })
                .doOnSuccess(result -> logger.log(Level.INFO, getClass().getName() + ": " + params + " => " + result));
    }

    public UseCaseScheduler getUseCaseScheduler() {
        return useCaseScheduler;
    }

    public void setUseCaseScheduler(UseCaseScheduler useCaseScheduler) {
        this.useCaseScheduler = useCaseScheduler;
    }
}
