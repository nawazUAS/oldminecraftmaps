package edu.fra.uas.controller;

import org.springframework.web.bind.annotation.RestController;

import edu.fra.uas.model.Event;
import edu.fra.uas.model.EventDTO;
import edu.fra.uas.service.Eventservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
public class EventController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(EventController.class);

    @Autowired
    Eventservice eventservice;

    
      // Now the service-methods will be called in the controller
 

      //using GET-METHOD to get all events
    @GetMapping(value = "/events", 
                produces = MediaType.APPLICATION_JSON_VALUE)

    @ResponseBody
        public ResponseEntity<List<Event>> list(){
        log.info("list() is called");
         Iterable<Event> eventIter = eventservice.getAll();
         List<Event> event = new ArrayList<>();
             for (Event oneEvent : eventIter){
         event.add(oneEvent);
     }

         if(event.isEmpty()){
          return ResponseEntity.noContent().build();
     }

    return new ResponseEntity<List<Event>>(event, HttpStatus.OK);
    
}

      //using GET-METHOD to get a partivular event
    @GetMapping(value = "/events/{id}", 
            produces = MediaType.APPLICATION_JSON_VALUE)

    @ResponseBody
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        log.info("Get event by id" + id);
        Event event = eventservice.getById(id);
        if(event == null){
            return new ResponseEntity<>("No event was found" + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Event>(event, HttpStatus.OK);
    }

          //using POST-Method to creat a new event
    @PostMapping(value = "/events",
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody EventDTO eventDTO){
        log.info("Create new event:" + eventDTO.getTitle());

        String detail= null;
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail);
        pd.setInstance(URI.create("/events"));
        pd.setTitle("Event creation error");

        if(eventDTO.getTitle() == null || eventDTO.getTitle().isEmpty()){
            detail="Event cannot be empty or null";
            return ResponseEntity.unprocessableEntity().body(pd);}
        else if(eventDTO.getDay() == 0 || eventDTO.getMonth() == 0 || eventDTO.getYear() == 0){
            detail="Date cannot be empty or null";
            return ResponseEntity.unprocessableEntity().body(pd);
        }

        Event event = eventservice.createEvent(eventDTO.getTitle());
        event.setDate(LocalDate.of(eventDTO.getYear(), eventDTO.getMonth(), eventDTO.getDay()));
        event.setTime(LocalTime.of(eventDTO.getHours(), eventDTO.getMinutes(), eventDTO.getSeconds()));
        eventservice.checkDateAndTime(event);
        return new ResponseEntity<Event>(event, HttpStatus.CREATED);
    } 

    //Using PUT-Method to update an existing event

    @PutMapping(value = "/events/{id}",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody EventDTO eventDTO, @PathVariable("id") Long id){
        log.debug("update() is called");
        Event event = eventservice.getById(id);
        if(event == null){
            return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
        }
        String
         detail= null;
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail);
        pd.setInstance(URI.create("/events/" + id));
        pd.setTitle("Event update error");

         if(eventDTO.getTitle() == null || eventDTO.getTitle().isEmpty()){
            detail="Event cannot be empty or null";
            return ResponseEntity.unprocessableEntity().body(pd);}
        else if(eventDTO.getDay() == 0 || eventDTO.getMonth() == 0 || eventDTO.getYear() == 0){
            detail="Date cannot be empty or null";
            return ResponseEntity.unprocessableEntity().body(pd);
        }

        event.setTitle(eventDTO.getTitle());
        event.setDate(LocalDate.of(eventDTO.getYear(), eventDTO.getMonth(), eventDTO.getDay()));
        event.setTime(LocalTime.of(eventDTO.getHours(), eventDTO.getMinutes(), eventDTO.getSeconds()));
        event = eventservice.update(event);
        eventservice.checkDateAndTime(event);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/events/" + id));
        return new ResponseEntity<Event>(event, HttpStatus.OK);

    }
   

    // using DELETE-Method to delete an event
    @DeleteMapping(value = "/events/{id}",
                    produces = MediaType.APPLICATION_JSON_VALUE)

    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        log.debug("delete() is called");
        Event event = eventservice.delete(id);
        if(event == null){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<Event>(event, HttpStatus.OK);
    }
    
}


