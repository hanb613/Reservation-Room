package com.sw.reservation.core;

import com.sw.reservation.common.errors.NotFoundException;
import com.sw.reservation.core.request.PostReservationReq;
import com.sw.reservation.room.Room;
import com.sw.reservation.room.RoomRepository;
import com.sw.reservation.users.User;
import com.sw.reservation.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoreService {

    private final CoreRepository coreRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public String createCore(PostReservationReq postReservationReq){
        Long userId = postReservationReq.getStudentId();
        System.out.println("userId = " + userId);
        User user = userRepository.findByStudentId(userId).orElseThrow(() ->
                new NotFoundException("회원이 존재하지 않습니다."));

        Room room = roomRepository.findByRoomId(postReservationReq.getRoomId()).orElseThrow(() ->
                new NotFoundException("강의실이 존재하지 않습니다."));

        LocalDateTime startTime = postReservationReq.getStartTime();
        LocalDateTime endTime = postReservationReq.getEndTime();
        Long roomId = postReservationReq.getRoomId();

        List<Core> byStartTimeBetween = coreRepository.findByStartTimeBetween(startTime, endTime);
        List<Core> byEndTimeBetween = coreRepository.findByEndTimeBetween(startTime, endTime);

        if(!byStartTimeBetween.isEmpty() || !byEndTimeBetween.isEmpty()){
            throw new NotFoundException("중복된 시간에 예약이 있습니다.");
        }

        Core core = new Core(postReservationReq, user, room);
        Optional.of(coreRepository.save(core));

        return "예약했습니다.";
    }

    @Transactional(readOnly = true)
    public List<Core> getByCore(Long roomId){
        List<Core> byCore = coreRepository.findByRoomId(roomId);
        return byCore;
    }

    @Transactional(readOnly = true)
    public Optional<Core> getById(Long studentId){
        return coreRepository.findByStudentId(studentId);
    }

    @Transactional
    public void deleteReservation(Long id){
        coreRepository.deleteById(id);
    }
}
