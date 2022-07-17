package com.applications.asm.domain.entities;

public class Schedule {
    private Integer dayNumber;
    private String day;
    private Hour openHour;
    private Hour closeHour;

    public Schedule(Integer dayNumber, String day, Hour openHour, Hour closeHour) {
        this.dayNumber = dayNumber;
        this.day = day;
        this.openHour = openHour;
        this.closeHour = closeHour;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Hour getOpenHour() {
        return openHour;
    }

    public void setOpenHour(Hour openHour) {
        this.openHour = openHour;
    }

    public Hour getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(Hour closeHour) {
        this.closeHour = closeHour;
    }
}
