package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.repository.AllCoordinates;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import io.reactivex.rxjava3.core.Single;

public class GetCoordinatesUc extends SingleUseCase<Coordinates, Coordinates.State> {
    private final AllCoordinates allCoordinates;

    public GetCoordinatesUc(UseCaseScheduler useCaseScheduler, AllCoordinates allCoordinates) {
        super(useCaseScheduler);
        this.allCoordinates = allCoordinates;
    }

    @Override
    protected Single<Coordinates> build(Coordinates.State coordinatesState) {
        return allCoordinates.myLocation(coordinatesState);
    }
}
