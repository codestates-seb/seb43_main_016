package com.codestates.edusync.infodto.timeschedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class TimeScheduleSingleResponseDto {
    private Long id;

    private Timestamp startTime;
    private Timestamp endTime;
}
