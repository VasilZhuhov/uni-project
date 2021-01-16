package com.timemanagement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("{userId}/{date}/schedule")
    public ResponseEntity<Map<String, Event>> schedule(@RequestParam long userId, @RequestParam Date date,
                                         @RequestBody ScheduleParams params) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(params.getStartTime());

        Map<String, Event> schedule = new HashMap<>();

        User currentUser = userRepository.getOne(userId);

        Set<UserEvent> userEventSet = currentUser.getUserEvents();
        List<UserEvent> userEvents = new ArrayList<>(userEventSet);
        Collections.sort(userEvents, new Comparator<UserEvent>() {
            @Override
            public int compare(UserEvent o1, UserEvent o2) {
                if (o1.getEvent().getStartTime() == null || o2.getEvent().getStartTime() == null) {
                    return 0;
                }
                return o1.getEvent().getStartTime().before(o2.getEvent().getStartTime()) ? 1 : 0;
            }
        });
        System.out.println(userEvents.toString());
        Event prev = null;

        for (UserEvent ue : userEvents) {
            Event currentEvent = ue.getEvent();
            if (ue.getEvent().getStartTime() != null) {
                if (prev != null && !prev.getLocation().equals(currentEvent.getLocation())) {
                    cal.add(Calendar.MINUTE, 30);
                }
                schedule.put(cal.getTime().toString(), ue.getEvent());
                cal.add(Calendar.MINUTE, (int) getDateDiff(currentEvent.getStartTime(), currentEvent.getEndTime()));
                prev = currentEvent;
            }
        }
        return new ResponseEntity<Map<String, Event>>(schedule, HttpStatus.OK);
    }

    private static long getDateDiff(Date date1, Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    @DeleteMapping("{eventId}")
    void deleteMe(@PathVariable Long eventId) {
        System.out.println(eventId);
        eventRepository.deleteById(eventId);
    }

}
