package com.sw.reservation.core;

import com.sw.reservation.room.Room;
import com.sw.reservation.users.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Core {

    @Id @GeneratedValue
    private Long id;

    private String studentId;

    private Timestamp createdAt;

    @OneToOne
    Room room;

    @OneToOne
    User user;

    public static Core createCore(User user, Room room){
        Core core= new Core();
        user.setCore(core);
        room.setCore(core);
        room.changeReserved();
        return core;
    }
}
