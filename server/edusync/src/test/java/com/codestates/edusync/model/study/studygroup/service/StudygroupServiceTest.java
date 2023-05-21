package com.codestates.edusync.model.study.studygroup.service;

import com.codestates.edusync.model.common.entity.DateRange;
import com.codestates.edusync.model.common.entity.TimeRange;
import com.codestates.edusync.model.study.plancalendar.entity.TimeSchedule;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import com.codestates.edusync.model.study.studygroup.repository.StudygroupRepository;
import com.codestates.edusync.model.study.studygroup.utils.ScheduleConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class StudygroupServiceTest {
    @Mock
    StudygroupRepository studygroupRepository;

    @InjectMocks
    StudygroupService studygroupService;

    @Test
    void nothing() {
        assert(true);
    }
}