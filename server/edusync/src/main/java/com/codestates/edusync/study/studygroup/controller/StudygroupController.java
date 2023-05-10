package com.codestates.edusync.study.studygroup.controller;

import com.codestates.edusync.study.studygroup.dto.StudygroupDto;
import com.codestates.edusync.study.studygroup.entity.Studygroup;
import com.codestates.edusync.study.studygroup.mapper.StudygroupMapper;
import com.codestates.edusync.study.studygroup.service.StudygroupService;
import com.codestates.edusync.util.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

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
     * @param studygroupId
     * @param patchDto
     * @return
     */
    @PatchMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}")
    public ResponseEntity patchStudygroup(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                          @Valid @RequestBody StudygroupDto.Patch patchDto) {

        // TODO: 2023-05-08 작업 해야함 
        
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 스터디 모집 상태 수정(모집중 - 모집완료)
     * @param studygroupId
     * @return
     */
    @PatchMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}/status")
    public ResponseEntity patchStudygroupStatus(@PathVariable("studygroup-id") @Positive Long studygroupId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 스터디 조회
     * @param studygroupId
     * @return
     */
    @GetMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}")
    public ResponseEntity getStudygroupDetail(@PathVariable("studygroup-id") @Positive Long studygroupId) {
        
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 스터디 리스트
     * @param page
     * @param size
     * @return
     */
    @GetMapping(STUDYGROUP_DEFAULT_URI + "s")   // 복수형으로 만들어주기 위해서 ( 오타 아님 )
    public ResponseEntity getStudygroupPage(@RequestParam("page") @Positive Integer page,
                                            @RequestParam("size") @Positive Integer size) {
        
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 스터디 삭제
     * @param studygroupId
     * @return
     */
    @DeleteMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}")
    public ResponseEntity deleteStudygroup(@PathVariable("studygroup-id") @Positive Long studygroupId) {
        
        return new ResponseEntity(HttpStatus.NO_CONTENT);
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
