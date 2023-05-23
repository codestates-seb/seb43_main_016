package com.codestates.edusync.model.study.studygroup.mapper;

import com.codestates.edusync.model.common.entity.DateRange;
import com.codestates.edusync.model.common.entity.TimeRange;
import com.codestates.edusync.model.common.utils.TagFormatConverter;
import com.codestates.edusync.model.study.studygroup.dto.CommonStudygroupDto;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import com.codestates.edusync.model.studyaddons.searchtag.entity.SearchTag;
import com.codestates.edusync.model.study.studygroup.dto.StudygroupDto;
import com.codestates.edusync.model.study.studygroup.dto.StudygroupResponseDto;
import org.mapstruct.Mapper;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StudygroupMapper {
    /**
     * 스터드 등록 시, Request Body 로 받아 Studygroup 객체로 매핑
     * @param studygroupDto
     * @return
     * @throws Exception
     */
    default Studygroup StudygroupDtoPostToStudygroup(StudygroupDto.Post studygroupDto) {
        Studygroup studygroup = new Studygroup();
        commonStudygroupDtoToEntitySetter(studygroupDto, studygroup);
        studygroup.setIsRecruited(false);
        return studygroup;
    }

    private static <T extends CommonStudygroupDto>
    void commonStudygroupDtoToEntitySetter(T studygroupDto, Studygroup studygroup) {
        studygroup.setStudyName(studygroupDto.getStudyName());
        studygroup.setDaysOfWeek(studygroupDto.getDaysOfWeek().toString());
        studygroup.setDate(
                new DateRange(
                        studygroupDto.getStudyPeriodStart(),
                        studygroupDto.getStudyPeriodEnd()
                )
        );
        studygroup.setTime(
                new TimeRange(
                        studygroupDto.getStudyTimeStart(),
                        studygroupDto.getStudyTimeEnd()
                )
        );
        studygroup.setIntroduction(studygroupDto.getIntroduction());
        studygroup.setMemberCountMin(studygroupDto.getMemberCountMin());
        studygroup.setMemberCountMax(studygroupDto.getMemberCountMax());
        studygroup.setPlatform(studygroupDto.getPlatform());

        studygroup.setSearchTags(
                TagFormatConverter.mapToList(
                        studygroupDto.getTags(),
                        studygroup
                )
        );
    }

    /**
     * 스터디 조회 시, Studygroup 객체를 ResponseDto 로 매핑
     * @param studygroup
     * @return
     * @throws Exception
     */
    default StudygroupResponseDto StudygroupToStudygroupResponseDto(Studygroup studygroup, String nickName){
        HashMap<String, String> tags = new HashMap<>();
        studygroup.getSearchTags()
                .forEach(st -> tags.put(st.getTagKey(), st.getTagValue()));

        String[] dayString = studygroup.getDaysOfWeek()
                .replace('[', ' ')
                .replace(']', ' ')
                .replaceAll("\\p{Z}", "")
                .split(",");

        List<String> daysOfWeek =  new ArrayList<>();
        for(String day : dayString) daysOfWeek.add(day);

        StudygroupResponseDto responseDto = new StudygroupResponseDto();
        responseDto.setId(studygroup.getId());
        responseDto.setStudyName(studygroup.getStudyName());
        responseDto.setStudyPeriodStart(studygroup.getDate().getStudyPeriodStart());
        responseDto.setStudyPeriodEnd(studygroup.getDate().getStudyPeriodEnd());
        responseDto.setDaysOfWeek(daysOfWeek);
        responseDto.setStudyTimeStart(studygroup.getTime().getStudyTimeStart());
        responseDto.setStudyTimeEnd(studygroup.getTime().getStudyTimeEnd());
        responseDto.setMemberCountMin(studygroup.getMemberCountMin());
        responseDto.setMemberCountMax(studygroup.getMemberCountMax());
        responseDto.setMemberCountCurrent(studygroup.getMemberCountCurrent());
        responseDto.setPlatform(studygroup.getPlatform());
        responseDto.setIntroduction(studygroup.getIntroduction());
        responseDto.setIsRecruited((studygroup.getIsRecruited()));

        responseDto.setTags(
                TagFormatConverter.listToMap(studygroup.getSearchTags())
        );
        responseDto.setLeaderNickName(studygroup.getLeaderMember().getNickName());
        responseDto.setLeader(studygroup.getLeaderMember().getNickName().equals(nickName));
        return responseDto;
    }

    /**
     * 스터디 수정 시, 스터디 객체로 매핑
     * @param studygroupDto
     * @return
     * @throws Exception
     */
    default Studygroup StudygroupDtoPatchToStudygroup(Long studyGroupId, StudygroupDto.Patch studygroupDto){
        Studygroup studygroup = new Studygroup();
        studygroup.setId(studyGroupId);
        commonStudygroupDtoToEntitySetter(studygroupDto, studygroup);

        return studygroup;
    }

    /**
     * 스터디 모집 상태 Response Dto
     * @param status
     * @return
     */
    default StudygroupResponseDto.Status statusDto(boolean status) {
        StudygroupResponseDto.Status statusDto = new StudygroupResponseDto.Status();
        statusDto.setStatus(status);
        return statusDto;
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
        dtoList.setTagValues(
                studygroup.getSearchTags()
                        .stream()
                        .map(SearchTag::getTagValue)
                        .collect(Collectors.toList())
        );
        return dtoList;
    }

    /**
     * 스터디 리스트 조회
     * @param studygroups
     * @return
     */
    default List<StudygroupResponseDto.DtoList> StudygroupListToStudygroupResponseDtoList(List<Studygroup> studygroups){
        return studygroups.stream()
                    .map(this::StudygroupsToStudygroupResponseDtoList)
                    .collect(Collectors.toList()
                );
    }
}
