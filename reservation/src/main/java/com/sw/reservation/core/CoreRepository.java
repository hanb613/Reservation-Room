package com.sw.reservation.core;

import com.sw.reservation.room.Room;
import com.sw.reservation.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CoreRepository extends JpaRepository<Core, Long> {
    Optional<Core> findById(Long id);
    Optional<Core> findByStudentId(User user);
    List<Core> findByRoomId(Room roomId);

    void deleteById(Long memberId);

    List<Core> findByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
    List<Core> findByEndTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}
