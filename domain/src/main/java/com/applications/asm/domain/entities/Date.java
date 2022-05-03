package com.applications.asm.domain.entities;

public class Date {
    private Integer day;
    private Integer month;
    private Integer age;
    private Hour hour;

    public Date(Integer day, Integer month, Integer age, Hour hour) {
        this.day = day;
        this.month = month;
        this.age = age;
        this.hour = hour;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Hour getHour() {
        return hour;
    }

    public void setHour(Hour hour) {
        this.hour = hour;
    }
}
