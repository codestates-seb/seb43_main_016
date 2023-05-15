package com.codestates.edusync.model.study.plancalendar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class TimeScheduleInfoDto {
    @JsonFormat(pattern = "MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp start;

    @JsonFormat(pattern = "MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp end;
}
