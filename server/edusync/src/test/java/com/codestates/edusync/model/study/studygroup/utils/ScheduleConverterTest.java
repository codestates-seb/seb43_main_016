package com.codestates.edusync.model.study.studygroup.utils;

import com.codestates.edusync.model.common.entity.DateRange;
import com.codestates.edusync.model.common.entity.TimeRange;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleConverterTest {

    @Test
    void convertToIndexTest() {
        // given
        List<Integer> expect = List.of(1, 3, 5);

        // when
        List<Integer> actual = ScheduleConverter.convertToIndex("[월,수,금]");

        // then
        assertEquals(expect, actual);
    }

    @Test
    void repeatedScheduleToScheduleListConverterTest() {
        // given
        LocalDateTime periodStart = LocalDateTime.of(2023, 5, 14, 5, 30, 0);
        LocalDateTime periodEnd = LocalDateTime.of(2023, 5, 24, 5, 30, 0);
        DateRange dateRange = DateRange.builder()
                .studyPeriodStart(periodStart)
                .studyPeriodEnd(periodEnd)
                .build();
        LocalDateTime timeStart = LocalDateTime.of(2023, 5, 24, 16, 30, 0);
        LocalDateTime timeEnd = LocalDateTime.of(2023, 5, 24, 20, 0, 0);
        TimeRange timeRange = TimeRange.builder()
                .studyTimeStart(timeStart)
                .studyTimeEnd(timeEnd)
                .build();
        String daysOfWeek = "[월,수,금]";

        Studygroup studygroup = new Studygroup();
        studygroup.setDate(dateRange);
        studygroup.setTime(timeRange);
        studygroup.setDaysOfWeek(daysOfWeek);

        List<TimeSchedule> expected = new ArrayList<>();
        expected.add(createTimeScheduleFromTimeRange(5, 15));
        expected.add(createTimeScheduleFromTimeRange(5, 17));
        expected.add(createTimeScheduleFromTimeRange(5, 19));
        expected.add(createTimeScheduleFromTimeRange(5, 22));
        expected.add(createTimeScheduleFromTimeRange(5, 24));

        // when
        List<TimeSchedule> actual = ScheduleConverter.repeatedScheduleToScheduleListConverter(studygroup);

        assertEquals(expected.size(), actual.size());
        // then
        for( int i = 0; i < expected.size(); i++ ) {
            assertEquals(
                    expected.get(i).getTime().getStudyTimeStart(),
                    actual.get(i).getTime().getStudyTimeStart()
            );
            assertEquals(
                    expected.get(i).getTime().getStudyTimeEnd(),
                    actual.get(i).getTime().getStudyTimeEnd()
            );
        }
    }

    private static TimeSchedule createTimeScheduleFromTimeRange(int month, int day) {
        TimeSchedule ts = new TimeSchedule();
        ts.setTime(createTimeRange(month, day));

        return ts;
    }

    private static TimeRange createTimeRange(int month, int day) {
        return TimeRange.builder()
                .studyTimeStart(LocalDateTime.of(2023, month, day, 16, 30, 0))
                .studyTimeEnd(LocalDateTime.of(2023, month, day, 20, 0, 0))
                .build();
    }
}