package com.timemanagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.timemanagement.models.Event;
import com.timemanagement.models.EventRepository;
import com.timemanagement.models.User;
import com.timemanagement.models.UserEvent;
import com.timemanagement.models.UserEventRepository;
import com.timemanagement.models.UserRepository;


@RestController
public class TimeManagementController {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserEventRepository userEventRepository;

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getEvents() {
        return new ResponseEntity<>(eventRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        User u = userRepository.findUserByCredentials(user.getEmail(), user.getPassword());
        if (u != null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/events")
    public ResponseEntity<Void> createEvent(@RequestBody Event event) {
        Event newEvent = new Event(event);
        newEvent = eventRepository.save(newEvent);
        for (UserEvent ue : event.getUsersEvent()) {
            ue.setEvent(newEvent);
            ue.setAccepted(true);
            userEventRepository.save(ue);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<User> authenticate(@RequestBody User user) {
        return new ResponseEntity<>(userRepository.findUserByCredentials(user.getEmail(), user.getPassword()), HttpStatus.OK);
    }

}
