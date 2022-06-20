package com.applications.asm.data.model.mapper;

import android.content.Context;

import com.applications.asm.data.R;
import com.applications.asm.data.model.LocationModel;
import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.PlaceModel;
import com.applications.asm.data.model.WorkingHoursModel;
import com.applications.asm.domain.entities.Hour;
import com.applications.asm.domain.entities.Location;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Price;
import com.applications.asm.domain.entities.WorkingHours;

import java.util.ArrayList;
import java.util.List;

public class PlaceModelMapperImpl implements PlaceModelMapper {
    private final Context context;

    public PlaceModelMapperImpl(Context context) {
        this.context = context;
    }

    @Override
    public Place getPlaceFromPlaceModel(PlaceModel placeModel) {
        String id = placeModel.getId();
        String name = placeModel.getName();
        String imageUrl = placeModel.getImageUrl();
        Double latitude = placeModel.getCoordinatesModel().getLatitude();
        Double longitude = placeModel.getCoordinatesModel().getLongitude();
        Location location = new Location(latitude, longitude);
        List<String> categories = placeModel.getCategories();
        String address = getAddressFromLocation(placeModel.getLocationModel());
        return new Place(id, name, location, imageUrl, categories, address);
    }

    @Override
    public PlaceDetails getPlaceDetailsFromPlaceDetailsModel(PlaceDetailsModel placeDetailsModel) {
        String id = placeDetailsModel.getId();
        String name = placeDetailsModel.getName();
        Double longitude = placeDetailsModel.getCoordinatesModel().getLongitude();
        Double latitude = placeDetailsModel.getCoordinatesModel().getLatitude();
        Location location = new Location(latitude, longitude);
        String imageUrl = placeDetailsModel.getImageUrl();
        List<String> categories = placeDetailsModel.getCategories();
        String address = getAddressFromLocation(placeDetailsModel.getLocationModel());
        Double rating = placeDetailsModel.getRating();
        Price price = getPrice(placeDetailsModel.getPrice());
        String phoneNumber = placeDetailsModel.getPhoneNumber();
        Integer reviewsCounter = placeDetailsModel.getReviewCount();
        List<WorkingHours> workingHoursDays = getWorkingHours(placeDetailsModel.getWorkingHoursModelDays());
        Boolean isOpen = placeDetailsModel.getOpen();
        return new PlaceDetails(id, name, location, imageUrl, categories, address, rating, price, phoneNumber, reviewsCounter, workingHoursDays, isOpen);
    }

    private String getAddressFromLocation(LocationModel locationModel) {
        return locationModel.getAddress() +
                (locationModel.getSuburb().compareTo("") == 0 ? "" : ", " + locationModel.getSuburb()) +
                (locationModel.getZipCode().compareTo("") == 0 ? "" : ", " + locationModel.getZipCode()) +
                (locationModel.getCity().compareTo("") == 0 ? "" : " " + locationModel.getCity()) +
                (locationModel.getState().compareTo("") == 0 ? "" : ", " + locationModel.getState() + ".") +
                (locationModel.getCountry().compareTo("") == 0 ? "" : " " + locationModel.getCountry());
    }

    private Price getPrice(String price) {
        if(price.compareTo("$$$$") == 0)
            return Price.VERY_EXPENSIVE;
        else if(price.compareTo("$$$") == 0)
            return Price.EXPENSIVE;
        else if(price.compareTo("$$") == 0)
            return Price.REGULAR;
        else if(price.compareTo("$") == 0)
            return Price.CHEAP;
        else return Price.UNKNOWN;
    }

    private List<WorkingHours> getWorkingHours(List<WorkingHoursModel> workingHoursModelList) {
        List<WorkingHours> workingHours = new ArrayList<>();
        for(WorkingHoursModel workingHoursModel : workingHoursModelList)
            workingHours.add(new WorkingHours(workingHoursModel.getDay(), getDay(workingHoursModel.getDay()), getHour(workingHoursModel.getHourOpen()), getHour(workingHoursModel.getHourClose())));
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
