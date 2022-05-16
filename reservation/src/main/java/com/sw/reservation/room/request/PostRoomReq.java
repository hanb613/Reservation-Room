package com.sw.reservation.room.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PostRoomReq {
    private String roomNumber;
    private int seatsNumber;
    private boolean computer;

}
