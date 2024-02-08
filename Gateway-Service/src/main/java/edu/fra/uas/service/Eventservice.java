package edu.fra.uas.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import edu.fra.uas.eventmodel.EventDTO;
import edu.fra.uas.model.ApiError;

import org.slf4j.Logger;

@Service
public class Eventservice {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Eventservice.class);

    //Read the URL of the external API (EventService) from the application.properties file 
    @Value("${eventservice.url}")
    String apiUrl;


     // get all events       GET /events
    public ResponseEntity<?> getAll(){
        log.debug("\"forward request to \""+ apiUrl + "\"/events\"");
        //create restTemplate
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events";
        HttpHeaders headers = new HttpHeaders();
        //create Headers to put it on a request
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;
        try {
            //from the request with the headers we should get a response from the service
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            //if not, there will be an error
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
        }

    // get event by id       GET /events/{id} 
    public ResponseEntity<?> getById(Long id){
        log.debug("\"forward request to \""+ apiUrl + "\"events/\""+ id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + id;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);
      
        ResponseEntity<?> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;

    }


    // create a new event       POST /events
    public ResponseEntity<?> createEvent(EventDTO eventDTO){
        log.debug("\"forward request to \""+ apiUrl + "\"/events\"");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<EventDTO> request = new HttpEntity<EventDTO>(eventDTO,headers);

      
        ResponseEntity<?> response;
                try {
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());}

  
        return response;

    }

      //delete a event         DELETE /events/{eventId}
      public ResponseEntity<?> deleteEvent(Long id){
        log.debug("\"forward request to \""+ apiUrl + "\"/events/{id}\""+ id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/"+ id;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

            ResponseEntity<?> response;
            try {
            response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
            } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());}

  
        return response;

    
            }

             //update event      PUT /events/{id}
        public ResponseEntity<?> update(Long id, EventDTO eventDTO){
        log.debug("\"forward request to \""+ apiUrl + "\"/events/\""+ id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/"+ id ;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EventDTO> request = new HttpEntity<EventDTO>(eventDTO,headers);

            ResponseEntity<?> response;
            try {
            response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
            } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());}

  
        return response;

    
            }

}
