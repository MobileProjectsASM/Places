package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.Price;
import com.applications.asm.domain.entities.SortCriteria;
import com.applications.asm.domain.entities.Validators;
import com.applications.asm.domain.exception.GetPlacesError;
import com.applications.asm.domain.exception.GetPlacesException;
import com.applications.asm.domain.exception.GetReviewsError;
import com.applications.asm.domain.exception.GetReviewsException;
import com.applications.asm.domain.exception.PlacesRepositoryError;
import com.applications.asm.domain.exception.PlacesRepositoryException;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.List;
import java.util.logging.Logger;

import io.reactivex.rxjava3.core.Single;

public class GetPlacesUc extends SingleUseCase<List<Place>, GetPlacesUc.Params> {
    private final PlacesRepository placesRepository;
    private final Validators validators;

    private static final Logger log = Logger.getLogger(GetPlacesUc.class.getName());

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
                throw new GetPlacesException(GetPlacesError.ANY_VALUE_IS_NULL);
            if(!validators.validateLatitudeRange(params.latitude) || !validators.validateLongitudeRange(params.longitude))
                throw new GetPlacesException(GetPlacesError.LAT_LON_OUT_OF_RANGE);
            if(!validators.validateRadiusRange(params.radius))
                throw new GetPlacesException(GetPlacesError.NEGATIVE_RADIUS);
            if(!validators.validatePage(params.page))
                throw new GetPlacesException(GetPlacesError.PAGE_OUT_OF_RANGE);
            return params;
        });
    }

    @Override
    protected Single<List<Place>> build(Params params) {
        return validateParams(params)
                .flatMap(param -> placesRepository.getPlaces(param.placeToFind, param.longitude, param.latitude, param.radius, getCategories(param.categories), param.sortCriteria.getKey(), getPrices(param.prices), param.isOpenNow, param.page))
                .doOnError(throwable -> {
                    Exception exception = (Exception) throwable;
                    if(exception instanceof PlacesRepositoryException) {
                        PlacesRepositoryError placesRepositoryError = ((PlacesRepositoryException) exception).getError();
                        switch (placesRepositoryError) {
                            case CONNECTION_WITH_SERVER_ERROR:
                                log.warning("Connection with server error: " + placesRepositoryError.getMessage());
                                throw new GetPlacesException(GetPlacesError.CONNECTION_WITH_SERVER_ERROR);
                            case DECODING_RESPONSE_ERROR:
                            case CREATE_REQUEST_ERROR:
                            case DO_REQUEST_ERROR:
                                log.warning("Request error: " + placesRepositoryError.getMessage());
                                throw new GetPlacesException(GetPlacesError.REQUEST_RESPONSE_ERROR);
                            case PAGE_OUT_OF_RANGE:
                                log.warning(getClass().getName() + ": " + placesRepositoryError.getMessage());
                                throw new GetPlacesException(GetPlacesError.NEGATIVE_RADIUS);
                            case RESPONSE_NULL:
                                log.warning("Response null: " + placesRepositoryError.getMessage());
                                throw new GetPlacesException(GetPlacesError.RESPONSE_NULL);
                            case SERVER_ERROR:
                                log.warning("Server error: " + placesRepositoryError.getMessage());
                                throw new GetReviewsException(GetReviewsError.SERVER_ERROR);
                            case NETWORK_ERROR:
                                log.warning("Network error: " +placesRepositoryError.getMessage());
                                throw new GetReviewsException(GetReviewsError.NETWORK_ERROR);
                        }
                    } else throw exception;
                });
    }

    private String getCategories(List<Category> categories) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < categories.size(); i++) {
            if(i == categories.size() - 1)
                stringBuilder.append(categories.get(i).getId());
            else if(i == 0)
                stringBuilder.append(categories.get(i).getId());
            else stringBuilder.append(",").append(categories.get(i).getId());
        }
        return stringBuilder.toString();
    }

    private String getPrices(List<Price> prices) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < prices.size(); i++) {
            if(i == prices.size() - 1)
                stringBuilder.append(prices.get(i));
            else if(i == 0)
                stringBuilder.append(prices.get(i));
            else stringBuilder.append(",").append(prices.get(i));
        }
        return stringBuilder.toString();
    }
}
