package com.sw.reservation.room.response;

import com.sw.reservation.room.RoomDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoomUpdateRes {
    private final RoomDto roomDto;
}
