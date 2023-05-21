package com.codestates.edusync.model.study.studygroup.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleConverter {

    private static int getDayIndex(String day) {
        List<String> daysOfWeek = Arrays.asList("일", "월", "화", "수", "목", "금", "토");
        return daysOfWeek.indexOf(day);
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
}
