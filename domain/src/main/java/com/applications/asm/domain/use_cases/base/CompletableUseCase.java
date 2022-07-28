package com.applications.asm.domain.use_cases.base;

import com.applications.asm.domain.exception.ParameterException;
import com.applications.asm.domain.exception.RepositoryException;
import com.applications.asm.domain.exception.UseCaseException;

import java.util.logging.Level;
import java.util.logging.Logger;

import io.reactivex.rxjava3.core.Completable;

public abstract class CompletableUseCase<Params> extends UseCase<Completable, Params> {
    private UseCaseScheduler useCaseScheduler;
    private static final Logger logger = Logger.getLogger("com.applications.asm.domain.use_cases.base.CompletableUseCase");

    public CompletableUseCase(UseCaseScheduler useCaseScheduler) {
        this.useCaseScheduler = useCaseScheduler;
    }

    @Override
    protected Completable execute(Params params, Boolean fromUseCase) {
        return super.execute(params, fromUseCase)
                .compose(transform -> {
                    if(fromUseCase) return transform;
                    return transform.subscribeOn(useCaseScheduler.getRun()).observeOn(useCaseScheduler.getPost());
                })
                .onErrorResumeNext(throwable -> {
                    Exception exception = (Exception) throwable;
                    if(exception instanceof ParameterException) {
                        ParameterException parameterException = (ParameterException) exception;
                        logger.log(Level.SEVERE, getClass().getName() + ": " + parameterException.getMessage());
                        return Completable.error(new UseCaseException(parameterException.getMessage()));
                    }
                    if(exception instanceof RepositoryException) {
                        RepositoryException repositoryException = (RepositoryException) exception;
                        logger.log(Level.SEVERE, repositoryException.getMessage());
                        return Completable.error(new UseCaseException(repositoryException.getMessage()));
                    }
                    logger.log(Level.SEVERE, exception.getMessage());
                    return Completable.error(new UseCaseException(exception.getMessage()));
                });
    }

    public UseCaseScheduler getUseCaseScheduler() {
        return useCaseScheduler;
    }

    public void setUseCaseScheduler(UseCaseScheduler useCaseScheduler) {
        this.useCaseScheduler = useCaseScheduler;
    }
}
