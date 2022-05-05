package com.sw.reservation.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<User, String> {
    Optional<User> findById(String id);
}
