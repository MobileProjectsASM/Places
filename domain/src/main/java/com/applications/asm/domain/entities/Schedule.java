package com.applications.asm.domain.entities;

import java.util.List;

public class Schedule {
    private Integer dayNumber;
    private String day;
    List<Hours> hours;

    public Schedule(Integer dayNumber, String day, List<Hours> hours) {
        this.dayNumber = dayNumber;
        this.day = day;
        this.hours = hours;
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

    public List<Hours> getHours() {
        return hours;
    }

    public void setHours(List<Hours> hours) {
        this.hours = hours;
    }
}
