package com.sw.reservation.board.Question;

import com.sw.reservation.board.Question.request.QuestionUpdateReq;
import com.sw.reservation.common.errors.NotFoundException;
import com.sw.reservation.common.web.HttpSessionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import com.sw.reservation.common.utils.ApiUtils.ApiResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.sw.reservation.common.utils.ApiUtils.fail;
import static com.sw.reservation.common.utils.ApiUtils.success;

@RestController
@RequestMapping(value="/board")
public class QuestionController {

    @Autowired
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("")
    public ApiResult<Page<Question>> getBoard(@PageableDefault(size = 10, sort = "no", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Question> getByQuestion = questionService.getByQuestion(pageable);

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
