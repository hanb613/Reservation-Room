package com.sw.reservation.room;

import com.sw.reservation.common.errors.NotFoundException;
import com.sw.reservation.room.request.RoomUpdateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/room")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody RoomDto roomDto){
        Room room = roomService.addRoom(roomDto);
        return ResponseEntity.status(200).body(room);
    }

    @GetMapping
    public ResponseEntity<?> getRoom(){
        List<Room> byRoom = roomService.getByRoom();
        return ResponseEntity.status(200).body(byRoom);
    }


    @DeleteMapping("/{seq}")
    public ResponseEntity<Long> deleteRoom(@PathVariable Long seq){
        roomService.deleteR(seq);
        return ResponseEntity.status(200).body(seq);
    }

    @PatchMapping("/{seq}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long seq, @RequestBody RoomUpdateReq roomUpdateReq){
        return ResponseEntity.status(200).body(
                roomService.updateRoom(seq, roomUpdateReq)
                        .orElseThrow(() -> new NotFoundException("update 안되었습니다."))
        );
    }
}
