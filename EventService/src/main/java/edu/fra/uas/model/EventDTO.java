package edu.fra.uas.model;

public class EventDTO {
    private String title;
    private int day;
    private int month;
    private int year;

    private int hours;
    private int minutes;
    private int seconds;

    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }
    public int getHours() {
        return hours;
    }
    public void setHours(int hours) {
        this.hours = hours;
    }
    public int getMinutes() {
        return minutes;
    }
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public int getSeconds() {
        return seconds;
    }
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setTitle(String title) {
        this.title = title;
    }public String getTitle() {
        return title;
    }

}
