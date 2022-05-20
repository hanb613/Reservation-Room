package com.sw.reservation.board.Reply;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Reply {

    @Id
    private Long no;
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

    public void updateByReply(String content){
        this.content = content;
    }
}
