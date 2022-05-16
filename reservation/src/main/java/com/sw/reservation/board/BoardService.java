package com.sw.reservation.board;

import com.sw.reservation.common.web.HttpSessionUtils;
import com.sw.reservation.users.User;
import com.sw.reservation.users.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Board registerQuestion(BoardDto boardDto, HttpSession session){

        Board newBoard = new Board();

        // 현재 로그인되어 있는 회원의 정보를 sessionUser 에 복사
        String sessionUser = HttpSessionUtils.getUserFromSession(session).toString();

        newBoard.setTitle(boardDto.getTitle());
        newBoard.setContent(boardDto.getContent());
        newBoard.setWriter(sessionUser);
        return boardRepository.save(newBoard);
    }

    public void deleteQuestion(Long no){
        boardRepository.deleteByno(no);
    }

}
