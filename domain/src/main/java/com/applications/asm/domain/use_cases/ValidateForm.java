package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.LatitudeState;
import com.applications.asm.domain.entities.LongitudeState;
import com.applications.asm.domain.entities.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;

public class ValidateForm {

    public void test() {
        Observable<State> obs1 = Observable.just(LatitudeState.EMPTY, LatitudeState.INVALID, LatitudeState.OUT_OF_RANGE, LatitudeState.OK);
        Observable<State> obs2 = Observable.just(LongitudeState.EMPTY, LongitudeState.OK);
        Observable.combineLatest(obs1, obs2, (n,m) -> {
            Map<String, State> states = new HashMap<>();
            states.put("latitude_state", n);
            return states;
        });
    }
}
