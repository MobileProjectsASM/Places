package com.applications.asm.data.model;

public class WorkingHoursModel {
    private Integer day;
    private String hourOpen;
    private String hourClose;

    public WorkingHoursModel() {}

    public WorkingHoursModel(Integer day, String hourOpen, String hourClose) {
        this.day = day;
        this.hourOpen = hourOpen;
        this.hourClose = hourClose;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getHourOpen() {
        return hourOpen;
    }

    public void setHourOpen(String hourOpen) {
        this.hourOpen = hourOpen;
    }

    public String getHourClose() {
        return hourClose;
    }

    public void setHourClose(String hourClose) {
        this.hourClose = hourClose;
    }
}
