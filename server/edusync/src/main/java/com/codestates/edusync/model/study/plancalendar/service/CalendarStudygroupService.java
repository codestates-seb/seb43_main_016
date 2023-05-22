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

import static com.codestates.edusync.exception.ExceptionCode.*;

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
        if( !studygroupUtils.isMemberLeaderOfStudygroup(email, studygroupId) ) {
            throw new BusinessLogicException(YOU_ARE_NOT_STUDYGROUP_LEADER);
        }

        Studygroup findStudygroup = studygroupUtils.findVerifyStudygroup(studygroupId);

        timeSchedules.forEach(ts -> {
            ts.setStudygroup(findStudygroup);
            ts.setTitle(findStudygroup.getStudyName());
            ts.setPlatform((findStudygroup.getPlatform()));
            ts.setDescription(findStudygroup.getIntroduction());
            // todo: color 기본값 적용할 수 있도록
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

        if( findStudygroup.getStudygroupJoins() == null ) {
            return;
        }

        List<TimeSchedule> timeSchedulesOfStudygroup = new ArrayList<>();
        timeSchedules.forEach(ts -> {
            if( ts.getMember() == null ) {
                timeSchedulesOfStudygroup.add(ts);
            }
        });

        List<TimeSchedule> newTimeSchedules = new ArrayList<>();
        findStudygroup.getStudygroupJoins()
                .forEach(studygroupJoin -> {
                    if( studygroupJoin.getIsApproved() ) {
                        newTimeSchedules.addAll(
                                createTimeScheduleList(
                                        timeSchedulesOfStudygroup,      // 모집글을 생성, 수정할 때는 맴버에는 스케쥴이 없고, 스터디 그룹에만 있기 때문에 참조할 때 문제없음
                                        findStudygroup,
                                        studygroupJoin.getMember()
                                )
                        );
                    }
                });

        calendarRepository.saveAll(newTimeSchedules);
    }

    public void createTimeSchedulesOfSingleMemberFromJoin(StudygroupJoin studygroupJoin) {
        Studygroup findStudygroup = studygroupUtils.findVerifyStudygroup(studygroupJoin.getStudygroup().getId());

        List<TimeSchedule> timeSchedulesOfStudygroup = new ArrayList<>();
        studygroupJoin.getStudygroup().getTimeSchedules()
                .forEach(ts -> {
                    if( ts.getMember() == null ) {
                        timeSchedulesOfStudygroup.add(ts);
                    }
                });

        List<TimeSchedule> newTimeSchedules =
                createTimeScheduleList(
                        timeSchedulesOfStudygroup,
                        studygroupJoin.getStudygroup(),
                        studygroupJoin.getMember()
                );

        calendarRepository.saveAll(newTimeSchedules);
    }

    private static List<TimeSchedule> createTimeScheduleList(List<TimeSchedule> timeSchedules,
                                                             Studygroup findStudygroup,
                                                             Member member) {
        List<TimeSchedule> iterableSchedules = new ArrayList<>(timeSchedules);
        List<TimeSchedule> newTimeSchedules = new ArrayList<>();

        for( TimeSchedule ts : iterableSchedules ) {
            TimeSchedule result = new TimeSchedule();
            result.setTitle(ts.getTitle());
            result.setPlatform(ts.getPlatform());
            result.setDescription(ts.getDescription());
            result.setColor(ts.getColor());
            result.setTime(ts.getTime());
            result.setMember(member);
            result.setStudygroup(findStudygroup);
            newTimeSchedules.add(result);
        }

        return newTimeSchedules;
    }


    @Override
    public void updateTimeSchedule(Long studygroupId, Long timeScheduleId,
                                   TimeSchedule timeSchedule,
                                   String email) {
        Member loginMember = memberUtils.getLoggedIn(email);
        TimeSchedule findTimeSchedule = calendarUtils.findVerifyTimeSchedule(timeScheduleId);

        Optional.ofNullable(timeSchedule.getTitle()).ifPresent(findTimeSchedule::setTitle);
        Optional.ofNullable(timeSchedule.getPlatform()).ifPresent(findTimeSchedule::setPlatform);
        Optional.ofNullable(timeSchedule.getDescription()).ifPresent(findTimeSchedule::setDescription);
        Optional.ofNullable(timeSchedule.getColor()).ifPresent(findTimeSchedule::setColor);

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

        updateTimeSchedulesOfAllMember(
                studygroupId, timeScheduleId,
                timeSchedule,
                email
        );
    }

    public void updateTimeSchedulesOfAllMember(Long studygroupId, Long timeScheduleId,
                                               TimeSchedule timeSchedule,
                                               String email) {
        Studygroup findStudygroup = studygroupUtils.findVerifyStudygroup(studygroupId);

        if( !studygroupUtils.isMemberLeaderOfStudygroup(email, studygroupId) ) {
            throw new BusinessLogicException(YOU_ARE_NOT_STUDYGROUP_LEADER);
        }

        TimeSchedule referenceOfTimeSchedule = timeScheduleUtils.findVerifyTimeSchedule(timeScheduleId);

        List<TimeSchedule> findTimeSchedules =
                calendarRepository.findAllByTimeStudyTimeStart(
                        referenceOfTimeSchedule.getTime().getStudyTimeStart()
                );

        findTimeSchedules.forEach(ts -> {
            Optional.ofNullable(timeSchedule.getTitle()).ifPresent(ts::setTitle);
            Optional.ofNullable(timeSchedule.getPlatform()).ifPresent(ts::setPlatform);
            Optional.ofNullable(timeSchedule.getDescription()).ifPresent(ts::setDescription);
            Optional.ofNullable(timeSchedule.getColor()).ifPresent(ts::setColor);

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
        TimeSchedule findTimeSchedule = calendarUtils.findVerifyTimeSchedule(timeScheduleId);
        if( findTimeSchedule.getStudygroup() == null ) {
            throw new BusinessLogicException(TIME_SCHEDULE_NOT_LINKED_WITH_STUDYGROUP);
        }

        if( !findTimeSchedule.getStudygroup().getId().equals(studygroupId) ) {
            throw new BusinessLogicException(TIME_SCHEDULE_NOT_MATCHED_WITH_STUDYGROUP);
        }

        return findTimeSchedule;
    }

    // todo : 스터디 삭제 시 일정 제거용으로 이걸 호출해야함
    @Override
    public void deleteAllTimeSchedulesByStudygroupId(Long studygroupId,
                                                     String email) {
        if( !studygroupUtils.isMemberLeaderOfStudygroup(email, studygroupId) ) {
            throw new BusinessLogicException(YOU_ARE_NOT_STUDYGROUP_LEADER);
        }

        List<TimeSchedule> findTimeSchedules = calendarRepository.findAllByStudygroupId(studygroupId);
        
        calendarRepository.deleteAll(findTimeSchedules);
    }

    @Override
    public void deleteTimeScheduleByTimeScheduleId(Long studygroupId,
                                                   Long timeScheduleId,
                                                   String email) {
        studygroupUtils.isMemberLeaderOfStudygroup(email, studygroupId);
        TimeSchedule findTimeSchedule = calendarUtils.findVerifyTimeSchedule(timeScheduleId);
        if( !findTimeSchedule.getStudygroup().getId().equals(studygroupId) ) {
            throw new BusinessLogicException(TIME_SCHEDULE_NOT_MATCHED_WITH_STUDYGROUP);
        }

        calendarRepository.delete(findTimeSchedule);
    }

    public void deleteTimeScheduleByMember(Long studygroupId,
                                           String nickName) {
        calendarRepository.deleteAllByStudygroupIdAndMemberNickName(studygroupId, nickName);
    }
}
