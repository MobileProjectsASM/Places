package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.SuggestedPlace;
import com.applications.asm.domain.exception.ConnectionServer;
import com.applications.asm.domain.executor.PostExecutionThread;
import com.applications.asm.domain.executor.ThreadExecutor;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.UseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import jdk.internal.org.jline.utils.Log;

public class GetSuggestedPlacesUc extends UseCase<List<SuggestedPlace>, GetSuggestedPlacesUc.Params> {
    private final PlacesRepository placesRepository;
    private final String TAG = "GetSuggestedPlaces";

    public static class Params {
        private final String place;
        private final Double longitude;
        private final Double latitude;

        private Params(String place, Double longitude, Double latitude) {
            this.place = place;
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public static Params forSuggestedPlaces(String place, Double longitude, Double latitude) {
            return new Params(place, longitude, latitude);
        }
    }

    public GetSuggestedPlacesUc(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PlacesRepository placesRepository) {
        super(threadExecutor, postExecutionThread);
        this.placesRepository = placesRepository;
    }

    @Override
    public Observable<List<SuggestedPlace>> buildUseCaseObservable(Params params) {
        return Observable.fromCallable(() -> getSuggestedPlaces(params.place, params.longitude, params.latitude));
    }

    private List<SuggestedPlace> getSuggestedPlaces(String place, Double longitude, Double latitude) {
        try {
            return placesRepository.getSuggestedPlaces(place, longitude, latitude);
        } catch (ConnectionServer connectionServer) {
            Log.error(TAG + " : " + connectionServer.getMessage());
            return new ArrayList<>();
        }
    }

}
