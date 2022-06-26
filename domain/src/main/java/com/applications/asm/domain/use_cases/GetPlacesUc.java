package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.Price;
import com.applications.asm.domain.entities.SortCriteria;
import com.applications.asm.domain.entities.Validators;
import com.applications.asm.domain.exception.ClientException;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetPlacesUc extends SingleUseCase<List<Place>, GetPlacesUc.Params> {
    private final PlacesRepository placesRepository;
    private final Validators validators;

    public static class Params {
        private final String placeToFind;
        private final Double latitude;
        private final Double longitude;
        private final Integer radius;
        private final List<Category> categories;
        private final SortCriteria sortCriteria;
        private final List<Price> prices;
        private final Boolean isOpenNow;
        private final Integer page;

        private Params(String placeToFind, Double latitude, Double longitude, Integer radius, List<Category> categories, SortCriteria sortCriteria, List<Price> prices, Boolean isOpenNow, Integer page) {
            this.placeToFind = placeToFind;
            this.latitude = latitude;
            this.longitude = longitude;
            this.radius = radius;
            this.categories = categories;
            this.sortCriteria = sortCriteria;
            this.prices = prices;
            this.isOpenNow = isOpenNow;
            this.page = page;
        }

        public static Params forFilterPlaces(String placeToFind, Double latitude, Double longitude, Integer radius, List<Category> categories, SortCriteria sortCriteria, List<Price> prices, Boolean isOpenNow, Integer page) {
            return new Params(placeToFind, latitude, longitude, radius, categories, sortCriteria, prices, isOpenNow, page);
        }
    }

    public GetPlacesUc(UseCaseScheduler useCaseScheduler, PlacesRepository placesRepository, Validators validators) {
        super(useCaseScheduler);
        this.placesRepository = placesRepository;
        this.validators = validators;
    }

    private Single<Params> validateParams(Params params) {
        return Single.fromCallable(() -> {
            if(params.placeToFind == null || params.latitude == null || params.longitude == null || params.radius == null || params.categories == null || params.sortCriteria == null || params.prices == null || params.isOpenNow == null || params.page == null)
                throw new ClientException("Null value was entered");
            if(!validators.validateLatitudeRange(params.latitude) || !validators.validateLongitudeRange(params.longitude))
                throw new ClientException("Location out of range: " + "[" + params.latitude + ", " + params.longitude + "]");
            if(!validators.validateRadiusRange(params.radius))
                throw new ClientException("Radius out of range: " + params.radius);
            if(!validators.validatePage(params.page))
                throw new ClientException("Page out of range: " + params.page);
            return params;
        });
    }

    @Override
    protected Single<List<Place>> build(Params params) {
        return validateParams(params)
                .flatMap(param -> placesRepository.getPlaces(param.placeToFind, param.longitude, param.latitude, param.radius, param.categories, param.sortCriteria, param.prices, param.isOpenNow, param.page));
    }

}
