package com.sw.reservation.room;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RoomDto {
    private Long roomId;
    private String roomNumber;
    private int seatsNumber;
    private boolean computer;

    public RoomDto(Room room) {
        this.roomId = room.getRoomId();
        this.roomNumber = room.getRoomNumber();
        this.seatsNumber = room.getSeatsNumber();
        this.computer = room.isComputer();
    }
}
