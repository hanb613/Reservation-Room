package com.sw.reservation.room;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class RoomController {

    private final RoomService roomService;


    @PostMapping(value = "room")
    public ResponseEntity<Room> create(@RequestBody RoomDto roomDto){
        System.out.println("roomDto = " + roomDto);
        Room room = roomService.addRoom(roomDto);
        return ResponseEntity.status(200).body(room);
    }
}
