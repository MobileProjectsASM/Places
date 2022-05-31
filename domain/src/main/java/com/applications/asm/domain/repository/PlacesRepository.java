package com.applications.asm.domain.repository;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.entities.SuggestedPlace;
import com.applications.asm.domain.exception.PlacesRepositoryException;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface PlacesRepository {
    Single<PlaceDetails> getPlaceDetails(String placeId) throws PlacesRepositoryException;
    Single<List<Place>> getPlaces(String placeToFind, Double longitude, Double latitude, Integer radius, List<String> categories, Integer page);
    Single<List<Review>> getReviews(String placeId) throws PlacesRepositoryException;
    Single<List<SuggestedPlace>> getSuggestedPlaces(String word, Double longitude, Double latitude) throws PlacesRepositoryException;
    Single<List<Category>> getCategories(String word, Double longitude, Double latitude, String locale) throws PlacesRepositoryException;
}
