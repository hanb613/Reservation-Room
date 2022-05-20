package com.sw.reservation.board.Reply;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ReplyRepository extends JpaRepository<Reply, String> {
    Optional<Reply> findByNo(Long no);
    void deleteByNo(Long no);
}