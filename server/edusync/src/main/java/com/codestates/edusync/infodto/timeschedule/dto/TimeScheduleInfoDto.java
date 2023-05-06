package com.codestates.edusync.infodto.timeschedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class TimeScheduleInfoDto {
    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private Timestamp start;

    @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
    private Timestamp end;
}
