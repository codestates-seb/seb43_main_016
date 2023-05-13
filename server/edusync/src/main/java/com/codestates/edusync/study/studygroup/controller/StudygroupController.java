package com.codestates.edusync.study.studygroup.controller;

import com.codestates.edusync.dto.MultiResponseDto;
import com.codestates.edusync.study.studygroup.dto.StudygroupDto;
import com.codestates.edusync.study.studygroup.dto.StudygroupResponseDto;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import com.codestates.edusync.study.studygroup.mapper.StudygroupMapper;
import com.codestates.edusync.study.studygroup.service.StudygroupService;
import com.codestates.edusync.util.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
public class StudygroupController {
    private static final String STUDYGROUP_DEFAULT_URI = "/studygroup";
    private final StudygroupMapper studygroupMapper;
    private final StudygroupService studygroupService;

    /**
     * 스터디 모집 & 등록
     * @param postDto
     * @return
     */
    @PostMapping(STUDYGROUP_DEFAULT_URI)
    public ResponseEntity postStudygroup(@Valid @RequestBody StudygroupDto.Post postDto) {

        Studygroup studygroup = studygroupMapper.StudygroupDtoPostToStudygroup(postDto);
        studygroup = studygroupService.createStudygruop(studygroup);
        URI location = UriCreator.createUri(STUDYGROUP_DEFAULT_URI, studygroup.getId());

        return ResponseEntity.created(location).build();
    }

    /**
     * 스터디 모집 & 수정
     * @param patchDto
     * @return
     * @throws Exception
     */
    @PatchMapping(STUDYGROUP_DEFAULT_URI)
    public ResponseEntity patchStudygroup(@Valid @RequestBody StudygroupDto.Patch patchDto) {

        Studygroup studygroup = studygroupMapper.StudygroupDtoPatchToStudygroup(patchDto);
        studygroup = studygroupService.updateStudygroup(studygroup);

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
    @PatchMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}")
    public ResponseEntity patchStudygroupStatus(@PathVariable("studygroup-id") @Positive Long studygroupId) {

        studygroupService.updateStatusStudygroup(studygroupId);

        URI location = UriCreator.createUri(STUDYGROUP_DEFAULT_URI, studygroupId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    /**
     * 스터디 조회
     * @param studygroupId
     * @return
     */
    @GetMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}")
    public ResponseEntity getStudygroupDetail(@PathVariable("studygroup-id") @Positive Long studygroupId) {

        Studygroup studygroup = studygroupService.findStudygroup(studygroupId);
        StudygroupResponseDto responseDto = studygroupMapper.StudygroupToStudygroupResponseDto(studygroup);

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

        Page<Studygroup> studygroupPage = studygroupService.findStudygroups(page-1, size);
        List<Studygroup> studygroupList = studygroupPage.getContent();
        List<StudygroupResponseDto.DtoList> responseDtoList =
                studygroupMapper.StudygroupListToStudygroupResponseDtoList(studygroupList);

        return ResponseEntity.ok(new MultiResponseDto<>(responseDtoList,studygroupPage));
    }

    /**
     * 스터디 삭제
     * @param studygroupId
     * @return
     */
    @DeleteMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}")
    public ResponseEntity deleteStudygroup(@PathVariable("studygroup-id") @Positive Long studygroupId) {
        studygroupService.deleteStudygroup(studygroupId);
        return ResponseEntity.noContent().build();
    }
}
