package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Criterion;
import com.applications.asm.domain.entities.FoundPlaces;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.exception.ParameterException;
import com.applications.asm.domain.repository.Validators;
import com.applications.asm.domain.repository.AllPlaces;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetPlacesUc extends SingleUseCase<Response<FoundPlaces>, GetPlacesUc.Params> {
    private final AllPlaces allPlaces;
    private final Validators validators;

    public static class Params {
        private final String placeToFind;
        private final Coordinates coordinates;
        private final Integer radius;
        private final List<Category> categories;
        private final Criterion sortCriterion;
        private final List<Criterion> pricesCriteria;
        private final Boolean isOpenNow;
        private final Integer page;

        private Params(String placeToFind, Coordinates coordinates, Integer radius, List<Category> categories, Criterion sortCriterion, List<Criterion> pricesCriteria, Boolean isOpenNow, Integer page) {
            this.placeToFind = placeToFind;
            this.coordinates = coordinates;
            this.radius = radius;
            this.categories = categories;
            this.sortCriterion = sortCriterion;
            this.pricesCriteria = pricesCriteria;
            this.isOpenNow = isOpenNow;
            this.page = page;
        }

        public static Params forFilterPlaces(String placeToFind, Coordinates coordinates, Integer radius, List<Category> categories, Criterion sortCriterion, List<Criterion> pricesCriteria, Boolean isOpenNow, Integer page) {
            return new Params(placeToFind, coordinates, radius, categories, sortCriterion, pricesCriteria, isOpenNow, page);
        }
    }

    @Inject
    public GetPlacesUc(UseCaseScheduler useCaseScheduler, AllPlaces allPlaces, Validators validators) {
        super(useCaseScheduler);
        this.allPlaces = allPlaces;
        this.validators = validators;
    }

    private Single<Params> validateParams(Params params) {
        return Single.fromCallable(() -> {
            if(params.placeToFind == null || params.coordinates == null || params.radius == null || params.categories == null || params.sortCriterion == null || params.pricesCriteria == null || params.isOpenNow == null || params.page == null)
                throw new ParameterException("Error: You entered a null value");
            if(!validators.validateLatitudeRange(params.coordinates.getLatitude()) || !validators.validateLongitudeRange(params.coordinates.getLongitude()))
                throw new ParameterException("Error: Coordinates out of range");
            if(!validators.validateRadiusRange(params.radius))
                throw new ParameterException("Error: Search radius out of range");
            if(!validators.isValidPage(params.page))
                throw new ParameterException("Error: Page out of range");
            return params;
        });
    }

    @Override
    protected Single<Response<FoundPlaces>> build(Params params) {
        return validateParams(params)
                .flatMap(param -> allPlaces.withThisCriteria(param.placeToFind, param.coordinates, param.radius, param.categories, param.sortCriterion, param.pricesCriteria, param.isOpenNow, param.page));
    }

}
