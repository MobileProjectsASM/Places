package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.FoundPlaces;
import com.applications.asm.domain.entities.Hour;
import com.applications.asm.domain.entities.Hours;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Price;
import com.applications.asm.domain.entities.SuggestedPlace;
import com.applications.asm.domain.entities.Schedule;
import com.applications.asm.places.model.CategoryVM;
import com.applications.asm.places.model.FoundPlacesVM;
import com.applications.asm.places.model.PlaceDetailsVM;
import com.applications.asm.places.model.PlaceVM;
import com.applications.asm.places.model.PriceVM;
import com.applications.asm.places.model.ScheduleVM;
import com.applications.asm.places.model.SuggestedPlaceVM;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PlaceMapperImpl implements PlaceMapper {

    @Inject
    public PlaceMapperImpl() {

    }

    @Override
    public SuggestedPlaceVM getSuggestedPlaceVM(SuggestedPlace suggestedPlace) {
        return new SuggestedPlaceVM(suggestedPlace.getId(), suggestedPlace.getName(), suggestedPlace.getAddress());
    }

    @Override
    public PlaceVM getPlaceVM(Place place) {
        String categories = getCategories(place.getCategories());
        return new PlaceVM(place.getId(), place.getName(), place.getLocation().getLatitude(), place.getLocation().getLongitude(), place.getImageUrl(), categories, place.getAddress());
    }

    @Override
    public PlaceDetailsVM getPlaceDetailsVM(PlaceDetails placeDetails) {
        return new PlaceDetailsVM(
            placeDetails.getId(),
            placeDetails.getName(),
            placeDetails.getImageUrl(),
            placeDetails.getAddress(),
            placeDetails.getLocation().getLongitude(),
            placeDetails.getLocation().getLatitude(),
            placeDetails.getRating(),
            getPrice(placeDetails.getPrice()),
            getCategories(placeDetails.getCategories()),
            placeDetails.getPhoneNumber(),
            placeDetails.getReviewsCounter(),
            getSchedule(placeDetails.getWorkingHoursDays()),
            placeDetails.getOpen()
        );
    }

    @Override
    public List<PlaceVM> getPlacesVM(List<Place> places) {
        List<PlaceVM> placesVM = new ArrayList<>();
        for(Place place: places)
            placesVM.add(getPlaceVM(place));
        return placesVM;
    }

    @Override
    public List<SuggestedPlaceVM> getSuggestedPlaces(List<SuggestedPlace> suggestedPlaces) {
        List<SuggestedPlaceVM> suggestedPlacesVM = new ArrayList<>();
        for(SuggestedPlace suggestedPlace: suggestedPlaces)
            suggestedPlacesVM.add(getSuggestedPlaceVM(suggestedPlace));
        return suggestedPlacesVM;
    }

    @Override
    public FoundPlacesVM getFoundPlacesVM(FoundPlaces foundPlaces) {
        return new FoundPlacesVM(getPlacesVM(foundPlaces.getPlaces()), foundPlaces.getTotalPages());
    }

    private String getCategories(List<Category> categories) {
        StringBuilder categoriesVM = new StringBuilder();
        for(int i = 0; i < categories.size(); i++) {
            if(i == 0) categoriesVM.append(categories.get(i).getName());
            else categoriesVM.append(", ").append(categories.get(i).getName());
        }
        return categoriesVM.toString();
    }

    private PriceVM getPrice(Price price) {
        if(price != null)
            return new PriceVM(price.getId(), price.getName());
        return null;
    }

    private List<ScheduleVM> getSchedule(List<Schedule> scheduleDays) {
        List<ScheduleVM> schedule = new ArrayList<>();
        for(Schedule workingHours: scheduleDays)
            schedule.add(mapSchedule(workingHours));
        return schedule;
    }

    private ScheduleVM mapSchedule(Schedule schedule) {
        List<String> strings = new ArrayList<>();
        for(Hours hours : schedule.getHours()) strings.add(getHours(hours.getOpenHour(), hours.getCloseHour()));
        return new ScheduleVM(schedule.getDay(), strings);
    }

    private String getHours(Hour openHour, Hour closeHour) {
        return formatHour12(openHour) + " - " + formatHour12(closeHour);
    }

    private String formatHour12(Hour hour) {
        String suffix = "";
        int auxHour;
        if(hour.getHour() <= 12) {
            suffix = hour.getHour() < 12 ? "AM" : "PM";
            auxHour = hour.getHour();
        } else {
            suffix = "PM";
            auxHour = hour.getHour() % 12;
        }
        String h = auxHour < 10 ? ("0" + auxHour) : auxHour + "";
        String m = hour.getMinutes() < 10 ? ("0" + hour.getMinutes()) : hour.getMinutes() + "";
        return h + ":" + m + " " + suffix;
    }
}
