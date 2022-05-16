package com.sw.reservation.room;

import com.sw.reservation.core.Core;
import com.sw.reservation.room.request.PostRoomReq;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Room {

    @Id @GeneratedValue
    private Long roomId;

    private String roomNumber;

    private int seatsNumber;
    private boolean computer;

    @OneToMany(mappedBy = "roomId")
    private List<Core> core = new ArrayList<>();

    public void updateByRoom(int seatsNumber, boolean computer){
        this.seatsNumber = seatsNumber;
        this.computer = computer;
    }

    public Room(PostRoomReq postRoomReq){
        this.roomNumber = postRoomReq.getRoomNumber();
        this.seatsNumber = postRoomReq.getSeatsNumber();
        this.computer = postRoomReq.isComputer();
    }
}
