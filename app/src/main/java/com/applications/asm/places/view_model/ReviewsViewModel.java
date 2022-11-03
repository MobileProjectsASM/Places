package com.applications.asm.places.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.use_cases.GetReviewsUc;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.ReviewVM;
import com.applications.asm.places.model.mappers.ReviewMapper;
import com.applications.asm.places.view_model.exception.ErrorUtils;

import java.util.List;

public class ReviewsViewModel extends ViewModel {
    private final GetReviewsUc getReviewsUc;
    private final ReviewMapper reviewMapper;

    public ReviewsViewModel(GetReviewsUc getReviewsUc, ReviewMapper reviewMapper) {
        this.getReviewsUc = getReviewsUc;
        this.reviewMapper = reviewMapper;
    }

    public LiveData<Resource<List<ReviewVM>>> getReviews(String placeId, String locale) {
        MediatorLiveData<Resource<List<ReviewVM>>> liveDataReviews = new MediatorLiveData<>();
        liveDataReviews.setValue(Resource.loading());
        LiveData<Resource<List<ReviewVM>>> sourceReviews = LiveDataReactiveStreams.fromPublisher(getReviewsUc.execute(GetReviewsUc.Params.forGetReviews(placeId, locale))
            .map(response -> {
                Resource<List<ReviewVM>> resource;
                if(response.getError() == null || response.getError().isEmpty()) resource = Resource.success(reviewMapper.getReviewsVM(response.getData()));
                else resource = Resource.warning(response.getError());
                return resource;
            })
            .onErrorReturn(throwable -> ErrorUtils.resolveError(throwable, ReviewsViewModel.class))
            .toFlowable());
        liveDataReviews.addSource(sourceReviews, resource -> {
            liveDataReviews.setValue(resource);
            liveDataReviews.removeSource(sourceReviews);
        });
        return liveDataReviews;
    }
}
