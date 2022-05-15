package com.sw.reservation.room;

import com.sw.reservation.core.Core;
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
    private Long idx;

    private String roomNumber;

    private int seatsNumber;
    private boolean computer;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    private Core core;

    public void changeReserved(){
        this.status = Status.RESERVED;
    }

    public void changeUnReserved(){
        this.status = Status.UNRESERVED;
    }

    public void updateByRoom(int seatsNumber, boolean computer){
        this.seatsNumber = seatsNumber;
        this.computer = computer;
    }


}
