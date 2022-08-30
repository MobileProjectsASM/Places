package com.applications.asm.domain.use_cases.base;

import com.applications.asm.domain.exception.ParameterException;
import com.applications.asm.domain.exception.RepositoryException;
import com.applications.asm.domain.exception.UseCaseException;

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
                .onErrorResumeNext(throwable -> {
                    Exception exception = (Exception) throwable;
                    if(exception instanceof ParameterException) {
                        ParameterException parameterException = (ParameterException) exception;
                        logger.log(Level.SEVERE, getClass().getPackage().getName() + ": " + parameterException.getMessage());
                        return Single.error(new UseCaseException(parameterException.getMessage()));
                    }
                    if(exception instanceof RepositoryException) {
                        RepositoryException repositoryException = (RepositoryException) exception;
                        logger.log(Level.SEVERE, getClass().getPackage().getName() + ": " + repositoryException.getMessage());
                        return Single.error(new UseCaseException(repositoryException.getMessage()));
                    }
                    logger.log(Level.SEVERE, exception.getMessage());
                    return Single.error(new UseCaseException(exception.getMessage()));
                });
    }

    public UseCaseScheduler getUseCaseScheduler() {
        return useCaseScheduler;
    }

    public void setUseCaseScheduler(UseCaseScheduler useCaseScheduler) {
        this.useCaseScheduler = useCaseScheduler;
    }
}
