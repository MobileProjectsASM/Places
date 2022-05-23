package com.applications.asm.data.sources;

import com.applications.asm.data.exception.PlacesDataSourceWSException;
import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.ResponsePlacesModel;
import com.applications.asm.data.model.ResponseReviewsModel;
import com.applications.asm.data.model.ResponseSuggestedPlacesModel;

import java.io.IOException;
import java.util.List;

public interface PlacesDataSourceWS {
    ResponsePlacesModel getPlacesModel(String placeToFind, Double longitude, Double latitude, Integer radius, List<String> categories, Integer initIndex, Integer amount) throws IOException, RuntimeException, PlacesDataSourceWSException;
    PlaceDetailsModel getPlaceDetailsModel(String placeId) throws IOException, RuntimeException, PlacesDataSourceWSException;
    ResponseReviewsModel getReviewsModel(String placeId) throws IOException, RuntimeException, PlacesDataSourceWSException;
    ResponseSuggestedPlacesModel getSuggestedPlaces(String word, Double latitude, Double longitude) throws IOException, RuntimeException, PlacesDataSourceWSException;
}
