package com.sw.reservation.board.Reply;

import com.sw.reservation.board.Reply.request.ReplyUpdateReq;
import com.sw.reservation.common.errors.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    public Reply registerReply(ReplyDto replyDto){
        Reply newReply = new Reply();

        newReply.setNo(replyDto.getNo());
        newReply.setContent(replyDto.getContent());
        newReply.setWriter("관리자");
        return replyRepository.save(newReply);
    }

    public List<Reply> getByReply(){
        return replyRepository.findAll();
    }

    @Transactional
    public void deleteReply(Long no){
        replyRepository.deleteByNo(no);
    }

    @Transactional
    public Optional<Reply> updateReply(Long no, ReplyUpdateReq replyUpdateReq){
        Reply reply = replyRepository.findByNo(no)
                .orElseThrow(() -> new NotFoundException("Could not find board by" + no));

        reply.updateByReply(replyUpdateReq.getContent());
        return Optional.of(
                replyRepository.save(reply)
        );
    }
}