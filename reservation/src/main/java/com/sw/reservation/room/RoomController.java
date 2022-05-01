package com.sw.reservation.room;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class RoomController {

    private final RoomService roomService;

    @GetMapping("roomList")
    public ResponseEntity<?> getRoom(){
        List<Room> byRoom = roomService.getByRoom();
        return ResponseEntity.status(200).body(byRoom);
    }

    @PostMapping(value = "room")
    public ResponseEntity<Room> create(@RequestBody RoomDto roomDto){
        Room room = roomService.addRoom(roomDto);
        return ResponseEntity.status(200).body(room);
    }



}
