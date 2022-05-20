package com.sw.reservation.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sw.reservation.core.request.PostReservationReq;
import com.sw.reservation.room.Room;
import com.sw.reservation.users.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Core {

    @Id @GeneratedValue
    private Long coreId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_Id")
    private User studentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room roomId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endTime;

    private Integer extension;

    @PrePersist
    public void prePersist() {
        this.extension = this.extension == null ? 0 : this.extension;
    }

    public Core(PostReservationReq postReservationReq, User user, Room room) {
        this.studentId = user;
        this.roomId = room;
        this.startTime = postReservationReq.getStartTime();
        this.endTime =  postReservationReq.getEndTime();
        this.extension = postReservationReq.getExtension();
    }
}
