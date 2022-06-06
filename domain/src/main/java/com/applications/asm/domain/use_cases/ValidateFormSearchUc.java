package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.RadiusState;
import com.applications.asm.domain.entities.State;
import com.applications.asm.domain.entities.StatesKey;
import com.applications.asm.domain.entities.Validators;
import com.applications.asm.domain.exception.ValidateFormSearchError;
import com.applications.asm.domain.exception.ValidateFormSearchException;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.core.Single;

public class ValidateFormSearchUc extends SingleUseCase<Map<String, State>, String> {
    private final Validators validators;
    private final Pattern regexIntegerNumber = Pattern.compile("[0-9]*");

    public ValidateFormSearchUc(UseCaseScheduler useCaseScheduler, Validators validators) {
        super(useCaseScheduler);
        this.validators = validators;
    }

    @Override
    protected Single<Map<String, State>> build(String radius) {
        return validateParams(radius)
                .flatMap(this::validateFormSearch);
    }

    private Single<String> validateParams(String radius) {
        return Single.fromCallable(() -> {
            if(radius == null) throw new ValidateFormSearchException(ValidateFormSearchError.ANY_VALUES_IS_NULL);
            return radius;
        });
    }

    private Single<Map<String, State>> validateFormSearch(String radius) {
        return validateRadius(radius).map(radiusState -> {
            Map<String, State> states = new HashMap<>();
            states.put(StatesKey.RADIUS_STATE_KEY, radiusState);
            return states;
        });
    }

    private Single<RadiusState> validateRadius(String radius) {
        return Single.fromCallable(() -> {
            if(radius.isEmpty())
                return RadiusState.EMPTY;
            else if(!regexIntegerNumber.matcher(radius).matches())
                return RadiusState.INVALID;
            else if(validators.validateRadiusRange(Integer.getInteger(radius)))
                return RadiusState.OUT_OF_RANGE;
            else return RadiusState.OK;
        });
    }
}
