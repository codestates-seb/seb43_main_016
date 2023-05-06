package com.codestates.edusync.infodto.timeschedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class TimeScheduleSingleResponseDto {
    private Long id;

    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private Timestamp startTime;

    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private Timestamp endTime;
}
