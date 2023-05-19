package com.codestates.edusync.model.study.studygroup.dto;

import lombok.Getter;

import java.util.List;

public class StudyListResponseDto {

    @Getter
    public static class beStudyList<T> {
        private List<T> beStudys;

        public beStudyList(List<T> beStudys) {
            this.beStudys = beStudys;
        }
    }

    @Getter
    public static class studyList<T> {
        private List<T> leaders;
        private List<T> members;

        public studyList(List<T> leaders, List<T> members) {
            this.leaders = leaders;
            this.members = members;
        }
    }
}
