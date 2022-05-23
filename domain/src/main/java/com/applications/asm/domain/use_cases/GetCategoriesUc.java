package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.exception.GetCategoryError;
import com.applications.asm.domain.exception.GetCategoryException;
import com.applications.asm.domain.exception.PlacesRepositoryError;
import com.applications.asm.domain.exception.PlacesRepositoryException;
import com.applications.asm.domain.executor.PostExecutionThread;
import com.applications.asm.domain.executor.ThreadExecutor;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.UseCase;

import java.util.List;
import java.util.logging.Logger;

import io.reactivex.rxjava3.core.Observable;

public class GetCategoriesUc extends UseCase<List<Category>, GetCategoriesUc.Params> {
    private final PlacesRepository placesRepository;
    private final Logger log = Logger.getLogger("com.applications.asm.domain.use_cases.GetCategoriesUc");

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

    public GetCategoriesUc(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PlacesRepository placesRepository) {
        super(threadExecutor, postExecutionThread);
        this.placesRepository = placesRepository;
    }

    @Override
    public Observable<List<Category>> buildUseCaseObservable(Params params) {
        return Observable.fromCallable(() -> getCategories(params.category, params.latitude, params.longitude, params.locale));
    }

    private List<Category> getCategories(String category, Double latitude, Double longitude, String locale) throws GetCategoryException {
        if(category == null || latitude == null || longitude == null || locale == null) throw new GetCategoryException(GetCategoryError.ANY_VALUE_IS_NULL);
        try {
            return placesRepository.getCategories(category, latitude, longitude, locale);
        } catch(PlacesRepositoryException placesRepositoryException) {
            String TAG = "GetCategoriesUc";
            PlacesRepositoryError placesRepositoryError = placesRepositoryException.getError();
            switch (placesRepositoryError) {
                case CONNECTION_WITH_SERVER_ERROR:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetCategoryException(GetCategoryError.CONNECTION_WITH_SERVER_ERROR);
                case DECODING_RESPONSE_ERROR:
                case CREATE_REQUEST_ERROR:
                case DO_REQUEST_ERROR:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetCategoryException(GetCategoryError.REQUEST_RESPONSE_ERROR);
                case RESPONSE_NULL:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetCategoryException(GetCategoryError.RESPONSE_NULL);
                default:
                    log.info(TAG + ": " + placesRepositoryError.getMessage());
                    throw new GetCategoryException(GetCategoryError.SERVER_ERROR);
            }
        }
    }
}
