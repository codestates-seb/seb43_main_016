package com.codestates.edusync.model.study.studygroup.utils;

import com.codestates.edusync.model.common.entity.TimeRange;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleConverter {

    private static int getDayIndex(String day) {
        List<String> daysOfWeek = Arrays.asList("월", "화", "수", "목", "금", "토", "일");
        return daysOfWeek.indexOf(day)+1;
    }

    public static List<Integer> convertToIndex(String daysOfWeek) {
        List<Integer> result = new ArrayList<>();

        Pattern pattern = Pattern.compile("[가-힣]");
        Matcher matcher = pattern.matcher(daysOfWeek);

        while (matcher.find()) {
            int index = getDayIndex(matcher.group());
            result.add(index);
        }

        return result;
    }

    public static List<TimeSchedule> repeatedScheduleToScheduleListConverter(Studygroup studygroup) {
        List<TimeSchedule> timeSchedules = new ArrayList<>();

        LocalDateTime periodStart = studygroup.getDate().getStudyPeriodStart();
        LocalDateTime periodEnd = studygroup.getDate().getStudyPeriodEnd();

        LocalDateTime timeStart = studygroup.getTime().getStudyTimeStart();
        LocalDateTime timeEnd = studygroup.getTime().getStudyTimeEnd();
        int continueToNextDayOffset = 0;
        if( timeStart.getHour() > timeEnd.getHour() ) {
            continueToNextDayOffset = 1;
        }

        long totalDays = ChronoUnit.DAYS.between(periodStart.toLocalDate(), periodEnd.toLocalDate());

        List<Integer> indexOfWeeks = ScheduleConverter.convertToIndex(studygroup.getDaysOfWeek());
        for( int offset = 0; offset <= totalDays; offset++ ) {
            LocalDateTime offsetDate = periodStart.plusDays(offset);

            int currentIndexOfWeek = offsetDate.getDayOfWeek().getValue();
            if( indexOfWeeks.contains(currentIndexOfWeek) ) {
                TimeSchedule ts = new TimeSchedule();
                TimeRange tr = TimeRange.builder()
                        .studyTimeStart(
                                LocalDateTime.of(
                                        offsetDate.getYear(),
                                        offsetDate.getMonth(),
                                        offsetDate.getDayOfMonth(),
                                        timeStart.getHour(),
                                        timeStart.getMinute(),
                                        timeStart.getSecond()
                                ))
                        .studyTimeEnd(
                                LocalDateTime.of(
                                        offsetDate.plusDays(continueToNextDayOffset).getYear(),
                                        offsetDate.plusDays(continueToNextDayOffset).getMonth(),
                                        offsetDate.plusDays(continueToNextDayOffset).getDayOfMonth(),
                                        timeEnd.getHour(),
                                        timeEnd.getMinute(),
                                        timeEnd.getSecond()
                                ))
                        .build();
                ts.setTime(tr);
                ts.setTitle(studygroup.getStudyName());
                ts.setPlatform(studygroup.getPlatform());
                ts.setDescription(studygroup.getIntroduction());
                timeSchedules.add(ts);
            }
        }

        return timeSchedules;
    }
}
