package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.LatitudeState;
import com.applications.asm.domain.entities.LongitudeState;
import com.applications.asm.domain.entities.State;
import com.applications.asm.domain.entities.StatesKey;
import com.applications.asm.domain.entities.Validators;
import com.applications.asm.domain.exception.ValidateFormLocationError;
import com.applications.asm.domain.exception.ValidateFormLocationException;
import com.applications.asm.domain.executor.PostExecutionThread;
import com.applications.asm.domain.executor.ThreadExecutor;
import com.applications.asm.domain.use_cases.base.UseCase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.core.Observable;

public class ValidateFormLocationUc extends UseCase<Map<String, State>, ValidateFormLocationUc.Params> {
    private final Validators validators;
    private final Pattern regexDecimalNumber = Pattern.compile("-?[0-9.]*");

    public static class Params {
        private final String latitude;
        private final String longitude;

        private Params(String latitude, String longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public Params forFormLocation(String latitude, String longitude) {
            return new Params(latitude, longitude);
        }
    }

    public ValidateFormLocationUc(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, Validators validators) {
        super(threadExecutor, postExecutionThread);
        this.validators = validators;
    }

    @Override
    public Observable<Map<String, State>> buildUseCaseObservable(Params params) {
        return Observable.fromCallable(() -> validateForm(params.latitude, params.longitude));
    }

    public Map<String, State> validateForm(String latitude, String longitude) throws ValidateFormLocationException {
        Map<String, State> states = new HashMap<>();
        if(latitude == null || longitude == null) throw new ValidateFormLocationException(ValidateFormLocationError.ANY_VALUES_IS_NULL);
        states.put(StatesKey.LATITUDE_STATE_KEY, validateLatitude(latitude));
        states.put(StatesKey.LONGITUDE_STATE_KEY, validateLongitude(longitude));
        return states;
    }

    public State validateLatitude(String latitude) {
        if(latitude.isEmpty())
            return LatitudeState.EMPTY;
        else if(!regexDecimalNumber.matcher(latitude).matches())
            return LatitudeState.INVALID;
        else if(validators.validateLatitudeRange(Double.parseDouble(latitude)))
            return LatitudeState.OUT_OF_RANGE;
        else return LatitudeState.OK;
    }

    public State validateLongitude(String longitude) {
        if(longitude.isEmpty())
            return LongitudeState.EMPTY;
        else if(!regexDecimalNumber.matcher(longitude).matches())
            return LongitudeState.INVALID;
        else if(validators.validateLongitudeRange(Double.parseDouble(longitude)))
            return LongitudeState.OUT_OF_RANGE;
        else return LongitudeState.OK;
    }
}
