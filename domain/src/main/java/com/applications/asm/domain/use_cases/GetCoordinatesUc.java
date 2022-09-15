package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.exception.ParameterException;
import com.applications.asm.domain.repository.AllCoordinates;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetCoordinatesUc extends SingleUseCase<Response<Coordinates>, Coordinates.State> {
    private final AllCoordinates allCoordinates;

    @Inject
    public GetCoordinatesUc(UseCaseScheduler useCaseScheduler, AllCoordinates allCoordinates) {
        super(useCaseScheduler);
        this.allCoordinates = allCoordinates;
    }

    private Single<Coordinates.State> validateParams(Coordinates.State state) {
        return Single.fromCallable(() -> {
            if(state == null)
                throw new ParameterException("You entered a null value");
            return state;
        });
    }

    @Override
    protected Single<Response<Coordinates>> build(Coordinates.State coordinatesState) {
        return validateParams(coordinatesState)
                .flatMap(allCoordinates::myLocation);
    }
}
