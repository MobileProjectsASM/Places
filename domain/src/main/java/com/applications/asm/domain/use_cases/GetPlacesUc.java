package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.exception.ConnectionServer;
import com.applications.asm.domain.executor.PostExecutionThread;
import com.applications.asm.domain.executor.ThreadExecutor;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.UseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import jdk.internal.org.jline.utils.Log;

public class GetPlacesUc extends UseCase<List<Place>, GetPlacesUc.Params> {
    private final PlacesRepository placesRepository;
    private final String TAG = "GetPlacesUc";

    public static class Params {
        private final String placeToFind;
        private final Double latitude;
        private final Double longitude;
        private final Integer radius;
        private final List<String> categories;

        private Params(String placeToFind, Double latitude, Double longitude, Integer radius, List<String> categories) {
            this.placeToFind = placeToFind;
            this.latitude = latitude == null || latitude <= 0 ? 19.441876 : latitude;
            this.longitude = longitude == null || longitude <= 0 ? -99.203783 : longitude;
            this.radius = radius;
            this.categories = categories;
        }

        public static Params forFilterPlaces(String placeToFind, Double latitude, Double longitude, Integer radius, List<String> categories) {
            return new Params(placeToFind, latitude, longitude, radius, categories);
        }
    }

    public GetPlacesUc(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PlacesRepository placesRepository) {
        super(threadExecutor, postExecutionThread);
        this.placesRepository = placesRepository;
    }

    @Override
    public Observable<List<Place>> buildUseCaseObservable(Params params) {
        return Observable.fromCallable(() -> getPlaces(params.placeToFind, params.latitude, params.longitude, params.radius, params.categories));
    }

    private List<Place> getPlaces(String placeToFind, Double latitude, Double longitude, Integer radius, List<String> categories) {
        try {
            return placesRepository.getPlaces(placeToFind, longitude, latitude, radius, categories);
        } catch (ConnectionServer connectionServer) {
            Log.error(TAG + " : " + connectionServer.getMessage());
            return new ArrayList<>();
        }
    }
}
