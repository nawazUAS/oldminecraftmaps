package edu.fra.uas.repository;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import edu.fra.uas.model.Event;

@Repository
public class EventRepository extends HashMap<Long, Event> {

}
