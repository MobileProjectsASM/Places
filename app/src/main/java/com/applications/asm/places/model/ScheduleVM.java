package com.applications.asm.places.model;

import java.util.List;

public class ScheduleVM {
    private String day;
    private List<String> hours;

    public ScheduleVM(String day, List<String> hours) {
        this.day = day;
        this.hours = hours;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<String> getHours() {
        return hours;
    }

    public void setHours(List<String> hours) {
        this.hours = hours;
    }
}
