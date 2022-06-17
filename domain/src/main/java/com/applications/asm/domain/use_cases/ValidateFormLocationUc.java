package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.LatitudeState;
import com.applications.asm.domain.entities.LongitudeState;
import com.applications.asm.domain.entities.State;
import com.applications.asm.domain.entities.StatesKey;
import com.applications.asm.domain.entities.Validators;
import com.applications.asm.domain.exception.ValidateFormLocationError;
import com.applications.asm.domain.exception.ValidateFormLocationException;
import com.applications.asm.domain.use_cases.base.SingleUseCase;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.core.Single;

public class ValidateFormLocationUc extends SingleUseCase<Map<String, State>, ValidateFormLocationUc.Params> {
    private final Validators validators;
    private final Pattern regexDecimalNumber = Pattern.compile("-?[0-9.]*");

    public static class Params {
        private final String latitude;
        private final String longitude;

        private Params(String latitude, String longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public static Params forFormLocation(String latitude, String longitude) {
            return new Params(latitude, longitude);
        }
    }

    public ValidateFormLocationUc(UseCaseScheduler useCaseScheduler, Validators validators) {
        super(useCaseScheduler);
        this.validators = validators;
    }

    private Single<Params> validateParams(Params params) {
        return Single.fromCallable(() -> {
            if(params.latitude == null || params.longitude == null) throw new ValidateFormLocationException(ValidateFormLocationError.ANY_VALUES_IS_NULL);
            return params;
        });
    }

    @Override
    protected Single<Map<String, State>> build(Params params) {
        return validateParams(params)
                .flatMap(this::validateForm);
    }

    private Single<Map<String, State>> validateForm(Params params) {
        return Single.zip(validateLatitude(params.latitude), validateLongitude(params.longitude), (stateLat, stateLon) -> {
            Map<String, State> states = new HashMap<>();
            states.put(StatesKey.LATITUDE_STATE_KEY, stateLat);
            states.put(StatesKey.LONGITUDE_STATE_KEY, stateLon);
            return states;
        });
    }

    private Single<LatitudeState> validateLatitude(String latitude) {
        return Single.fromCallable(() -> {
            if(latitude.isEmpty())
                return LatitudeState.EMPTY;
            else if(!regexDecimalNumber.matcher(latitude).matches())
                return LatitudeState.INVALID;
            else if(validators.validateLatitudeRange(Double.parseDouble(latitude)))
                return LatitudeState.OUT_OF_RANGE;
            else return LatitudeState.OK;
        });
    }

    private Single<LongitudeState> validateLongitude(String longitude) {
        return Single.fromCallable(() -> {
            if(longitude.isEmpty())
                return LongitudeState.EMPTY;
            else if(!regexDecimalNumber.matcher(longitude).matches())
                return LongitudeState.INVALID;
            else if(validators.validateLongitudeRange(Double.parseDouble(longitude)))
                return LongitudeState.OUT_OF_RANGE;
            else return LongitudeState.OK;
        });
    }
}
