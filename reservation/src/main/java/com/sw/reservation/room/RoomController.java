package com.sw.reservation.room;

import com.sw.reservation.common.errors.NotFoundException;
import com.sw.reservation.common.utils.ApiUtils.ApiResult;
import com.sw.reservation.room.request.PostRoomReq;
import com.sw.reservation.room.request.RoomUpdateReq;
import com.sw.reservation.room.response.RoomGetRes;
import com.sw.reservation.room.response.RoomUpdateRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.sw.reservation.common.utils.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/room")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ApiResult<Room> createRoom(@Valid @RequestBody PostRoomReq postRoomReq){
        return success(roomService.createRoom(postRoomReq));
    }

    @GetMapping
    public ApiResult<RoomGetRes> getRoom(){
        return success(new RoomGetRes(roomService.getByRoom())
        );
    }

    @DeleteMapping("/{seq}")
    public ApiResult<Long> deleteRoom(@PathVariable Long seq){
        roomService.deleteRoom(seq);
        return success(seq);
    }

    @PatchMapping("/{seq}")
    public ApiResult<RoomUpdateRes> updateRoom(@PathVariable Long seq, @Valid @RequestBody RoomUpdateReq roomUpdateReq){
        return success(new RoomUpdateRes(roomService.updateRoom(seq, roomUpdateReq)
                .map(RoomDto::new)
                .orElseThrow(() -> new NotFoundException("update 실패했습니다.")))
        );
    }
}
