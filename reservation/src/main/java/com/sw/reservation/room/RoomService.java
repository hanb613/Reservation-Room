package com.sw.reservation.room;

import com.sw.reservation.common.errors.NotFoundException;
import com.sw.reservation.room.request.PostRoomReq;
import com.sw.reservation.room.request.RoomUpdateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room createRoom(PostRoomReq postRoomReq){
        if(postRoomReq.getRoomNumber() == null){
            throw new NotFoundException("강의실 번호를 입력해주세요");
        }
        Room room = new Room(postRoomReq);
        return roomRepository.save(room);
    }

    public List<Room> getByRoom(){
        return roomRepository.findAll();
    }

    public Optional<Room> updateRoom(Long seq, RoomUpdateReq roomUpdateReq){
        Room room = roomRepository.findById(seq)
                .orElseThrow(() -> new NotFoundException("Could not find room by" + seq));
        room.updateByRoom(roomUpdateReq.getSeatsNumber(), roomUpdateReq.isComputer());
        return Optional.of(
                roomRepository.save(room)
        );
    }

    public void deleteRoom(Long seq){
        roomRepository.deleteById(seq);
    }

}
