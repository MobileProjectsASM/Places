package com.applications.asm.places.view.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.function.BiFunction;

public class MultipleLiveDataTransformation {

    public static <X, Y, O> LiveData<O> combineLatest(LiveData<X> liveDataX, LiveData<Y> liveDataY, BiFunction<X, Y, O> combine) {
        return new CombineLiveData<>(liveDataX, liveDataY, combine);
    }

    private static final class CombineLiveData<X, Y, O> extends MediatorLiveData<O> {
        private X x;
        private Y y;

        CombineLiveData(LiveData<X> liveDataX, LiveData<Y> liveDataY, BiFunction<X, Y, O> biFun) {
            if(liveDataX == liveDataY) {
                addSource(liveDataX, x -> {
                    this.x = x;
                    y = (Y) x;
                    setValue(biFun.apply(this.x, y));
                });
            } else {
                addSource(liveDataX, x -> {
                    if(x != null) {
                        this.x = x;
                        if(y != null) setValue(biFun.apply(this.x, y));
                    }
                });
                addSource(liveDataY, y -> {
                    if(y != null) {
                        this.y = y;
                        if(x != null) setValue(biFun.apply(x, this.y));
                    }
                });
            }
        }
    }
}
