package com.codestates.edusync.model.study.studygroup.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
}