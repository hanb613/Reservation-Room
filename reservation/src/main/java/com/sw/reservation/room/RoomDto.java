package com.sw.reservation.room;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class RoomDto {
    private String roomNumber;
    private int seatsNumber;
    private boolean computer;

    public Room toEntity(){
        return Room.builder()
                .roomNumber(roomNumber)
                .seatsNumber(seatsNumber)
                .computer(computer)
                .build();
    }
}
