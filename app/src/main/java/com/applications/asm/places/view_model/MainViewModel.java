package com.applications.asm.places.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.use_cases.GetPlaceDetailsUc;
import com.applications.asm.domain.use_cases.GetPlacesUc;
import com.applications.asm.domain.use_cases.GetReviewsUc;
import com.applications.asm.domain.use_cases.base.DefaultObserver;
import com.applications.asm.places.model.PlaceDetailsM;
import com.applications.asm.places.model.PlaceM;
import com.applications.asm.places.model.ReviewM;
import com.applications.asm.places.model.mappers.PlaceDetailsMapper;
import com.applications.asm.places.model.mappers.PlaceMapper;
import com.applications.asm.places.model.mappers.ReviewMapper;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private final GetPlacesUc getPlacesUc;
    private final GetPlaceDetailsUc getPlaceDetailsUc;
    private final GetReviewsUc getReviewsUc;
    private final PlaceMapper placeMapper;
    private final PlaceDetailsMapper placeDetailsMapper;
    private final ReviewMapper reviewMapper;

    private final MutableLiveData<List<PlaceM>> _placesM;
    private final MutableLiveData<PlaceDetailsM> _placeDetailsM;
    private final MutableLiveData<List<ReviewM>> _reviewsM;

    public MainViewModel(
        GetPlacesUc getPlacesUc,
        GetPlaceDetailsUc getPlaceDetailsUc,
        GetReviewsUc getReviewsUc,
        PlaceMapper placeMapper,
        PlaceDetailsMapper placeDetailsMapper,
        ReviewMapper reviewMapper
    ) {
        this.getPlacesUc = getPlacesUc;
        this.getPlaceDetailsUc = getPlaceDetailsUc;
        this.getReviewsUc = getReviewsUc;
        this.placeMapper = placeMapper;
        this.placeDetailsMapper = placeDetailsMapper;
        this.reviewMapper = reviewMapper;
        _placesM = new MutableLiveData<>(new ArrayList<>());
        _placeDetailsM = new MutableLiveData<>(null);
        _reviewsM = new MutableLiveData<>(new ArrayList<>());
    }

    public void searchNearPlaces(String placeToFind, Double latitude, Double longitude, Integer radius, List<String> categories) {
        getPlacesUc.execute(new GetPlacesObserver(), GetPlacesUc.Params.forFilterPlaces(placeToFind, latitude, longitude, radius, categories));
    }

    public void getPlaceDetail(String placeId) {
        getPlaceDetailsUc.execute(new GetPlaceDetailObserver(), placeId);
    }

    public void getReviews(String placeId) {
        getReviewsUc.execute(new GetReviewsObserver(), placeId);
    }

    public LiveData<List<PlaceM>> places() {
        return _placesM;
    }

    public LiveData<PlaceDetailsM> placeDetail() { return _placeDetailsM; }

    public LiveData<List<ReviewM>> reviews() { return _reviewsM; }

    private class GetPlacesObserver extends DefaultObserver<List<Place>> {
        @Override
        public void onNext(List<Place> places) {
            List<PlaceM> placesMV = new ArrayList<>();
            for(Place place: places)
                placesMV.add(placeMapper.getPlaceMVFromPlace(place));
            _placesM.postValue(placesMV);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

    private class GetPlaceDetailObserver extends DefaultObserver<PlaceDetails> {
        @Override
        public void onNext(PlaceDetails placeDetails) {
            PlaceDetailsM placeDetailsM = placeDetailsMapper.getPlaceDetailsMFromPlaceDetails(placeDetails);
            _placeDetailsM.postValue(placeDetailsM);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

    private class GetReviewsObserver extends DefaultObserver<List<Review>> {
        @Override
        public void onNext(List<Review> reviews) {
            List<ReviewM> reviewsM = new ArrayList<>();
            for(Review review: reviews)
                reviewsM.add(reviewMapper.getReviewMFromReview(review));
            _reviewsM.postValue(reviewsM);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }
}
