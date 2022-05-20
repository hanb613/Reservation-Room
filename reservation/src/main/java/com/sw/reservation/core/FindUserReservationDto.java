package com.sw.reservation.core;

import com.sw.reservation.room.Room;
import com.sw.reservation.users.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class FindUserReservationDto {

    private String roomNumber;
    private String userName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public FindUserReservationDto(Core core){
        Room roomId = core.getRoomId();
        User user = core.getStudentId();
        this.userName = user.getName();
        this.roomNumber = roomId.getRoomNumber();
        this.startTime = core.getStartTime();
        this.endTime = core.getEndTime();
    }
}
