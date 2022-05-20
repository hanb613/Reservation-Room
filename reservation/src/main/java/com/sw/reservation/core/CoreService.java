package com.sw.reservation.core;

import com.sw.reservation.common.errors.NotFoundException;
import com.sw.reservation.common.web.HttpSessionUtils;
import com.sw.reservation.core.request.PostReservationReq;
import com.sw.reservation.room.Room;
import com.sw.reservation.room.RoomRepository;
import com.sw.reservation.users.User;
import com.sw.reservation.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoreService {

    private final CoreRepository coreRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public String createCore(PostReservationReq postReservationReq, HttpSession session){
        Long userId = HttpSessionUtils.getUserFromSession(session);

        User user = userRepository.findByStudentId(userId).orElseThrow(() ->
                new NotFoundException("회원이 존재하지 않습니다."));

        Room room = roomRepository.findByRoomId(postReservationReq.getRoomId()).orElseThrow(() ->
                new NotFoundException("강의실이 존재하지 않습니다."));

        LocalDateTime startTime = postReservationReq.getStartTime();
        LocalDateTime endTime = postReservationReq.getEndTime();

        List<Core> byStartTimeBetween = coreRepository.findByStartTimeBetween(startTime, endTime);
        List<Core> byEndTimeBetween = coreRepository.findByEndTimeBetween(startTime, endTime);

        if(!byStartTimeBetween.isEmpty() || !byEndTimeBetween.isEmpty()){
            throw new NotFoundException("중복된 시간에 예약이 있습니다.");
        }

        // 같이 예약하는 학생들
        if(postReservationReq.getWithStudent() != null){
            List<Long> withStudent = postReservationReq.getWithStudent();
            for (Long user1 : withStudent){
                Optional<User> byStudentId = userRepository.findByStudentId(user1);
                Core core = new Core(postReservationReq, byStudentId.get(), room);
                Optional.of(coreRepository.save(core));
            }
        }

        Core core = new Core(postReservationReq, user, room);
        Optional.of(coreRepository.save(core));
        return "예약했습니다.";
    }

    public List<FindReservationDto> getByCore(Long roomId){
        Room room = roomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new NotFoundException("강의실 없습니다."));

        List<FindReservationDto> findReserve = new ArrayList<FindReservationDto>();

        for(Core core : room.getCore()){
            findReserve.add(new FindReservationDto(core));
        }
        return findReserve;
    }

    public List<FindUserReservationDto> getById(HttpSession session){
        Long studentId = HttpSessionUtils.getUserFromSession(session);
        Optional<User> user = userRepository.findByStudentId(studentId);

        List<FindUserReservationDto> findReserve = new ArrayList<FindUserReservationDto>();

        for(Core core : user.get().getCore()){
            findReserve.add(new FindUserReservationDto(core));
        }
        return findReserve;
    }

    @Transactional
    public String deleteReservation(Long id){
        coreRepository.deleteById(id);
        return "예약을 삭제했습니다.";
    }

    @Transactional
    public String extensionReservation(HttpSession session){
        Long userId = HttpSessionUtils.getUserFromSession(session);
        Optional<User> byStudentId = userRepository.findByStudentId(userId);
        Optional<Core> byStudentId1 = coreRepository.findByStudentId(byStudentId.get());

        Core core = byStudentId1.get();
        Room roomId = core.getRoomId(); //현재 예약된 강의실
        LocalDateTime endTime = core.getEndTime();
        LocalDateTime plusHours = core.getEndTime().plusHours(1);

        if(core.getExtension() == 1){
            throw new NotFoundException("연장 횟수를 초과했습니다.");
        }
        List<Core> byStartTimeBetween = coreRepository.findByStartTimeBetween(endTime, plusHours);
        for(Core core1 : byStartTimeBetween){
            if(core1.getRoomId() == roomId){
                throw new NotFoundException("예약이 꽉 찼습니다.");
            }
        }

        core.setEndTime(plusHours);
        core.setExtension(1);
        coreRepository.save(core);
        return "연장했습니다.";
    }
}
