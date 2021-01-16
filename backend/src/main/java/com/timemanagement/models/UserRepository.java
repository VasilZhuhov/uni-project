package com.timemanagement.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users u WHERE u.email=?1 AND u.password=?2", nativeQuery = true)
    User findUserByCredentials(String email, String password);
}
