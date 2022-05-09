package com.applications.asm.domain.use_cases;

import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.exception.ConnectionServer;
import com.applications.asm.domain.exception.ResponseNull;
import com.applications.asm.domain.executor.PostExecutionThread;
import com.applications.asm.domain.executor.ThreadExecutor;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.base.UseCase;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import jdk.internal.org.jline.utils.Log;

public class GetPlaceDetailsUc extends UseCase<PlaceDetails, String> {
    private final PlacesRepository placesRepository;
    private final String TAG = "GetPlaceDetailsUc";

    public GetPlaceDetailsUc(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PlacesRepository placesRepository) {
        super(threadExecutor, postExecutionThread);
        this.placesRepository = placesRepository;
    }

    @Override
    public Observable<PlaceDetails> buildUseCaseObservable(String placeId) {
        return Observable.fromCallable(() -> getDetails(placeId));
    }

    private PlaceDetails getDetails(String placeId) throws ResponseNull {
        try {
            PlaceDetails placeDetails = placesRepository.getPlaceDetails(placeId);
            throw new ResponseNull("Error desconocido");
        } catch (ConnectionServer connectionServer) {
            Log.error(TAG + " : " + connectionServer.getMessage());
            throw new ResponseNull(connectionServer.getMessage());
        }
    }
}
