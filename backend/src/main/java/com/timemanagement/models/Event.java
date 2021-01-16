package com.timemanagement.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<UserEvent> usersEvent = new HashSet<>();

    public Event() {
    }

    public Event(Event e) {
        this.setDescription(e.getDescription());
        this.setEndTime(e.getEndTime());
        this.setStartTime(e.getStartTime());
        this.setLocation(e.getLocation());
        this.setTitle(e.getTitle());
    }
}
