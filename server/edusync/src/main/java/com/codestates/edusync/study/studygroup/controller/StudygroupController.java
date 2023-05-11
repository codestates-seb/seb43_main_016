package com.codestates.edusync.study.studygroup.controller;

import com.codestates.edusync.dto.MultiResponseDto;
import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.member.entity.Member;
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
    private final StudygroupMapper mapper;
    private final StudygroupService service;

    /**
     * 스터디 모집 & 등록
     * @param postDto
     * @return
     */
    @PostMapping(STUDYGROUP_DEFAULT_URI)
    public ResponseEntity postStudygroup(@Valid @RequestBody StudygroupDto.Post postDto) throws Exception{

        Studygroup studygroup = mapper.StudygroupDtoPostToStudygroup(postDto);
        studygroup = service.createStudygruop(studygroup);
        // FIXME: 2023-05-11 생성된 데이터를 전달할 지, 확인 필요
        //StudygroupResponseDto responseDto = mapper.StudygroupToStudygroupResponseDto(studygroup);
        //URI location = UriCreator.createUri(STUDYGROUP_DEFAULT_URI, responseDto.getId());
        URI location = UriCreator.createUri(STUDYGROUP_DEFAULT_URI, studygroup.getId());

        return ResponseEntity.created(location).build();
        //return ResponseEntity.created(location).body(responseDto);
    }

    /**
     * 스터디 모집 & 수정
     * @param patchDto
     * @return
     * @throws Exception
     */
    @PatchMapping(STUDYGROUP_DEFAULT_URI)
    public ResponseEntity patchStudygroup(@Valid @RequestBody StudygroupDto.Patch patchDto) throws Exception{

        Studygroup studygroup = mapper.StudygroupDtoPatchToStudygroup(patchDto);
        studygroup = service.updateStudygroup(studygroup);
        // FIXME: 2023-05-11 수정된 데이터를 전달할 지, 확인 필요
        //StudygroupResponseDto responseDto = mapper.StudygroupToStudygroupResponseDto(studygroup);

        URI location = UriCreator.createUri(STUDYGROUP_DEFAULT_URI, studygroup.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(headers, HttpStatus.OK);
        //return new ResponseEntity<>(responseDto, location, HttpStatus.OK);
    }

    /**
     * 스터디 모집 상태 수정(모집중 - 모집완료)
     * @param studygroupId
     * @return
     */
    @PatchMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}")
    public ResponseEntity patchStudygroupStatus(@PathVariable("studygroup-id") @Positive Long studygroupId) throws Exception{

        service.updateStatusStudygroup(studygroupId);

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
    public ResponseEntity getStudygroupDetail(@PathVariable("studygroup-id") @Positive Long studygroupId) throws Exception {

        Studygroup studygroup = service.findStudygroup(studygroupId);
        StudygroupResponseDto responseDto = mapper.StudygroupToStudygroupResponseDto(studygroup);

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
                                            @RequestParam("size") @Positive Integer size) throws Exception{

        Page<Studygroup> studygroupPage = service.findStudygroups(page-1, size);
        List<Studygroup> studygroupList = studygroupPage.getContent();
        List<StudygroupResponseDto.DtoList> responseDtoList =
                mapper.StudygroupListToStudygroupResponseDtoList(studygroupList);

        return ResponseEntity.ok(new MultiResponseDto<>(responseDtoList,studygroupPage));
    }

    /**
     * 스터디 삭제
     * @param studygroupId
     * @return
     */
    @DeleteMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}")
    public ResponseEntity deleteStudygroup(@PathVariable("studygroup-id") @Positive Long studygroupId) throws Exception{
        service.deleteStudygroup(studygroupId);
        return ResponseEntity.noContent().build();
    }

    // FIXME: 2023-05-10 끝나고 생각
//    /**
//     * 스터디 멤버 강퇴
//     * @param studygroupId
//     * @param classmateId
//     * @return
//     */
//    @DeleteMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}/classmate/{classmate-id}/kickout")
//    public ResponseEntity deleteStudygroupkick(@PathVariable("studygroup-id") @Positive Long studygroupId,
//                                           @PathVariable("classmate-id") @Positive Long classmateId) {
//
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//
//    /**
//     * 스터디 자진 탈퇴
//     * @param studygroupId
//     * @param classmateId
//     * @return
//     */
//    @DeleteMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}/classmate/{classmate-id}")
//    public ResponseEntity deleteStudygroup(@PathVariable("studygroup-id") @Positive Long studygroupId,
//                                           @PathVariable("classmate-id") @Positive Long classmateId) {
//
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
}
