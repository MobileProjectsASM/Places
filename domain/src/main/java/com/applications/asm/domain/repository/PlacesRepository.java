package com.applications.asm.domain.repository;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Price;
import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.entities.SortCriteria;
import com.applications.asm.domain.entities.SuggestedPlace;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface PlacesRepository {
    Single<PlaceDetails> getPlaceDetails(String placeId);
    Single<List<Place>> getPlaces(String placeToFind, Double longitude, Double latitude, Integer radius, List<Category> categories, SortCriteria sortBy, List<Price> prices, Boolean isOpenNow, Integer page);
    Single<List<Review>> getReviews(String placeId);
    Single<List<SuggestedPlace>> getSuggestedPlaces(String word, Double longitude, Double latitude);
    Single<List<Category>> getCategories(String word, Double longitude, Double latitude, String locale);
    Single<List<SortCriteria>> getSortCriteria();
    Single<List<Price>> getPrices();
}
