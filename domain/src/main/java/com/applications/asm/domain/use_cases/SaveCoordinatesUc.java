package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.exception.ParameterException;
import com.applications.asm.domain.repository.AllCoordinates;
import com.applications.asm.domain.use_cases.base.CompletableUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class SaveCoordinatesUc extends CompletableUseCase<Coordinates> {
    private final AllCoordinates allCoordinates;

    @Inject
    public SaveCoordinatesUc(UseCaseScheduler useCaseScheduler, AllCoordinates allCoordinates) {
        super(useCaseScheduler);
        this.allCoordinates = allCoordinates;
    }

    private Single<Coordinates> validateParams(Coordinates coordinates) {
        return Single.fromCallable(() -> {
            if(coordinates == null)
                throw new ParameterException("You entered a null value");
            return coordinates;
        });
    }

    @Override
    protected Completable build(Coordinates coordinates) {
        return validateParams(coordinates).flatMapCompletable(param -> allCoordinates.saveThis(coordinates));
    }
}
