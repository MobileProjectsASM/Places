package com.applications.asm.data.sources;

import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.ResponsePlacesModel;
import com.applications.asm.data.model.ResponseReviewsModel;
import com.applications.asm.data.model.ReviewModel;

import java.io.IOException;
import java.util.List;

public interface PlacesDataSource {
    ResponsePlacesModel getPlacesModel(String placeToFind, Double longitude, Double latitude, Integer radius, List<String> categories) throws IOException;
    PlaceDetailsModel getPlaceDetailsModel(String placeId) throws IOException;
    ResponseReviewsModel getReviewsModel(String placeId) throws IOException;
}
