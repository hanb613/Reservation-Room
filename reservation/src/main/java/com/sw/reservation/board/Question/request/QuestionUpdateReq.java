package com.sw.reservation.board.Question.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionUpdateReq {
    private String title;
    private String content;
}