package com.sw.reservation.core;

import com.sw.reservation.core.request.PostCoreReq;
import com.sw.reservation.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoreService {

    private final CoreRepository coreRepository;
    private final RoomRepository roomRepository;

    public Optional<Core> createCore(Long memeberId, PostCoreReq postCoreReq){

    }
}
