package edu.fra.uas.model;

import java.time.Duration;
import java.time.Period;
import java.time.LocalDate;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Event implements java.io.Serializable {
     private static final Logger log = LoggerFactory.getLogger(Event.class);

    private long id;
    private String title;
    private LocalDate date;
    private LocalTime time;
    private TemporalState state;
    private Period timeLeft;
    private Duration hoursLeft;

    public Event(){
        log.debug("Event created without values");
    }

    public Event(long id, String title, LocalDate date, LocalTime time, TemporalState state, Period timeLeft, Duration hoursLeft) {
        this.date=date;
        this.id=id;
        this.time=time;
        this.title=title;
        this.state=state;
        this.timeLeft=timeLeft;
        this.hoursLeft=hoursLeft;
        log.debug("Event created: "+ getId());
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }
    public LocalTime getTime() {
        return time;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public TemporalState getState() {
        return state;
    }
    public void setState(TemporalState state) {
        this.state = state;
    }
    public void setTimeLeft(Period timeLeft) {
        this.timeLeft = timeLeft;
    }
    public Period getTimeLeft() {
        return timeLeft;
    }
    public void setHoursLeft(Duration hoursLeft) {
        this.hoursLeft = hoursLeft;
    }
    public Duration getHoursLeft() {
        return hoursLeft;
    }
}
