package com.sw.reservation.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sw.reservation.core.request.PostReservationReq;
import com.sw.reservation.room.Room;
import com.sw.reservation.users.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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


    public Core(PostReservationReq postReservationReq, User user, Room room) {
        this.studentId = user;
        this.roomId = room;
        this.startTime = postReservationReq.getStartTime();
        this.endTime =  postReservationReq.getEndTime();
    }
}
