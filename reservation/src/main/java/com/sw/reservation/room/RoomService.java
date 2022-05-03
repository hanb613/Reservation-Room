package com.sw.reservation.room;

import com.sw.reservation.common.errors.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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

//    public Optional<Room> updateRoom(String roomNumber){
//        Optional<Room> roomId = roomRepository.findById(roomNumber);
//
//    }
    public boolean deleteRoom(Long seq, String roomNumber){
        System.out.println("roomNumber = " + roomNumber);
        Room room = roomRepository.findById(seq).get();
        if(room.getRoomNumber().equals(roomNumber)){
            roomRepository.deleteById(seq);
            return true;
        }
        return false;
    }
}
