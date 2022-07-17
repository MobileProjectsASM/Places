package com.applications.asm.data.model.mapper;

import android.content.Context;

import com.applications.asm.data.R;
import com.applications.asm.data.model.LocationModel;
import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.PlaceModel;
import com.applications.asm.data.model.SuggestedPlaceModel;
import com.applications.asm.data.model.WorkingHoursModel;
import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Hour;
import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Price;
import com.applications.asm.domain.entities.SuggestedPlace;
import com.applications.asm.domain.entities.Schedule;

import java.util.ArrayList;
import java.util.List;

public class PlaceModelMapperImpl implements PlaceModelMapper {
    private final Context context;
    private final CategoryModelMapper categoryModelMapper;
    private final PriceModelMapper priceModelMapper;

    public PlaceModelMapperImpl(Context context, CategoryModelMapper categoryModelMapper, PriceModelMapper priceModelMapper) {
        this.context = context;
        this.categoryModelMapper = categoryModelMapper;
        this.priceModelMapper = priceModelMapper;
    }

    @Override
    public Place getPlace(PlaceModel placeModel) {
        String id = placeModel.getId();
        String name = placeModel.getName();
        String imageUrl = placeModel.getImageUrl();
        Double latitude = placeModel.getCoordinatesModel().getLatitude();
        Double longitude = placeModel.getCoordinatesModel().getLongitude();
        Coordinates coordinates = new Coordinates(latitude, longitude);
        List<Category> categories = categoryModelMapper.getCategories(placeModel.getCategories());
        String address = getAddress(placeModel.getLocationModel());
        return new Place(id, name, coordinates, imageUrl, categories, address);
    }

    @Override
    public PlaceDetails getPlaceDetails(PlaceDetailsModel placeDetailsModel) {
        String id = placeDetailsModel.getId();
        String name = placeDetailsModel.getName();
        Double longitude = placeDetailsModel.getCoordinatesModel().getLongitude();
        Double latitude = placeDetailsModel.getCoordinatesModel().getLatitude();
        Coordinates coordinates = new Coordinates(latitude, longitude);
        String imageUrl = placeDetailsModel.getImageUrl();
        List<Category> categories = categoryModelMapper.getCategories(placeDetailsModel.getCategories());
        String address = getAddress(placeDetailsModel.getLocationModel());
        Double rating = placeDetailsModel.getRating();
        Price price = priceModelMapper.getPrice(placeDetailsModel.getPrice());
        String phoneNumber = placeDetailsModel.getPhoneNumber();
        Integer reviewsCounter = placeDetailsModel.getReviewCount();
        List<Schedule> scheduleDays = getWorkingHours(placeDetailsModel.getWorkingHoursModelDays());
        Boolean isOpen = placeDetailsModel.getOpen();
        return new PlaceDetails(id, name, coordinates, imageUrl, categories, address, rating, price, phoneNumber, reviewsCounter, scheduleDays, isOpen);
    }

    @Override
    public SuggestedPlace getSuggestedPlace(SuggestedPlaceModel suggestedPlaceModel, LocationModel locationModel) {
        return new SuggestedPlace(suggestedPlaceModel.getId(), suggestedPlaceModel.getName(), getAddress(locationModel));
    }

    @Override
    public List<Place> getPlaces(List<PlaceModel> placesModel) {
        List<Place> places = new ArrayList<>();
        for(PlaceModel placeModel: placesModel)
            places.add(getPlace(placeModel));
        return places;
    }

    private String getAddress(LocationModel locationModel) {
        return locationModel.getAddress() +
                (locationModel.getSuburb().compareTo("") == 0 ? "" : ", " + locationModel.getSuburb()) +
                (locationModel.getZipCode().compareTo("") == 0 ? "" : ", " + locationModel.getZipCode()) +
                (locationModel.getCity().compareTo("") == 0 ? "" : " " + locationModel.getCity()) +
                (locationModel.getState().compareTo("") == 0 ? "" : ", " + locationModel.getState() + ".") +
                (locationModel.getCountry().compareTo("") == 0 ? "" : " " + locationModel.getCountry());
    }

    private List<Schedule> getWorkingHours(List<WorkingHoursModel> workingHoursModelList) {
        List<Schedule> workingHours = new ArrayList<>();
        for(WorkingHoursModel workingHoursModel : workingHoursModelList)
            workingHours.add(new Schedule(workingHoursModel.getDay(), getDay(workingHoursModel.getDay()), getHour(workingHoursModel.getHourOpen()), getHour(workingHoursModel.getHourClose())));
        return workingHours;
    }

    private String getDay(Integer day) {
        switch (day) {
            case 0: return context.getString(R.string.monday);
            case 1: return context.getString(R.string.tuesday);
            case 2: return context.getString(R.string.wednesday);
            case 3: return context.getString(R.string.thursday);
            case 4: return context.getString(R.string.friday);
            case 5: return context.getString(R.string.saturday);
            default: return context.getString(R.string.sunday);
        }
    }

    private Hour getHour(String hour) {
        int hourInt = Integer.parseInt(hour.substring(0,2));
        int minuteInt = Integer.parseInt(hour.substring(2));
        return new Hour(hourInt, minuteInt);
    }
}
