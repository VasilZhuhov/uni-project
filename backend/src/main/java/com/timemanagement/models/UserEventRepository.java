package com.timemanagement.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserEventRepository extends JpaRepository<UserEvent, Long> {

    @Query(value = "SELECT * FROM user_event ue WHERE ue.user_id = ?1", nativeQuery = true)
    List<UserEvent> findUserEventsByUserId(long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_event ue WHERE ue.event_id = ?1", nativeQuery = true)
    void deleteUserEventByEventId(long eventId);
}
