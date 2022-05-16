package com.sw.reservation.board;

import com.sw.reservation.common.web.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value="/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;


    @GetMapping("/")
    public String boardList(){
        return "board/list";
    }

    @RequestMapping(value="/write", method=RequestMethod.POST)
    public String writeQuestion(@RequestBody BoardDto boardDto, HttpSession session){
        if (!HttpSessionUtils.isLoginUser(session) ) {
            return "redirect:/login";
        }

        boardService.registerQuestion(boardDto, session);

        System.out.println("게시물 등록 완료");

        return "redirect:/";
    }

    @DeleteMapping("/{no}")
    public ResponseEntity<Long> deleteQuestion(@PathVariable Long no){
        boardService.deleteQuestion(no);

        return ResponseEntity.status(200).body(no);
    }
}
