package com.sw.reservation.room;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, String> {
    Optional<Room> findById(String id);
}
