package com.codestates.edusync.model.study.studygroup.controller;

import com.codestates.edusync.model.common.dto.MultiResponseDto;
import com.codestates.edusync.model.common.utils.MemberUtils;
import com.codestates.edusync.model.common.utils.UriCreator;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.studygroup.dto.StudygroupDto;
import com.codestates.edusync.model.study.studygroup.dto.StudygroupResponseDto;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import com.codestates.edusync.model.study.studygroup.mapper.StudygroupMapper;
import com.codestates.edusync.model.study.studygroup.service.StudygroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Validated
@RestController
public class StudygroupController {
    private static final String STUDYGROUP_DEFAULT_URI = "/studygroup";
    private final StudygroupMapper studygroupMapper;
    private final StudygroupService studygroupService;
    private final MemberUtils memberUtils;

    /**
     * 스터디 모집 & 등록
     * @param postDto
     * @return
     */
    @PostMapping(STUDYGROUP_DEFAULT_URI)
    public ResponseEntity postStudygroup(Authentication authentication,
                                         @Valid @RequestBody StudygroupDto.Post postDto) {

        Studygroup studygroup = studygroupMapper.StudygroupDtoPostToStudygroup(postDto);
        studygroup = studygroupService.create(studygroup, authentication.getPrincipal().toString());
        URI location = UriCreator.createUri(STUDYGROUP_DEFAULT_URI, studygroup.getId());

        return ResponseEntity.created(location).build();
    }

    /**
     * 스터디 모집 & 수정
     * @param patchDto
     * @return
     * @throws Exception
     */
    @PatchMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}")
    public ResponseEntity patchStudygroup(Authentication authentication,
                                          @Positive @PathVariable("studygroup-id") Long studygroupId,
                                          @Valid @RequestBody StudygroupDto.Patch patchDto) {

        Studygroup studygroup = studygroupMapper.StudygroupDtoPatchToStudygroup(studygroupId, patchDto);
        studygroup = studygroupService.update(authentication.getName(), studygroup);

        URI location = UriCreator.createUri(STUDYGROUP_DEFAULT_URI, studygroup.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    /**
     * 스터디 모집 상태 수정(모집중 - 모집완료)
     * @param studygroupId
     * @return
     */
    @PatchMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}/status")
    public ResponseEntity patchStudygroupStatus(Authentication authentication,
                                                @PathVariable("studygroup-id") @Positive Long studygroupId) {

        boolean status = studygroupService.updateStatus(authentication.getName(), studygroupId);
        StudygroupResponseDto.Status statusDto = studygroupMapper.statusDto(status);

        URI location = UriCreator.createUri(STUDYGROUP_DEFAULT_URI, studygroupId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(statusDto, headers, HttpStatus.OK);
    }

    /**
     * 스터디 조회
     * @param studygroupId
     * @return
     */
    @GetMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}")
    public ResponseEntity getStudygroupDetail(Authentication authentication,
                                              @PathVariable("studygroup-id") @Positive Long studygroupId) {

        Member member = memberUtils.get(authentication.getPrincipal().toString());
        Studygroup studygroup = studygroupService.get(studygroupId);
        StudygroupResponseDto responseDto = studygroupMapper.StudygroupToStudygroupResponseDto(studygroup, member.getNickName());

        return ResponseEntity.ok(responseDto);
    }

    /**
     * 스터디 리스트
     * @param page
     * @param size
     * @return
     */
    @GetMapping(STUDYGROUP_DEFAULT_URI + "s")
    public ResponseEntity getStudygroupPage(@RequestParam("page") @Positive Integer page,
                                            @RequestParam("size") @Positive Integer size){

        Page<Studygroup> studygroupPage = studygroupService.getWithPaging(page-1, size);

        List<StudygroupResponseDto.DtoList> responseDtoList =
                studygroupMapper.StudygroupListToStudygroupResponseDtoList(studygroupPage.getContent());

        return ResponseEntity.ok(new MultiResponseDto<>(responseDtoList,studygroupPage));
    }

    /**
     * 스터디 삭제
     * @param studygroupId
     * @return
     */
    @DeleteMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}")
    public ResponseEntity deleteStudygroup(Authentication authentication,
                                           @PathVariable("studygroup-id") @Positive Long studygroupId) {
        studygroupService.delete(authentication.getName(), studygroupId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 스터디 리더 권한 이전
     * @param authentication
     * @param studygroupId
     * @return
     */
    @PatchMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}/privileges")
    public ResponseEntity patchStudygroupLeader(Authentication authentication,
                                                @PathVariable("studygroup-id") @Positive Long studygroupId,
                                                @RequestBody StudygroupDto.PatchLeader patchLeader) {

        studygroupService.patchLeader(authentication.getName(), studygroupId, patchLeader.getNickName());
        return ResponseEntity.ok().build();
    }
}
