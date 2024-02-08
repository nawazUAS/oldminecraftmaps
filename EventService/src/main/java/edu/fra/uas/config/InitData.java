package edu.fra.uas.config;

import java.time.LocalDate;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.fra.uas.model.Event;
import edu.fra.uas.service.Eventservice;
import jakarta.annotation.PostConstruct;

@Component
public class InitData {
    private final Logger log = org.slf4j.LoggerFactory.getLogger(InitData.class);

    @Autowired
    Eventservice eventService;

     @PostConstruct
    public void init() {
        log.debug("### Initialize Data ###");

        log.debug("create first event");
        Event event1=
        eventService.createEvent("Frisuer");
        event1.setDate(LocalDate.of(2023, 12, 20));
        event1.setTime(LocalTime.of(12, 23, 0));
        eventService.checkDateAndTime(event1);

        log.debug("### Data initialized ###");
    }

}
