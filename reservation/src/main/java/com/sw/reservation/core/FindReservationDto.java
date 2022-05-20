package com.sw.reservation.core;

import com.sw.reservation.room.Room;
import com.sw.reservation.users.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class FindReservationDto {

    private Long studentId;
    private String roomNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public FindReservationDto(Core core){
        Room roomId = core.getRoomId();
        User user = core.getStudentId();
        this.studentId = user.getStudentId();
        this.roomNumber = roomId.getRoomNumber();
        this.startTime = core.getStartTime();
        this.endTime = core.getEndTime();
    }
}
