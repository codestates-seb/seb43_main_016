package com.codestates.edusync.model.study.plancalendar.service;

import com.codestates.edusync.model.common.entity.TimeRange;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;

import java.util.Optional;

public interface CommonCalendarFeature {
    static void setTimeScheduleInformation(TimeSchedule source, TimeSchedule target) {
        Optional.ofNullable(source.getTitle()).ifPresent(target::setTitle);
        Optional.ofNullable(source.getPlatform()).ifPresent(target::setPlatform);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
        Optional.ofNullable(source.getColor()).ifPresent(target::setColor);

        target.setTime(
                new TimeRange(
                        (source.getTime().getStudyTimeStart() == null ?
                                target.getTime().getStudyTimeStart()
                                : source.getTime().getStudyTimeStart() ),
                        (source.getTime().getStudyTimeEnd() == null ?
                                target.getTime().getStudyTimeEnd()
                                : source.getTime().getStudyTimeEnd() ) )
        );
    }
}
