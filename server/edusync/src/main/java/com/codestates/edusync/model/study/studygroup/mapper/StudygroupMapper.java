package com.codestates.edusync.model.study.studygroup.mapper;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import com.codestates.edusync.model.studyaddons.searchtag.entity.SearchTag;
import com.codestates.edusync.model.study.studygroup.dto.StudygroupDto;
import com.codestates.edusync.model.study.studygroup.dto.StudygroupResponseDto;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StudygroupMapper {
    /**
     * 스터드 등록 시, Request Body 로 받아 Studygroup 객체로 매핑
     * @param studygroupDto
     * @return
     * @throws Exception
     */
    default Studygroup StudygroupDtoPostToStudygroup(StudygroupDto.Post studygroupDto, Member member) {
        Studygroup studygroup = new Studygroup();
        studygroup.setStudyName(studygroupDto.getStudyName());
        studygroup.setDaysOfWeek(studygroupDto.getDaysOfWeek().toString());
        studygroup.setStudyPeriodStart(studygroupDto.getStudyPeriodStart());
        studygroup.setStudyPeriodEnd(studygroupDto.getStudyPeriodEnd());
        studygroup.setStudyTimeStart(studygroupDto.getStudyTimeStart());
        studygroup.setStudyTimeEnd(studygroupDto.getStudyTimeEnd());
        studygroup.setIntroduction(studygroupDto.getIntroduction());
        studygroup.setMemberCountMin(studygroupDto.getMemberCountMin());
        studygroup.setMemberCountMax(studygroupDto.getMemberCountMax());
        studygroup.setMemberCountCurrent(studygroupDto.getMemberCountCurrent());
        studygroup.setPlatform(studygroupDto.getPlatform());
        studygroup.setIs_requited(false);
        studygroup.setLeaderMember(member);

        List<SearchTag> resultTags = new ArrayList<>();
        studygroupDto.getTags()
                .forEach((key, value) -> {
                        SearchTag st = new SearchTag();
                        st.setTagKey(key);
                        st.setTagValue(value);
                        st.setStudygroup(studygroup);

                        resultTags.add(st);
                });
        studygroup.setSearchTags(resultTags);
        return studygroup;
    }

    /**
     * 스터디 조회 시, Studygroup 객체를 ResponseDto 로 매핑
     * @param studygroup
     * @return
     * @throws Exception
     */
    default StudygroupResponseDto StudygroupToStudygroupResponseDto(Studygroup studygroup){
        HashMap<String, String> tags = new HashMap<>();
        studygroup.getSearchTags()
                .forEach(st -> tags.put(st.getTagKey(), st.getTagValue()));

        StudygroupResponseDto responseDto = new StudygroupResponseDto();
        responseDto.setId(studygroup.getId());
        responseDto.setStudyName(studygroup.getStudyName());
        responseDto.setStudyPeriodStart(studygroup.getStudyPeriodStart());
        responseDto.setStudyPeriodEnd(studygroup.getStudyPeriodEnd());
        responseDto.setDaysOfWeek(studygroup.getDaysOfWeek());
        responseDto.setStudyTimeStart(studygroup.getStudyTimeStart());
        responseDto.setStudyTimeEnd(studygroup.getStudyTimeEnd());
        responseDto.setMemberCountMin(studygroup.getMemberCountMin());
        responseDto.setMemberCountMax(studygroup.getMemberCountMax());
        responseDto.setMemberCountCurrent(studygroup.getMemberCountCurrent());
        responseDto.setPlatform(studygroup.getPlatform());
        responseDto.setIntroduction(studygroup.getIntroduction());
        responseDto.setRequited(studygroup.getIs_requited());
        responseDto.setTags(tags);
        responseDto.setLeader(memberToStudyLeader(studygroup.getLeaderMember()));
        return responseDto;
    }

    /**
     * 스터디 조회 시, 스터디 리더 정보 매핑
     * @param member
     * @return
     * @throws Exception
     */
    default StudygroupResponseDto.StudyLeader memberToStudyLeader(Member member){
        if (member == null) throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        StudygroupResponseDto.StudyLeader leader = new StudygroupResponseDto.StudyLeader();
        leader.setId(member.getId());
        leader.setNickName(member.getNickName());
        return leader;
    }

    /**
     * 스터디 수정 시, 스터디 객체로 매핑
     * @param studygroupDto
     * @return
     * @throws Exception
     */
    default Studygroup StudygroupDtoPatchToStudygroup(StudygroupDto.Patch studygroupDto){
        Studygroup studygroup = new Studygroup();
        studygroup.setId(studygroupDto.getId());
        studygroup.setStudyName(studygroupDto.getStudyName());
        studygroup.setDaysOfWeek(studygroupDto.getDaysOfWeek().toString());
        studygroup.setStudyPeriodStart(studygroupDto.getStudyPeriodStart());
        studygroup.setStudyPeriodEnd(studygroupDto.getStudyPeriodEnd());
        studygroup.setStudyTimeStart(studygroupDto.getStudyTimeStart());
        studygroup.setStudyTimeEnd(studygroupDto.getStudyTimeEnd());
        studygroup.setIntroduction(studygroupDto.getIntroduction());
        studygroup.setMemberCountMin(studygroupDto.getMemberCountMin());
        studygroup.setMemberCountMax(studygroupDto.getMemberCountMax());
        studygroup.setMemberCountCurrent(studygroupDto.getMemberCountCurrent());
        studygroup.setPlatform(studygroupDto.getPlatform());

        List<SearchTag> resultTags = new ArrayList<>();
        studygroupDto.getTags()
                .forEach((key, value) -> {
                    SearchTag st = new SearchTag();
                    st.setTagKey(key);
                    st.setTagValue(value);
                    st.setStudygroup(studygroup);

                    resultTags.add(st);
                });
        studygroup.setSearchTags(resultTags);
        return studygroup;
    }

    /**
     * 스터디 리스트 조회 시, 각 스터디를 ResponseDto 로 매핑
     * @param studygroup
     * @return
     */
    default StudygroupResponseDto.DtoList StudygroupsToStudygroupResponseDtoList(Studygroup studygroup){
        StudygroupResponseDto.DtoList dtoList = new StudygroupResponseDto.DtoList();
        dtoList.setId(studygroup.getId());
        dtoList.setTitle(studygroup.getStudyName());
        return dtoList;
    }

    /**
     * 스터디 리스트 조회
     * @param studygroups
     * @return
     */
    default List<StudygroupResponseDto.DtoList> StudygroupListToStudygroupResponseDtoList(List<Studygroup> studygroups){
        return studygroups.stream().map(this::StudygroupsToStudygroupResponseDtoList).collect(Collectors.toList());
    }
}
