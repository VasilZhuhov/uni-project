package com.timemanagement.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    private String location;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<UserEvent> usersEvent = new HashSet<>();

    public Event() {
    }

    public Event(Event e) {
        this.setDescription(e.getDescription());
        this.setEndTime(e.getEndTime());
        this.setStartTime(e.getStartTime());
        this.setLocation(e.getLocation());
        this.setTitle(e.getTitle());
//        for (UserEvent ue : e.getUsersEvent()) {
//            usersEvent.add(new UserEvent(ue.getUser(), e));
//        }
    }
}
