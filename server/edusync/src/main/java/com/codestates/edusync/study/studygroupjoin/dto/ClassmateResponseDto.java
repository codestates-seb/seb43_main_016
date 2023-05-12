package com.codestates.edusync.study.studygroupjoin.dto;

import com.codestates.edusync.member.dto.MemberResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ClassmateResponseDto {

    private Long id;
    private String address;
    private MemberResponseDto member;
    private Count count;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Count {
        private Integer studygroupAsLeader;
        private Integer studygroup;
        private Integer comment;
        private Integer tag;
    }
}
