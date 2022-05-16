package com.sw.reservation.room;

import com.sw.reservation.common.errors.NotFoundException;
import com.sw.reservation.common.utils.ApiUtils.ApiResult;
import com.sw.reservation.room.request.PostRoomReq;
import com.sw.reservation.room.request.RoomUpdateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sw.reservation.common.utils.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/room")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ApiResult<Room> createRoom(@RequestBody PostRoomReq postRoomReq){
        return success(roomService.createRoom(postRoomReq));
    }

    @GetMapping
    public ApiResult<List<Room>> getRoom(){
        List<Room> byRoom = roomService.getByRoom();
        return success(byRoom);
    }

    @DeleteMapping("/{seq}")
    public ApiResult<Long> deleteRoom(@PathVariable Long seq){
        roomService.deleteRoom(seq);
        return success(seq);
    }

    @PatchMapping("/{seq}")
    public ApiResult<Room> updateRoom(@PathVariable Long seq, @RequestBody RoomUpdateReq roomUpdateReq){
        return success(roomService.updateRoom(seq, roomUpdateReq)
                        .orElseThrow(() -> new NotFoundException("update 실패했습니다.")));
    }
}
