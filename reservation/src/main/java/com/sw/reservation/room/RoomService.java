package com.sw.reservation.room;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room addRoom(RoomDto roomDto){
        Room newRoom = roomDto.toEntity();
        return roomRepository.save(newRoom);
    }
}
