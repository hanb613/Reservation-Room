package com.sw.reservation.room.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class RoomUpdateReq {
    @NotNull(message = "seatsNumber must be provided")
    private int seatsNumber;

    @NotNull(message = "computer must be provided")
    private boolean computer;
}