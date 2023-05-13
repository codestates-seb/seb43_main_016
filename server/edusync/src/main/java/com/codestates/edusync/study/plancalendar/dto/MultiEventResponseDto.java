package com.codestates.edusync.study.plancalendar.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class MultiEventResponseDto<T> {
    private List<T> events;
}
