package com.sw.reservation.core;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoreRepository extends JpaRepository<Core, Long> {
    Optional<Core> findById(Long id);
}
