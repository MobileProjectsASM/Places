package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.exception.GetCategoryError;
import com.applications.asm.domain.exception.GetCategoryException;
import com.applications.asm.domain.exception.PlacesRepositoryError;
import com.applications.asm.domain.exception.PlacesRepositoryException;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.List;
import java.util.logging.Logger;

import io.reactivex.rxjava3.core.Single;

public class GetCategoriesUc extends SingleUseCase<List<Category>, GetCategoriesUc.Params> {
    private final PlacesRepository placesRepository;
    private static final Logger log = Logger.getLogger("com.applications.asm.domain.use_cases.GetCategoriesUc");

    public static class Params {
        private final String category;
        private final Double latitude;
        private final Double longitude;
        private final String locale;

        private Params(String category, Double latitude, Double longitude, String locale) {
            this.category = category;
            this.latitude = latitude;
            this.longitude = longitude;
            this.locale = locale;
        }

        public static Params forGetCategories(String category, Double latitude, Double longitude, String locale) {
            return new Params(category, latitude, longitude, locale);
        }
    }

    public GetCategoriesUc(UseCaseScheduler useCaseScheduler, PlacesRepository placesRepository) {
        super(useCaseScheduler);
        this.placesRepository = placesRepository;
    }

    private Single<Params> validateParams(Params params) {
        return Single.fromCallable(() -> {
            if(params.category == null || params.latitude == null || params.longitude == null || params.locale == null)
                throw new GetCategoryException(GetCategoryError.ANY_VALUE_IS_NULL);
            return params;
        });
    }

    @Override
    protected Single<List<Category>> build(Params params) {
        return validateParams(params)
                .flatMap(param -> placesRepository.getCategories(param.category, param.longitude, param.latitude, param.locale))
                .doOnError(throwable -> {
                    Exception exception = (Exception) throwable;
                    if(exception instanceof PlacesRepositoryException) {
                        PlacesRepositoryError placesRepositoryError = ((PlacesRepositoryException) exception).getError();
                        switch (placesRepositoryError) {
                            case CONNECTION_WITH_SERVER_ERROR:
                                log.info(getClass().getName() + ": " + placesRepositoryError.getMessage());
                                throw new GetCategoryException(GetCategoryError.CONNECTION_WITH_SERVER_ERROR);
                            case DECODING_RESPONSE_ERROR:
                            case CREATE_REQUEST_ERROR:
                            case DO_REQUEST_ERROR:
                                log.info(getClass().getName() + ": " + placesRepositoryError.getMessage());
                                throw new GetCategoryException(GetCategoryError.REQUEST_RESPONSE_ERROR);
                            case RESPONSE_NULL:
                                log.info(getClass().getName() + ": " + placesRepositoryError.getMessage());
                                throw new GetCategoryException(GetCategoryError.RESPONSE_NULL);
                            default:
                                log.info(getClass().getName() + ": " + placesRepositoryError.getMessage());
                                throw new GetCategoryException(GetCategoryError.SERVER_ERROR);
                        }
                    } throw exception;
                });
    }
}
