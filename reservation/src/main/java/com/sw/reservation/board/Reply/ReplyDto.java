package com.sw.reservation.board.Reply;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyDto {

    private Long no;
    private String content;
    private String writer;
    private String date;

    @Builder
    public ReplyDto(Long no, String content, String writer, String date) {
        this.no = no;
        this.content = content;
        this.writer = writer;
        this.date = date;
    }
}
