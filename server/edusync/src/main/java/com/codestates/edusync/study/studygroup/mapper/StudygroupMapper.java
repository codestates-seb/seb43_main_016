package com.codestates.edusync.study.studygroup.mapper;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.member.dto.MemberJoinResponseDto;
import com.codestates.edusync.member.entity.Member;
import com.codestates.edusync.study.studygroup.dto.StudygroupDto;
import com.codestates.edusync.study.studygroup.dto.StudygroupResponseDto;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StudygroupMapper {
    /**
     * 스터드 등록 시, Request Body 로 받아 Studygroup 객체로 매핑
     * @param studygroupDto
     * @return
     * @throws Exception
     */
    default Studygroup StudygroupDtoPostToStudygroup(StudygroupDto.Post studygroupDto) throws Exception{
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
        studygroup.setPlatform(studygroupDto.getPlatform());
        studygroup.setIs_requited(false);
        studygroup.setSearchTags(studygroupDto.getTags());
        return studygroup;
    }

    /**
     * 스터디 조회 시, Studygroup 객체를 ResponseDto 로 매핑
     * @param studygroup
     * @return
     * @throws Exception
     */
    default StudygroupResponseDto StudygroupToStudygroupResponseDto(Studygroup studygroup) throws Exception{
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
        responseDto.setPlatform(studygroup.getPlatform());
        responseDto.setIntroduction(studygroup.getIntroduction());
        responseDto.setRequited(studygroup.getIs_requited());
        responseDto.setTags(studygroup.getSearchTags());
        responseDto.setLeader(memberToStudyLeader(studygroup.getLeaderMember()));
        return responseDto;
    }

    /**
     * 스터디 조회 시, 스터디 리더 정보 매핑
     * @param member
     * @return
     * @throws Exception
     */
    default StudygroupResponseDto.StudyLeader memberToStudyLeader(Member member) throws Exception{
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
    default Studygroup StudygroupDtoPatchToStudygroup(StudygroupDto.Patch studygroupDto) throws Exception{
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
        studygroup.setPlatform(studygroupDto.getPlatform());
        studygroup.setSearchTags(studygroupDto.getTags());
        return studygroup;
    }

    /**
     * 스터디 리스트 조회
     * @param studygroups
     * @return
     */
    default List<StudygroupResponseDto.DtoList> StudygroupListToStudygroupResponseDtoList(List<Studygroup> studygroups) throws Exception {
        List<StudygroupResponseDto.DtoList> respnseDtoList = new ArrayList<>(studygroups.size());
        Iterator studygroupIterator = studygroups.iterator();

        while(studygroupIterator.hasNext()) {
            StudygroupResponseDto.DtoList responseDto =
                    StudygroupsToStudygroupResponseDtoList((Studygroup) studygroupIterator.next());
            respnseDtoList.add(responseDto);
        }

        return respnseDtoList;
    }

    /**
     * 스터디 리스트 조회 시, 각 스터디를 ResponseDto 로 매핑
     * @param studygroup
     * @return
     */
    default StudygroupResponseDto.DtoList StudygroupsToStudygroupResponseDtoList(Studygroup studygroup) throws Exception{
        StudygroupResponseDto.DtoList dtoList = new StudygroupResponseDto.DtoList();
        dtoList.setId(studygroup.getId());
        dtoList.setTitle(studygroup.getStudyName());
        dtoList.setTags(studygroup.getSearchTags());
        return dtoList;
    }
}
