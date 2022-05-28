package com.sw.reservation.core;

import com.sw.reservation.common.utils.ApiUtils;
import com.sw.reservation.common.utils.ApiUtils.ApiResult;
import com.sw.reservation.core.request.PostReservationReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
     *     "roomId":3,
     *     "studentId":1923717
     *     //null 허용
     *     "withStudent": [
     *          1924268,
     *          1428293
     *     ]
     * }
     */
    @PostMapping
    public ApiResult<String> createCore(@Valid @RequestBody PostReservationReq postReservationReq, HttpSession session){
        return success(coreService.createCore(postReservationReq, session));
    }

    // 강의실 별 예약 현황
    @GetMapping("/{roomId}")
    public ApiResult<List<FindReservationDto>> getCore(@PathVariable Long roomId){
        return success(coreService.getByCore(roomId));
    }

    // 유저 별 예약 현황
    @GetMapping("/users")
    public ApiResult<List<FindUserReservationDto>> getUserCore(HttpSession session){
        return success(coreService.getById(session));
    }

    // 예약 삭제
    @DeleteMapping("/{id}")
    public ApiResult<String> deleteCore(@PathVariable Long id){
        return ApiUtils.success(coreService.deleteReservation(id));
    }

    @PatchMapping("/extension")
    public ApiResult<String> extensionCore(HttpSession session){
        return ApiUtils.success(coreService.extensionReservation(session));
    }
}
