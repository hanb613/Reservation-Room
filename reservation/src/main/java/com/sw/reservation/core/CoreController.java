package com.sw.reservation.core;

import com.sw.reservation.core.request.PostCoreReq;
import com.sw.reservation.room.Room;
import com.sw.reservation.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class CoreController {
    private final CoreService coreService;

    @PostMapping()
    public ResponseEntity<Core> createCore(@RequestBody CoreDto coreDto){

        return ResponseEntity.status(200).body(
                coreService.createCore()
        );
    }
}
