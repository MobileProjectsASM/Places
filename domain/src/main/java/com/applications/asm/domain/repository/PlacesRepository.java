package com.applications.asm.domain.repository;

import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.exception.ConnectionServer;

import java.util.List;

public interface PlacesRepository {
    PlaceDetails getPlaceDetails(String placeId) throws ConnectionServer;
    List<Place> getPlaces(String placeToFind, Double longitude, Double latitude, Integer radius, List<String> categories) throws ConnectionServer;
    List<Review> getReviews(String placeId) throws ConnectionServer;
}
