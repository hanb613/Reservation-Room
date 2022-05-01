package com.sw.reservation.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room addRoom(RoomDto roomDto){
        Room newRoom = roomDto.toEntity();
        return roomRepository.save(newRoom);
    }

    public List<Room> getByRoom(){
        List<Room> all = roomRepository.findAll();
        System.out.println("all = " + all);
        return all;
    }

}
