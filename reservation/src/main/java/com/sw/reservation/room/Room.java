package com.sw.reservation.room;

import com.sw.reservation.core.Core;
import com.sw.reservation.room.request.RoomUpdateReq;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Room {

    @Id @GeneratedValue
    private Long idx;

    private String roomNumber;

    private int seatsNumber;
    private boolean computer;

    @OneToMany(mappedBy = "room")
    private List<Core> cores = new ArrayList<>();

    public void updateByRoom(int seatsNumber, boolean computer){
        this.seatsNumber = seatsNumber;
        this.computer = computer;
    }


}
