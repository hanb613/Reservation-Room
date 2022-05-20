package com.sw.reservation.board.Question;

import com.sw.reservation.board.Question.request.QuestionUpdateReq;
import com.sw.reservation.common.errors.NotFoundException;
import com.sw.reservation.common.web.HttpSessionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import com.sw.reservation.common.utils.ApiUtils.ApiResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.sw.reservation.common.utils.ApiUtils.fail;
import static com.sw.reservation.common.utils.ApiUtils.success;

@RestController
@RequestMapping(value="/board")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public ApiResult<List<Question>> getBoard() {
        List<Question> getByQuestion = questionService.getByQuestion();

        return success(getByQuestion);
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public ApiResult<String> registerQuestion(@RequestBody QuestionDto questionDto, HttpSession session){
        if (!HttpSessionUtils.isLoginUser(session) ) {
            return fail("로그인 해주세요.");
        }

        questionService.registerQuestion(questionDto, session);
        return success("게시물 등록 완료");
    }

    @DeleteMapping("/{no}")
    public ApiResult<String> deleteQuestion(@PathVariable("no") Long no, HttpSession session){
        if (!HttpSessionUtils.isLoginUser(session) ) {
            return fail("로그인 해주세요.");
        }

        questionService.deleteQuestion(no, session);
        return success("게시물 삭제 완료");
    }

    @PatchMapping("/{no}")
    public ApiResult<Question> updateQuestion(@PathVariable("no") Long no, HttpSession session, @RequestBody QuestionUpdateReq questionUpdateReq){
        return success(questionService.updateQuestion(no, session, questionUpdateReq)
                .orElseThrow(() -> new NotFoundException("update 실패했습니다.")));
    }
}
