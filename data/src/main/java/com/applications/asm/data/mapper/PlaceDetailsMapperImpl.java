package com.applications.asm.data.mapper;

import android.content.Context;

import com.applications.asm.data.R;
import com.applications.asm.data.framework.network.graphql.PlaceDetailsQuery;
import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Hour;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Price;
import com.applications.asm.domain.entities.Schedule;

import java.util.ArrayList;
import java.util.List;

public class PlaceDetailsMapperImpl implements PlaceDetailsMapper {
    private final Context context;

    public PlaceDetailsMapperImpl(Context context) {
        this.context = context;
    }

    @Override
    public PlaceDetails placeDetailsQueryToPlaceDetails(PlaceDetailsQuery.Business business) {
        String id = business.id != null ? business.id : "";
        String name = business.name != null ? business.name : "";
        String imageUrl = !business.photos.isEmpty() ? business.photos.get(0) : "";
        String address = business.location != null ? getAddress(business.location) : "";
        Double rating = business.rating != null ? business.rating : 0;
        String phoneNumber = business.phone != null ? business.phone : "";
        Integer reviewsCounter = business.review_count != null ? business.review_count : 0;
        Boolean isOpen = business.hours != null && business.hours.size() > 0 && (business.hours.get(0).is_open_now != null ? business.hours.get(0).is_open_now : false);
        Coordinates coordinates = getCoordinates(business.coordinates);
        List<Category> categories = business.categories != null ? getCategories(business.categories) : new ArrayList<>();
        Price price = business.price != null ? getPrice(business.price) : new Price("unknown", context.getString(R.string.price_unknown));
        List<Schedule> schedule = business.hours != null && business.hours.size() > 0  && business.hours.get(0).open != null ? getSchedule(business.hours.get(0).open) : new ArrayList<>();
        return new PlaceDetails(id, name, coordinates, imageUrl, categories, address, rating, price, phoneNumber, reviewsCounter, schedule, isOpen);
    }

    private Coordinates getCoordinates(PlaceDetailsQuery.Coordinates coordinates) {
        Double latitude = coordinates != null && coordinates.latitude != null ? coordinates.latitude : 0;
        Double longitude = coordinates != null && coordinates.longitude != null ? coordinates.longitude : 0;
        return new Coordinates(latitude , longitude);
    }

    private List<Category> getCategories(List<PlaceDetailsQuery.Category> categories) {
        List<Category> categoryList = new ArrayList<>();
        for(PlaceDetailsQuery.Category category : categories)
            categoryList.add(new Category(category.alias, category.title));
        return categoryList;
    }

    private String getAddress(PlaceDetailsQuery.Location location) {
        return (location.address1 != null ? location.address1 : "") +
                (location.address2 != null ? ", " + location.address2 : "") +
                (location.address3 != null ? ", " + location.address3 : "") +
                (location.postal_code != null ? ", " + location.postal_code : "") +
                (location.city != null ? " " + location.city : "") +
                (location.state != null? ", " + location.state + "." : "") +
                (location.country != null ? " " + location.country : "");
    }

    private Price getPrice(String price) {
        if(price.compareTo("$") == 0)
            return new Price("$", context.getString(R.string.price_cheap));
        else if(price.compareTo("$$") == 0)
            return new Price("$$", context.getString(R.string.price_regular));
        else if(price.compareTo("$$$") == 0)
            return new Price("$$$", context.getString(R.string.price_expensive));
        else if(price.compareTo("$$$$") == 0)
            return new Price("$$$$", context.getString(R.string.price_very_expensive));
        else return new Price("unknown", context.getString(R.string.price_unknown));
    }

    private List<Schedule> getSchedule(List<PlaceDetailsQuery.Open> listOpen) {
        List<Schedule> schedule = new ArrayList<>();
        for(PlaceDetailsQuery.Open open : listOpen)
            schedule.add(getScheduleDay(open));
        return schedule;
    }

    private Schedule getScheduleDay(PlaceDetailsQuery.Open open) {
        Integer dayNumber = open.day != null ? open.day : 0;
        String day = getDay(dayNumber);
        Hour openHour = open.start != null ? getHour(open.start) : new Hour(0, 0);
        Hour closeHour = open.end != null ? getHour(open.end) : new Hour(0, 0);
        return new Schedule(dayNumber, day, openHour, closeHour);
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
