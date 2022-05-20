package com.sw.reservation.room.response;

import com.sw.reservation.room.RoomDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class RoomGetRes {
    private final List<RoomDto> roomList;
}
