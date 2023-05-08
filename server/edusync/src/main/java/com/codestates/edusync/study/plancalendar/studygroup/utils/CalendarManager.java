package com.codestates.edusync.study.plancalendar.studygroup.utils;

public interface CalendarManager<T> {

    /**
     * <h2>T 타입에 대한 캘린더 생성</h2>
     * classmate 인 경우에는 생성할 게 없음<br>
     * studygroup 인 경우에는 동일한 일정을 classmate 의 각 캘린더에 추가한다<br>
     * @param id
     * @return
     */
    T createCalendar(Long id);


}
