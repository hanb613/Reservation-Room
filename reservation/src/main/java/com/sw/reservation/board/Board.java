package com.sw.reservation.board;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Board {

    @Id
    private String no;
    private String title;
    private String content;
    private String writer;
    private String date;
    private String viewCnt;

}
