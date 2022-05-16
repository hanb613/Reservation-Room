package com.sw.reservation.core;

import com.sw.reservation.common.errors.NotFoundException;
import com.sw.reservation.common.utils.ApiUtils;
import com.sw.reservation.common.utils.ApiUtils.ApiResult;
import com.sw.reservation.core.request.PostReservationReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sw.reservation.common.utils.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class CoreController {
    private final CoreService coreService;

    /**
     * 예약 하기
     * [post] /reservation
     * Body
     * {
     *     "startTime":"2022-01-12T14:00",
     *     "endTime":"2022-01-12T14:30",
     *     "roomId":1,
     *     "studentId":1923717
     * }
     */
    @PostMapping
    public ApiResult<String> createCore(@RequestBody PostReservationReq postReservationReq){
        return success(coreService.createCore(postReservationReq));
    }

    // 강의실 별 예약 현황
    @GetMapping("/{roomId}")
    public List<Core> getCore(@PathVariable Long roomId){
        return coreService.getByCore(roomId);
    }

    // 유저 별 예약 현황
    @GetMapping("/users/{studentId}")
    public ApiResult<Core> getUserCore(@PathVariable Long studentId){
        return success(coreService.getById(studentId)
                .orElseThrow(() -> new NotFoundException("exception")));
    }

    // 예약한 강의실 삭제
    @DeleteMapping("/{id}")
    public ApiResult<String> deleteCore(@PathVariable Long id){
        coreService.deleteReservation(id);
        return ApiUtils.success("예약을 삭제했습니다.");
    }
}
