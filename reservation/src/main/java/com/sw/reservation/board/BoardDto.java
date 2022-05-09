package com.sw.reservation.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardDto {

    private String no;
    private String title;
    private String content;
    private String writer;
    private String date;
    private String viewCnt;

    @Builder
    public BoardDto(String no, String title, String content, String writer, String date, String viewCnt){
        this.no = no;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.date = date;
        this.viewCnt = viewCnt;
    }

    public Board toEntity(){
        return Board.builder()
                .no(no)
                .title(title)
                .content(content)
                .writer(writer)
                .date(date)
                .viewCnt(viewCnt)
                .build();
    }
}
