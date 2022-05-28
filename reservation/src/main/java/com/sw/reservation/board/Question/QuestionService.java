package com.sw.reservation.board.Question;

import com.sw.reservation.board.Question.request.QuestionUpdateReq;
import com.sw.reservation.common.errors.NotFoundException;
import com.sw.reservation.common.web.HttpSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Question registerQuestion(QuestionDto questionDto, HttpSession session){

        Question newQuestion = new Question();

        // 현재 로그인되어 있는 회원의 정보를 sessionUser 에 복사
        String sessionUser = HttpSessionUtils.getUserFromSession(session).toString();

        newQuestion.setTitle(questionDto.getTitle());
        newQuestion.setContent(questionDto.getContent());
        newQuestion.setWriter(sessionUser);
        return questionRepository.save(newQuestion);
    }

    @Transactional
    public void deleteQuestion(Long no, HttpSession session){

        String sessionUser = HttpSessionUtils.getUserFromSession(session).toString();

        if(questionRepository.findByNo(no).get().getWriter().equals(sessionUser)){
            questionRepository.deleteByNo(no);
        }
        else {
            throw new NotFoundException("해당 게시물을 삭제할 수 없습니다.");
        }
    }

    public Page<Question> getByQuestion(Pageable pageable){
        return questionRepository.findAll(pageable);
    }

    @Transactional
    public Optional<Question> updateQuestion(Long no, HttpSession session, QuestionUpdateReq questionUpdateReq){
        String sessionUser = HttpSessionUtils.getUserFromSession(session).toString();

        if(questionRepository.findByNo(no).get().getWriter().equals(sessionUser)){
            Question question = questionRepository.findByNo(no)
                    .orElseThrow(() -> new NotFoundException("Could not find question by" + no));

            question.updateByQuestion(questionUpdateReq.getTitle(), questionUpdateReq.getContent());
            return Optional.of(
                    questionRepository.save(question)
            );
        }
        else {
            throw new NotFoundException("해당 게시물을 수정할 수 없습니다.");
        }

    }
}
