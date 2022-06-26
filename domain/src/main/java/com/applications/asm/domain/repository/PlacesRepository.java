package com.applications.asm.domain.repository;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Location;
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
    Single<List<Place>> getPlaces(String placeToFind, Location location, Integer radius, List<Category> categories, SortCriteria sortBy, List<Price> prices, Boolean isOpenNow, Integer page);
    Single<List<Review>> getReviews(String placeId);
    Single<List<SuggestedPlace>> getSuggestedPlaces(String word, Location location);
    Single<List<Category>> getCategories(String word, Location location, String locale);
    Single<List<SortCriteria>> getSortCriteria();
    Single<List<Price>> getPrices();
}
