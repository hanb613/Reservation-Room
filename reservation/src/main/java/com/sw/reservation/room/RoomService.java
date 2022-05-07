package com.sw.reservation.room;

import com.sw.reservation.common.errors.NotFoundException;
import com.sw.reservation.room.request.RoomUpdateReq;
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
        return roomRepository.findAll();
    }

    public Optional<Room> updateRoom(Long seq, RoomUpdateReq roomUpdateReq){
        Room room = roomRepository.findById(seq)
                .orElseThrow(() -> new NotFoundException("Could not find room by"+ seq));
        room.updateByRoom(roomUpdateReq.getSeatsNumber(), roomUpdateReq.isComputer());
        return Optional.of(
                roomRepository.save(room)
        );
    }

    public void deleteR(Long seq){
        roomRepository.deleteById(seq);
    }


}
