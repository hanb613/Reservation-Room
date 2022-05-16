package com.sw.reservation.board;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    private String title;
    private String content;
    private String writer;

    @CreatedDate
    private String createDate;

    @LastModifiedDate
    private String lastUpdatedDate;

    @PrePersist
    public void onPrePersist(){
        this.createDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        this.lastUpdatedDate = this.createDate;
    }
    @PreUpdate
    public void onPreUpdate(){
        this.lastUpdatedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}
