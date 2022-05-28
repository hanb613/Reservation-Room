package com.sw.reservation.room;

import com.sw.reservation.common.errors.NotFoundException;
import com.sw.reservation.core.Core;
import com.sw.reservation.core.CoreRepository;
import com.sw.reservation.room.request.PostRoomReq;
import com.sw.reservation.room.request.RoomUpdateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final CoreRepository coreRepository;

    public Room createRoom(PostRoomReq postRoomReq){
        Optional<Room> byRoomNumber = roomRepository.findByRoomNumber(postRoomReq.getRoomNumber());
        boolean present = byRoomNumber.isPresent();
        if(byRoomNumber.isPresent()){
            throw new NotFoundException("동일한 강의실 번호가 존재합니다.");
        }

        Room room = new Room(postRoomReq);
        return roomRepository.save(room);
    }

    public List<RoomDto> getByRoom(){
        return roomRepository.findAll().stream()
                .map(RoomDto::new).collect(Collectors.toList());
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
        Optional<Room> byRoomId = roomRepository.findByRoomId(seq);
        List<Core> checkRoomId = coreRepository.findByRoomId(byRoomId.get());
        if(!checkRoomId.isEmpty()){
            throw new NotFoundException("예약된 좌석이 있습니다.");
        }
        roomRepository.deleteById(seq);
    }

}
