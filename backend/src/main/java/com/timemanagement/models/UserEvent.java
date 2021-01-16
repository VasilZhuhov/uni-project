package com.timemanagement.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
public class UserEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonIgnoreProperties("userEvents")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("userEvents")
    private User user;

    private boolean isAccepted;

    public UserEvent() {

    }

    public UserEvent(User u, Event e) {
        this.setUser(u);
        this.setEvent(e);
        isAccepted = true;
    }
}
