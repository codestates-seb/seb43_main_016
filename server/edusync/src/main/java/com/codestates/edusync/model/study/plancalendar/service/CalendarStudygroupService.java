package com.codestates.edusync.model.study.plancalendar.service;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.model.common.entity.TimeRange;
import com.codestates.edusync.model.common.utils.*;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.plancalendar.repository.CalendarRepository;
import com.codestates.edusync.model.study.studygroupjoin.entity.StudygroupJoin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.codestates.edusync.exception.ExceptionCode.YOU_ARE_NOT_STUDYGROUP_LEADER;

@Transactional
@RequiredArgsConstructor
@Service
public class CalendarStudygroupService implements CalendarStudygroupManager {
    private final CalendarRepository calendarRepository;
    private final VerifyCalendarUtils calendarUtils;
    private final VerifyStudygroupUtils studygroupUtils;
    private final VerifyTimeScheduleUtils timeScheduleUtils;
    private final MemberUtils memberUtils;

    @Override
    public void createTimeSchedules(Long studygroupId,
                                    List<TimeSchedule> timeSchedules,
                                    String email) {
        Member loginMember = memberUtils.getLoggedIn(email);
        Studygroup findStudygroup = studygroupUtils.findVerifyStudygroup(studygroupId);

        timeSchedules.forEach(ts -> {
            ts.setStudygroup(findStudygroup);
            ts.setTitle(findStudygroup.getStudyName());
            ts.setContent((findStudygroup.getPlatform()));
        } );
        calendarRepository.saveAll(timeSchedules);

        createTimeSchedulesOfAllMember(
                studygroupId,
                timeSchedules,
                email
        );
    }


    public void createTimeSchedulesOfAllMember(Long studygroupId,
                                               List<TimeSchedule> timeSchedules,
                                               String email) {
        Member loginMember = memberUtils.getLoggedIn(email);
        Studygroup findStudygroup = studygroupUtils.findVerifyStudygroup(studygroupId);

        if(!findStudygroup.getLeaderMember().getId().equals(loginMember.getId())) {
            throw new BusinessLogicException(YOU_ARE_NOT_STUDYGROUP_LEADER);
        }

        List<TimeSchedule> newTimeSchedules = new ArrayList<>();
        findStudygroup.getStudygroupJoins()
                .forEach(studygroupJoin -> {
                    if( studygroupJoin.getIsApproved() ) {
                        timeSchedules.forEach(ts -> {
                            TimeSchedule result = new TimeSchedule();
                            result.setTitle(ts.getTitle());
                            result.setContent(ts.getContent());
                            result.setTime(ts.getTime());
                            result.setMember(studygroupJoin.getMember());
                            result.setStudygroup(findStudygroup);
                            newTimeSchedules.add(result);
                        });
                    }
                });

        calendarRepository.saveAll(newTimeSchedules);
    }


    @Override
    public void updateTimeSchedule(Long studygroupId, Long timeScheduleId,
                                   TimeSchedule timeSchedule,
                                   String email) {
        Member loginMember = memberUtils.getLoggedIn(email);
        TimeSchedule findTimeSchedule = calendarUtils.findVerifyTimeSchedule(timeScheduleId);

        Optional.ofNullable(timeSchedule.getTitle()).ifPresent(findTimeSchedule::setTitle);
        Optional.ofNullable(timeSchedule.getContent()).ifPresent(findTimeSchedule::setContent);

        findTimeSchedule.setTime(
                new TimeRange(
                        (timeSchedule.getTime().getStudyTimeStart() == null ?
                                findTimeSchedule.getTime().getStudyTimeStart()
                                : timeSchedule.getTime().getStudyTimeStart() ),
                        (timeSchedule.getTime().getStudyTimeEnd() == null ?
                                findTimeSchedule.getTime().getStudyTimeEnd()
                                : timeSchedule.getTime().getStudyTimeEnd() ) )
        );

        calendarRepository.save(findTimeSchedule);
    }

    public void updateTimeSchedulesOfAllMember(Long studygroupId, Long timeScheduleId,
                                               TimeSchedule timeSchedule,
                                               String email) {
        Member loginMember = memberUtils.getLoggedIn(email);
        Studygroup findStudygroup = studygroupUtils.findVerifyStudygroup(studygroupId);

        if(!findStudygroup.getLeaderMember().getId().equals(loginMember.getId())) {
            throw new BusinessLogicException(YOU_ARE_NOT_STUDYGROUP_LEADER);
        }

        TimeSchedule referenceOfTimeSchedule = timeScheduleUtils.findVerifyTimeSchedule(timeScheduleId);

        List<TimeSchedule> findTimeSchedules =
                calendarRepository.findAllByTimeStudyTimeStart(
                        referenceOfTimeSchedule.getTime().getStudyTimeStart()
                );


        findTimeSchedules.forEach(ts -> {
            Optional.ofNullable(timeSchedule.getTitle()).ifPresent(ts::setTitle);
            Optional.ofNullable(timeSchedule.getContent()).ifPresent(ts::setContent);

            ts.setTime(
                    new TimeRange(
                            (timeSchedule.getTime().getStudyTimeStart() == null ?
                                    ts.getTime().getStudyTimeStart()
                                    : timeSchedule.getTime().getStudyTimeStart() ),
                            (timeSchedule.getTime().getStudyTimeEnd() == null ?
                                    ts.getTime().getStudyTimeEnd()
                                    : timeSchedule.getTime().getStudyTimeEnd() ) )
            );
        });

        calendarRepository.saveAll(findTimeSchedules);
    }

    @Override
    public List<TimeSchedule> getTimeSchedules(Long studygroupId) {
        return calendarRepository.findAllByStudygroupIdAndMemberIsNull(studygroupId);
    }

    @Override
    public TimeSchedule getSingleTimeScheduleByTimeScheduleId(Long studygroupId, Long timeScheduleId) {

        return calendarUtils.findVerifyTimeSchedule(timeScheduleId);
    }

    @Override
    public void deleteAllTimeSchedules(Long studygroupId,
                                       String email) {
        Member loginMember = memberUtils.getLoggedIn(email);
        List<TimeSchedule> findTimeSchedules = calendarRepository.findAllByStudygroupId(studygroupId);
        
        calendarRepository.deleteAll(findTimeSchedules);
    }

    @Override
    public void deleteTimeScheduleByTimeScheduleId(Long studygroupId,
                                                   Long timeScheduleId,
                                                   String email) {
        Member loginMember = memberUtils.getLoggedIn(email);
        TimeSchedule findTimeSchedule = calendarUtils.findVerifyTimeSchedule(timeScheduleId);
        
        calendarRepository.delete(findTimeSchedule);
    }
}