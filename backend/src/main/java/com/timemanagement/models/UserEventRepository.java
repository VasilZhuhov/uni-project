package com.timemanagement.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserEventRepository extends JpaRepository<UserEvent, Long> {

}
