package com.sw.reservation.board;

import com.sw.reservation.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, String> {
    Optional<Board> findById(String id);
    void deleteByno(Long no);
}
