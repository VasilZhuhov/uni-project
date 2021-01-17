package com.timemanagement;

import com.timemanagement.models.*;
import org.joda.time.DateTimeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TimeManagementController {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserEventRepository userEventRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    // working
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // working
    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        User u = userRepository.findUserByCredentials(user.getEmail(), user.getPassword());
        if (u != null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Should we return only events for which isAccepted == true? - For now we return all(true and false)
    // working when sending value for isAccepted
    @GetMapping("{userId}/events/{timestamp}")
    public ResponseEntity<List<Event>> getEvents(@PathVariable long userId, @PathVariable long timestamp,
                                                 @RequestParam("isAccepted") Boolean isAccepted) {
        Date date = new Date(timestamp);
        List<UserEvent> userEvents = userEventRepository.findUserEventsByUserId(userId).stream()
                .filter(ue -> ue.isAccepted() == isAccepted)
                .collect(Collectors.toList());
        DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();

        List<Event> currentDateEvents = new ArrayList<>();
        for (UserEvent userEvent : userEvents) {
            if (dateTimeComparator.compare(userEvent.getEvent().getStartTime(), date) == 0) {
                currentDateEvents.add(userEvent.getEvent());
            }
        }
        return new ResponseEntity<>(currentDateEvents, HttpStatus.OK);
    }

    // working
    @PostMapping("{userId}/events")
    public ResponseEntity<Void> createEvent(@PathVariable long userId, @RequestBody Event event) {
        Event newEvent = new Event(event);
        newEvent = eventRepository.save(newEvent);
        for (UserEvent ue : event.getUsersEvent()) {
            ue.setEvent(newEvent);
            ue.setAccepted(ue.getUser().getId() == userId);
            userEventRepository.save(ue);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // working
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable long eventId) {
        userEventRepository.deleteUserEventByEventId(eventId);
        eventRepository.deleteById(eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // If userEvent is updated how to update it in user_event table?
    @PutMapping("/events/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable long eventId, @RequestBody Event newEvent) {
        Event updatedEvent = eventRepository.findById(eventId)
                .map(event -> {
                    event.setTitle(newEvent.getTitle());
                    event.setStartTime(newEvent.getStartTime());
                    event.setEndTime(newEvent.getEndTime());
                    event.setDescription(newEvent.getDescription());
                    event.setLocation(newEvent.getLocation());
//                    event.setUsersEvent(newEvent.getUsersEvent());
                    return eventRepository.save(event);
                }).orElse(null);

        if (updatedEvent != null) {
            return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // working
    @PutMapping("{userId}/events/{eventId}")
    public ResponseEntity<Event> acceptEvent(@PathVariable long userId, @PathVariable long eventId) {
        List<UserEvent> userEventsByUserId = userEventRepository.findUserEventsByUserId(userId);
        Optional<UserEvent> userEventByEventId = userEventsByUserId.stream()
                .filter(userEvent -> userEvent.getEvent().getId() == eventId)
                .findFirst();

        if (userEventByEventId.isPresent()) {
            UserEvent userEvent = userEventByEventId.get();
            userEvent.setAccepted(true);
            userEventRepository.save(userEvent);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // working
    @DeleteMapping("{userId}/events/{eventId}")
    public ResponseEntity<Void> ignoreEvent(@PathVariable long userId, @PathVariable long eventId) {
        List<UserEvent> userEventsByUserId = userEventRepository.findUserEventsByUserId(userId);
        Optional<UserEvent> userEventByEventId = userEventsByUserId.stream()
                .filter(userEvent -> userEvent.getEvent().getId() == eventId)
                .findFirst();

        if (userEventByEventId.isPresent()) {
            userEventRepository.delete(userEventByEventId.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // working
    @PostMapping("/authenticate")
    public ResponseEntity<User> authenticate(@RequestBody User user) {
        return new ResponseEntity<>(userRepository.findUserByCredentials(user.getEmail(), user.getPassword()), HttpStatus.OK);
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable long eventId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event != null) {
            return new ResponseEntity<>(event, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    private static long getDateDiff(Date date1, Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
