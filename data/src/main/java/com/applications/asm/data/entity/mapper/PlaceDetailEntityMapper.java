package com.applications.asm.data.entity.mapper;

import android.content.Context;

import com.applications.asm.data.R;
import com.applications.asm.data.entity.PlaceDetailsEntity;
import com.applications.asm.data.entity.WorkingHoursEntity;
import com.applications.asm.domain.entities.Hour;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Price;
import com.applications.asm.domain.entities.WorkingHours;

import java.util.ArrayList;
import java.util.List;

public class PlaceDetailEntityMapper {
    private final Context context;

    public PlaceDetailEntityMapper(Context context) {
        this.context = context;
    }

    PlaceDetails getPlaceDetailsFromPlaceDetailsEntity(PlaceDetailsEntity placeDetailsEntity) {
        String id = placeDetailsEntity.getId();
        String name = placeDetailsEntity.getName();
        Double rating = placeDetailsEntity.getRating();
        Price price = getPrice(placeDetailsEntity.getPrice());
        String phoneNumber = placeDetailsEntity.getPhoneNumber();
        Integer reviewsCounter = placeDetailsEntity.getReviewCount();
        List<WorkingHours> workingHoursDays = getWorkingHours(placeDetailsEntity.getWorkingHoursEntityDays());
        Boolean isOpen = placeDetailsEntity.getOpen();
        return new PlaceDetails(id, name, rating, price, phoneNumber, reviewsCounter, workingHoursDays, isOpen);
    }

    private Price getPrice(String price) {
        if(price.compareTo("$$$$") == 0)
            return Price.VERY_EXPENSIVE;
        else if(price.compareTo("$$$") == 0)
            return Price.EXPENSIVE;
        else if(price.compareTo("$$") == 0)
            return Price.REGULAR;
        else return Price.CHEAP;
    }

    private List<WorkingHours> getWorkingHours(List<WorkingHoursEntity> workingHoursEntityList) {
        List<WorkingHours> workingHours = new ArrayList<>();
        for(WorkingHoursEntity workingHoursEntity: workingHoursEntityList)
            workingHours.add(new WorkingHours(workingHoursEntity.getDay(), getDay(workingHoursEntity.getDay()), getHour(workingHoursEntity.getHourOpen()), getHour(workingHoursEntity.getHourClose())));
        return workingHours;
    }

    private String getDay(Integer day) {
        switch (day) {
            case 0: return context.getString(R.string.monday);
            case 1: return context.getString(R.string.tuesday);
            case 2: return context.getString(R.string.wednesday);
            case 3: return context.getString(R.string.thursday);
            case 4: return context.getString(R.string.thursday);
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
