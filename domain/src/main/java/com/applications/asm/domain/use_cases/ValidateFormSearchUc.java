package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.RadiusState;
import com.applications.asm.domain.entities.State;
import com.applications.asm.domain.entities.StatesKey;
import com.applications.asm.domain.entities.Validators;
import com.applications.asm.domain.exception.ValidateFormSearchError;
import com.applications.asm.domain.exception.ValidateFormSearchException;
import com.applications.asm.domain.executor.PostExecutionThread;
import com.applications.asm.domain.executor.ThreadExecutor;
import com.applications.asm.domain.use_cases.base.UseCase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.core.Observable;

public class ValidateFormSearchUc extends UseCase<Map<String, State>, String> {
    private final Validators validators;
    private final Pattern regexIntegerNumber = Pattern.compile("[0-9]*");

    public ValidateFormSearchUc(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, Validators validators) {
        super(threadExecutor, postExecutionThread);
        this.validators = validators;
    }

    @Override
    public Observable<Map<String, State>> buildUseCaseObservable(String radius) {
        return Observable.fromCallable(() -> validateFormSearch(radius));
    }

    private Map<String, State> validateFormSearch(String radius) throws ValidateFormSearchException {
        Map<String, State> states = new HashMap<>();
        if(radius == null) throw new ValidateFormSearchException(ValidateFormSearchError.ANY_VALUES_IS_NULL);
        states.put(StatesKey.RADIUS_STATE_KEY, validateRadius(radius));
        return states;
    }

    private State validateRadius(String radius) {
        if(radius.isEmpty())
            return RadiusState.EMPTY;
        else if(!regexIntegerNumber.matcher(radius).matches())
            return RadiusState.INVALID;
        else if(validators.validateRadiusRange(Integer.getInteger(radius)))
            return RadiusState.OUT_OF_RANGE;
        else return RadiusState.OK;
    }
}
