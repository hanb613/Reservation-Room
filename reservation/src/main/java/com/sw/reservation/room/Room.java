package com.sw.reservation.room;

import com.sw.reservation.core.Core;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id @GeneratedValue
    private int idx;

    private String roomNumber;

    private int seatsNumber;
    private boolean computer;

    @OneToMany(mappedBy = "room")
    private List<Core> cores = new ArrayList<>();

    @Builder
    public Room(int idx, String roomNumber, int seatsNumber, boolean computer) {
        this.idx = idx;
        this.roomNumber = roomNumber;
        this.seatsNumber = seatsNumber;
        this.computer = computer;
    }
}
