package com.codestates.edusync.model.study.plancalendar.repository;

import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarRepository extends JpaRepository<TimeSchedule, Long> {

    List<TimeSchedule> findAllByStudygroupId(Long studygroupId);
    List<TimeSchedule> findAllByMemberEmail(String email);
    List<TimeSchedule> findAllByStudygroupIdAndMemberIsNull(Long studygroupId);

    List<TimeSchedule> findAllByTimeStudyTimeStart(LocalDateTime studyTimeStart);

    void deleteAllByStudygroupIdAndMemberNickName(Long studygroupId, String nickName);
}
