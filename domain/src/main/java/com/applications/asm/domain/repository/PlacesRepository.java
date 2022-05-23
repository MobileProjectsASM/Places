package com.applications.asm.domain.repository;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.entities.SuggestedPlace;
import com.applications.asm.domain.exception.PlacesRepositoryException;

import java.util.List;

public interface PlacesRepository {
    PlaceDetails getPlaceDetails(String placeId) throws PlacesRepositoryException;
    List<Place> getPlaces(String placeToFind, Double longitude, Double latitude, Integer radius, List<String> categories, Integer page) throws PlacesRepositoryException;
    List<Review> getReviews(String placeId) throws PlacesRepositoryException;
    List<SuggestedPlace> getSuggestedPlaces(String word, Double longitude, Double latitude) throws PlacesRepositoryException;
    List<Category> getCategories(String word, Double longitude, Double latitude, String locale) throws PlacesRepositoryException;
}
