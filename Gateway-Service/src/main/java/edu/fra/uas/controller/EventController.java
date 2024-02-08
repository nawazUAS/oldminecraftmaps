package edu.fra.uas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.fra.uas.eventmodel.EventDTO;
import edu.fra.uas.service.Eventservice;

import java.net.URI;


import org.slf4j.Logger;

import org.springframework.web.bind.annotation.CrossOrigin; 
@CrossOrigin
@RestController
@RequestMapping("/api")
public class EventController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(EventController.class);

    @Autowired
    Eventservice eventservice;


@GetMapping(value = "/events", 
            produces = MediaType.APPLICATION_JSON_VALUE)

@ResponseBody
public ResponseEntity<?> list(){
    log.debug("list() is called");
    ResponseEntity<?> response = eventservice.getAll();
    if(response.getStatusCode().isSameCodeAs(HttpStatus.NO_CONTENT)){
        return new ResponseEntity<>("no semesters", HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    
}


@GetMapping(value = "/events/{eventId}", 
            produces = MediaType.APPLICATION_JSON_VALUE)

@ResponseBody
public ResponseEntity<?> find(@PathVariable("eventId") Long eventId){
    log.debug("find() is called");
    ResponseEntity<?> response = eventservice.getById(eventId);
    if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
        return response;
    }

    return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    
}


 @PostMapping(value = "/events", 
                 consumes = MediaType.APPLICATION_JSON_VALUE, 
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody EventDTO eventDTO) {
        log.info("Create new event: ", eventDTO.getTitle());
        ResponseEntity<?> response = eventservice.createEvent(eventDTO);
        if(response.getStatusCode().isSameCodeAs(HttpStatus.CREATED)){
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/event"));
            return new ResponseEntity<>(response.getBody(), headers, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(response.getBody(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

     @PutMapping(value = "/events/{eventId}", 
                 consumes = MediaType.APPLICATION_JSON_VALUE, 
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> updateEvent(@PathVariable("eventId") Long eventId,@RequestBody EventDTO eventDTO) {
        log.info("updateEvent() is called: ");
   

        ResponseEntity<?> response = eventservice.update(eventId, eventDTO);

        if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
          return response;
        }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
  

    }

        @DeleteMapping(value = "/events/{eventId}", 
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> deleteEvent(@PathVariable("eventId") Long eventId) {
        log.info("deleteEvent() is called");
        ResponseEntity<?> response = eventservice.deleteEvent(eventId);

        if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
          return response;
        }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    

}
}
