package com.applications.asm.data.mapper;

import com.applications.asm.data.PlaceDetailsQuery;
import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Hour;
import com.applications.asm.domain.entities.Hours;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Price;
import com.applications.asm.domain.entities.Schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class PlaceDetailsMapperImpl implements PlaceDetailsMapper {

    @Inject
    public PlaceDetailsMapperImpl() {

    }

    @Override
    public PlaceDetails placeDetailsQueryToPlaceDetails(PlaceDetailsQuery.Business business, Map<String, String> days, Map<String, String> prices) {
        String id = business.id() != null ? business.id() : "";

        String name = business.name() != null ? business.name() : "";

        String imageUrl = "";
        List<String> photos = business.photos();
        if(photos != null && !photos.isEmpty()) imageUrl = photos.get(0);

        String address = "";
        PlaceDetailsQuery.Location location = business.location();
        if(location != null) address = getAddress(location);

        Double rating = (double) 0;
        Double rating2 = business.rating();
        if(rating2 != null) rating = rating2;

        String phoneNumber = business.phone() != null ? business.phone() : "";

        Integer reviewsCounter = 0;
        Integer reviewsCount = business.review_count();
        if(reviewsCount != null) reviewsCounter = reviewsCount;

        Boolean isOpen = false;
        List<PlaceDetailsQuery.Hour> hours = business.hours();
        if(hours != null && hours.size() > 0) {
            PlaceDetailsQuery.Hour hour = hours.get(0);
            if(hour != null) isOpen = hour.is_open_now();
        }

        Coordinates coordinates = new Coordinates((double) 0, (double) 0);
        PlaceDetailsQuery.Coordinates coordinates2 = business.coordinates();
        if(coordinates2 != null) coordinates = getCoordinates(coordinates2);

        List<Category> categories = new ArrayList<>();
        List<PlaceDetailsQuery.Category> categories2 = business.categories();
        if(categories2 != null) categories = getCategories(categories2);

        Price price = business.price() != null ? getPrice(business.price(), prices) : null;

        List<Schedule> schedule = new ArrayList<>();
        List<PlaceDetailsQuery.Hour> hours2 = business.hours();
        if(hours2 != null && hours2.size() > 0) {
            PlaceDetailsQuery.Hour hour = hours2.get(0);
            if(hour != null)  {
                List<PlaceDetailsQuery.Open> opens = hour.open();
                if(opens != null) schedule = getSchedule(opens, days);
            }
        }

        return new PlaceDetails(id, name, coordinates, imageUrl, categories, address, rating, price, phoneNumber, reviewsCounter, schedule, isOpen);
    }

    private Coordinates getCoordinates(PlaceDetailsQuery.Coordinates coordinates) {
        Double lat = (double) 0;
        Double latitude = coordinates.latitude();
        if(latitude != null) lat = latitude;

        Double lon = (double) 0;
        Double longitude = coordinates.longitude();
        if(longitude != null) lon = longitude;

        return new Coordinates(lat , lon);
    }

    private List<Category> getCategories(List<PlaceDetailsQuery.Category> categories) {
        List<Category> categoryList = new ArrayList<>();
        for(PlaceDetailsQuery.Category category : categories)
            categoryList.add(new Category(category.alias(), category.title()));
        return categoryList;
    }

    private String getAddress(PlaceDetailsQuery.Location location) {
        String add1 = location.address1() != null ? location.address1() : "";

        String add2 = "";
        String address2 = location.address2();
        if(address2 != null) if(!address2.isEmpty()) add2 = ", " + address2;

        String add3 = "";
        String address3 = location.address3();
        if(address3 != null) if(!address3.isEmpty()) add3 = ", " + address3;

        String pc = "";
        String postalCode = location.postal_code();
        if(postalCode != null) if(!postalCode.isEmpty()) pc = ", " + postalCode;

        String city = "";
        String city2 = location.city();
        if(city2 != null) if(!city2.isEmpty()) city = ", " + city2;

        String state = "";
        String state2 = location.state();
        if(state2 != null) if(!state2.isEmpty()) state = ", " + state2 + ".";

        String country = "";
        String country2 = location.country();
        if(country2 != null) if(!country2.isEmpty()) country = " " + location.country();

        return add1 + add2 + add3 + pc + city + state + country;
    }

    private Price getPrice(String priceId, Map<String, String> prices) {
        String priceName = prices.get(priceId);
        if(priceName != null)
            return new Price(priceId, priceName);
        return null;
    }

    private List<Schedule> getSchedule(List<PlaceDetailsQuery.Open> listOpen, Map<String, String> days) {
        Map<Integer, Schedule> scheduleMap = new HashMap<>();
        for(PlaceDetailsQuery.Open open : listOpen){
            int dayNum = 0;
            Integer dayNumber = open.day();
            if(dayNumber != null) dayNum = dayNumber;

            String day = getDay(dayNumber, days);

            Hour openH = new Hour(0, 0);
            String openHour = open.start();
            if(openHour != null && !openHour.isEmpty())
                openH = getHour(openHour);

            Hour closeH = new Hour(0,0);
            String closeHour = open.end();
            if(closeHour != null && !closeHour.isEmpty())
                closeH = getHour(closeHour);

            Hours hours = new Hours(openH, closeH);

            Schedule schedule = scheduleMap.get(dayNum);
            if(schedule == null) scheduleMap.put(dayNum, new Schedule(dayNum, day, new ArrayList<Hours>() {{add(hours);}}));
            else schedule.getHours().add(hours);
        }
        List<Schedule> schedule = new ArrayList<>();
        for(Integer key : scheduleMap.keySet()) schedule.add(scheduleMap.get(key));
        return schedule;
    }

    private String getDay(Integer day, Map<String, String> days) {
        return days.get(String.valueOf(day));
    }

    private Hour getHour(String hour) {
        int hourInt = Integer.parseInt(hour.substring(0,2));
        int minuteInt = Integer.parseInt(hour.substring(2));
        return new Hour(hourInt, minuteInt);
    }
}
