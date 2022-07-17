package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.exception.ClientException;
import com.applications.asm.domain.repository.AllCoordinates;
import com.applications.asm.domain.use_cases.base.CompletableUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class SaveLocationUc extends CompletableUseCase<Coordinates> {
    private final AllCoordinates allCoordinates;

    public SaveLocationUc(UseCaseScheduler useCaseScheduler, AllCoordinates allCoordinates) {
        super(useCaseScheduler);
        this.allCoordinates = allCoordinates;
    }

    private Single<Coordinates> validateParams(Coordinates coordinates) {
        return Single.fromCallable(() -> {
            if(coordinates == null)
                throw new ClientException("Null value was entered");
            return coordinates;
        });
    }

    @Override
    protected Completable build(Coordinates coordinates) {
        return validateParams(coordinates).flatMapCompletable(param -> allCoordinates.saveThis(coordinates));
    }
}
