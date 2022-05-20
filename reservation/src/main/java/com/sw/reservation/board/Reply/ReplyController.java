package com.sw.reservation.board.Reply;

import com.sw.reservation.board.Reply.request.ReplyUpdateReq;
import com.sw.reservation.common.errors.NotFoundException;
import com.sw.reservation.common.utils.ApiUtils.ApiResult;
import com.sw.reservation.common.web.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.sw.reservation.common.utils.ApiUtils.fail;
import static com.sw.reservation.common.utils.ApiUtils.success;

@RestController
@RequestMapping(value="/admin/board")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ReplyRepository replyRepository;

    @GetMapping("/")
    public ApiResult<List<Reply>> getReply() {
        List<Reply> getByReply = replyService.getByReply();

        return success(getByReply);
    }

    @RequestMapping(value="/register", method= RequestMethod.POST)
    public ApiResult<String> registerReply(@RequestBody ReplyDto replyDto, HttpSession session){
        if (!HttpSessionUtils.isLoginUser(session) ) {
            return fail("로그인 해주세요.");
        }

        replyService.registerReply(replyDto);
        return success("댓글 등록 완료");
    }

    @DeleteMapping("/{no}")
    public ApiResult<String> deleteReply(@PathVariable("no") Long no, HttpSession session){
        if (!HttpSessionUtils.isLoginUser(session) ) {
            return fail("로그인 해주세요.");
        }

        replyService.deleteReply(no);
        return success("댓글 삭제 완료");
    }

    @PatchMapping("/{no}")
    public ApiResult<Reply> updateReply(@PathVariable("no") Long no, @RequestBody ReplyUpdateReq replyUpdateReq){
        return success(replyService.updateReply(no, replyUpdateReq)
                .orElseThrow(() -> new NotFoundException("update 실패했습니다.")));
    }
}
