package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Location;
import com.applications.asm.domain.exception.ClientException;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetCategoriesUc extends SingleUseCase<List<Category>, GetCategoriesUc.Params> {
    private final PlacesRepository placesRepository;

    public static class Params {
        private final String category;
        private final Location location;
        private final String locale;

        private Params(String category, Location location, String locale) {
            this.category = category;
            this.location = location;
            this.locale = locale;
        }

        public static Params forGetCategories(String category, Location location, String locale) {
            return new Params(category, location, locale);
        }
    }

    public GetCategoriesUc(UseCaseScheduler useCaseScheduler, PlacesRepository placesRepository) {
        super(useCaseScheduler);
        this.placesRepository = placesRepository;
    }

    private Single<Params> validateParams(Params params) {
        return Single.fromCallable(() -> {
            if(params.category == null || params.location != null || params.locale == null)
                throw new ClientException("Null value was entered");
            return params;
        });
    }

    @Override
    protected Single<List<Category>> build(Params params) {
        return validateParams(params)
                .flatMap(param -> placesRepository.getCategories(param.category, param.location, param.locale));
    }
}
