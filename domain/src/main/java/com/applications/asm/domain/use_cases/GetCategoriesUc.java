package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.exception.ParameterException;
import com.applications.asm.domain.repository.AllCategories;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetCategoriesUc extends SingleUseCase<Response<List<Category>>, GetCategoriesUc.Params> {
    private final AllCategories allCategories;

    public static class Params {
        private final String category;
        private final Coordinates coordinates;
        private final String locale;

        private Params(String category, Coordinates coordinates, String locale) {
            this.category = category;
            this.coordinates = coordinates;
            this.locale = locale;
        }

        public static Params forGetCategories(String category, Coordinates coordinates, String locale) {
            return new Params(category, coordinates, locale);
        }
    }

    @Inject
    public GetCategoriesUc(UseCaseScheduler useCaseScheduler, AllCategories allCategories) {
        super(useCaseScheduler);
        this.allCategories = allCategories;
    }

    private Single<Params> validateParams(Params params) {
        return Single.fromCallable(() -> {
            if(params.category == null || params.coordinates == null || params.locale == null)
                throw new ParameterException("You entered a null value");
            return params;
        });
    }

    @Override
    protected Single<Response<List<Category>>> build(Params params) {
        return validateParams(params)
                .flatMap(param -> allCategories.withThisCriteria(param.category, param.coordinates, param.locale));
    }
}
