package com.codestates.edusync.model.study.plancalendar.service;

import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;

import java.util.List;

public interface CalendarStudygroupManager {

    /**
     * <h2>스터디 그룹 일정 리스트를 생성한다</h2>
     * 일정 리스트를 입력 받아, Time Schedule 을 생성 한다.<br>
     * 각각의 Time Schedule 은 <font color="white">해당 스터디 그룹의 일정</font>과 연결 된다.<br>
     * 스터디 그룹만의 캘린더에는 member 식별자 컬럼을 매핑하지 않는다.<br>
     * @param studygroupId  스터디 그룹의 식별자
     * @param timeSchedules 일정 리스트
     * @param loginMember   로그인 중인 맴버
     */
    void createTimeSchedules(Long studygroupId, List<TimeSchedule> timeSchedules, Member loginMember);

    /**
     * <h2>스터디 맴버의 캘린더에 일정 리스트를 생성한다</h2>
     * 일정 리스트를 입력 받아, Time Schedule 을 생성 한다.<br>
     * 각각의 Time Schedule 은 <font color="white">해당 스터디 그룹에 속해 있는 스터디 원들의 일정</font>과 연결 된다.<br>
     * 스터디 그룹에 속한 스터디 맴버의 일정에는 studygroup 식별자 컬럼과 member 식별자 컬럼을 전부 매핑한다.<br>
     * @param studygroupId  스터디 그룹의 식별자
     * @param timeSchedules 일정 리스트
     * @param loginMember   로그인 중인 맴버
     */
    void createTimeSchedulesOfAllMember(Long studygroupId, List<TimeSchedule> timeSchedules, Member loginMember);

    /**
     * <h2>하나의 일정을 수정한다.</h2>
     * Time Schedule Id 에 해당하는 일정의 내용을 수정한다<br>
     * studygroup Id 는 유효성 검증용<br>
     * @param studygroupId   스터디 그룹의 식별자
     * @param timeScheduleId 일정의 식별자
     * @param timeSchedule   변경할 일정 내용
     * @param loginMember    로그인 중인 맴버
     */
    void updateTimeSchedule(Long studygroupId, Long timeScheduleId, TimeSchedule timeSchedule, Member loginMember);

    /**
     * <h2>스터디 그룹에 해당하는 일정 리스트를 조회한다</h2>
     * 스터디 그룹의 식별자에 해당하는 일정 리스트를 조회한다.
     * @param studygroupId
     * @return
     */
    List<TimeSchedule> getTimeSchedules(Long studygroupId);


    /**
     * <h2>하나의 일정을 조회한다</h2>
     * 스터디 그룹에 속하는 일정 하나를 조회한다.
     * @param studygroupId   스터디 그룹의 식별자
     * @param timeScheduleId 일정의 식별자
     * @return
     */
    TimeSchedule getSingleTimeScheduleByTimeScheduleId(Long studygroupId, Long timeScheduleId);

    /**
     * <h2>모든 스터디 맴버와 스터디 그룹의 일정을 삭제한다</h2>
     * 해당 스터디 그룹에 속한 맴버의 일정 리스트를 <font color=white>전부 삭제</font>한다.
     * @param studygroupId studygroup 의 식별자
     * @param loginMember  로그인 중인 맴버
     */
    void deleteAllTimeSchedulesById(Long studygroupId, Member loginMember);

    /**
     * <h2>스터디 그룹의 해당 일정을 삭제한다</h2>
     * 해당 스터디에 속한 일정의 식별자에 해당하는 일정을 삭제한다.
     * @param studygroupId   studygroup 의 식별자
     * @param timeScheduleId timeSchedule 의 식별자
     * @param loginMember    로그인 중인 맴버
     */
    void deleteTimeScheduleByTimeScheduleId(Long studygroupId, Long timeScheduleId, Member loginMember);

    /**
     * <h2>스터디 그룹의 일정과 스터디 그룹에 속한 맴버의 동일한 시간의 일정을 삭제한다. </h2>
     * 해당 <font color=white>스터디 그룹의 일정도 삭제</font>하고, <br>
     * 스터디 그룹에 속한 맴버의 <font color=white>동일한 시간의 일정을 전부 삭제</font>한다.<br>
     * @param studygroupId
     * @param timeScheduleId
     * @param loginMember    로그인 중인 맴버
     */
    void deleteTimeScheduleWithSameTimeOfMember(Long studygroupId, Long timeScheduleId, Member loginMember);
}
