package edu.fra.uas.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.Duration;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fra.uas.model.Event;
import edu.fra.uas.model.TemporalState;
import edu.fra.uas.repository.EventRepository;

@Service
public class Eventservice {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Eventservice.class);

    private long eventId=1;

    @Autowired
    EventRepository eventRepository;

    /*
    here the eventReposity is use with the crud-operations
    to get all events: the values of the repository will be returned
    to create or update a new event: the event will be saved in the repository
    to delete an event: the event will be removed
    */    

    public Iterable<Event> getAll() {
        log.debug("getAll");
        return eventRepository.values();
    }

    public Event getById(Long id){
        log.info("get semester by id: {}", id);
        return eventRepository.get(id);
    }

    public Event createEvent(String eventTitle) {
        log.info("createEvent: ",eventTitle);
        Event event = new Event();
        event.setId(eventId++);
        event.setTitle(eventTitle);
        eventRepository.put(event.getId(), event);
        return event;
    }


    public Event update(Event event) {
        log.info("update event: {}", event);
        eventRepository.put(event.getId(), event);
        return event;
    }

    public Event delete(Long id){
        log.info("delete event: {}", id);
        return eventRepository.remove(id);
    }


    /*
     * this metod is used to check date and time
     * after checking, the event will get a status
     * important: 'hoursleft' should be only displayed of on same date
     */

    public Event checkDateAndTime (Event event){
        LocalDate date = event.getDate();
        LocalTime time = event.getTime();
        Duration hoursLeft = Duration.between(time, LocalTime.now());
        Period duration = Period.between(date,LocalDate.now());
        //event.setHoursLeft(hoursLeft); hoursleft should be displayed only if the date is the same!
        event.setTimeLeft(duration);
  
        if(date.isBefore(LocalDate.now())){
            event.setState(TemporalState.MISSED);
            return event;

        }else if(date.isAfter(LocalDate.now())){
            event.setState(TemporalState.PENDING);
            return event;
        }
        else if(date.isEqual(LocalDate.now())){
            event.setState(TemporalState.NOW);
            if(time.isBefore(LocalTime.now())){
                event.setHoursLeft(hoursLeft);
                event.setState(TemporalState.MISSED);
                return event;
            } else if(time.isAfter(LocalTime.now())){
                event.setHoursLeft(hoursLeft);
                return event;
            }
        }


        return event;
    }

}
