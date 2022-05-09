package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Hour;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Price;
import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.entities.WorkingHours;
import com.applications.asm.places.model.PlaceDetailsM;
import com.applications.asm.places.model.PriceM;
import com.applications.asm.places.model.ReviewM;
import com.applications.asm.places.model.ScheduleM;

import java.util.ArrayList;
import java.util.List;

public class PlaceDetailsMapperImpl implements PlaceDetailsMapper {
    @Override
    public PlaceDetailsM getPlaceDetailsMFromPlaceDetails(PlaceDetails placeDetails) {
        PriceM price = getPrice(placeDetails.getPrice());
        List<ScheduleM> schedule = getSchedule(placeDetails.getWorkingHoursDays());
        return new PlaceDetailsM(
                placeDetails.getId(),
                placeDetails.getName(),
                placeDetails.getImageUrl(),
                placeDetails.getLongitude(),
                placeDetails.getLatitude(),
                placeDetails.getRating(),
                price,
                placeDetails.getPhoneNumber(),
                placeDetails.getReviewsCounter(), 
                schedule,
                placeDetails.getOpen()
        );
    }

    private PriceM getPrice(Price price) {
        switch (price) {
            case CHEAP: return PriceM.CHEAP;
            case REGULAR: return PriceM.REGULAR;
            case EXPENSIVE: return PriceM.EXPENSIVE;
            case VERY_EXPENSIVE: return PriceM.VERY_EXPENSIVE;
            default: return PriceM.UNKNOWN;
        }
    }

    private List<ScheduleM> getSchedule(List<WorkingHours> workingHoursDays) {
        List<ScheduleM> schedule = new ArrayList<>();
        for(WorkingHours workingHours: workingHoursDays)
            schedule.add(mapSchedule(workingHours));
        return schedule;
    }

    private ScheduleM mapSchedule(WorkingHours workingHours) {
        String hours = getHours(workingHours.getOpenHour(), workingHours.getCloseHour());
        return new ScheduleM(workingHours.getDay(), hours);
    }

    private String getHours(Hour openHour, Hour closeHour) {
        return formatHour(openHour) + " - " + formatHour(closeHour);
    }

    private String formatHour(Hour hour) {
        String h = hour.getHour() < 10 ? ("0" + hour.getHour()) : hour.getHour() + "";
        String m = hour.getMinutes() < 10 ? ("0" + hour.getMinutes()) : hour.getMinutes() + "";
        return h + ":" + m;
    }
}
