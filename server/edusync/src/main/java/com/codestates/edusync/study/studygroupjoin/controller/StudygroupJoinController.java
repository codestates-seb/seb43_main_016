package com.codestates.edusync.study.studygroupjoin.controller;

import com.codestates.edusync.study.studygroupjoin.dto.StudygroupJoinDto;
import com.codestates.edusync.study.studygroupjoin.entity.StudygroupJoin;
import com.codestates.edusync.study.studygroupjoin.mapper.StudygroupJoinMapper;
import com.codestates.edusync.study.studygroupjoin.service.StudygroupJoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
public class StudygroupJoinController {
    private static final String DEFAULT_STUDYGROUP_URL = "/studygroup";
    private static final String DEFAULT_JOIN_URL = "/join";
    private static final String DEFAULT_CANDIDATE_URL = "/candidate";
    private final StudygroupJoinService studygroupJoinService;
    private final StudygroupJoinMapper mapper;

    /**
     * 스터디 가입 대기 리스트 및 가입된 멤버 리스트 조회
     * @param studygroupId
     * @return
     */
    // FIXME: 2023-05-12 응답줄 때 리스트로 변환 필요
    @GetMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_CANDIDATE_URL)
    public ResponseEntity getStudygroupJoins(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                             @RequestParam("candidate") Boolean candidate) {

        List<StudygroupJoin> studygroupJoinList;

        if (candidate) studygroupJoinList = studygroupJoinService.findStudygroupJoinsCandidateList(studygroupId); // 가입 대기
        else studygroupJoinList = studygroupJoinService.findStudygroupJoinsList(studygroupId); // 가입된 멤버

        List<StudygroupJoinDto.Response> studygroupJoinDtos = mapper.studygroupJoinToStudygroupJoinDtos(studygroupJoinList);
        return ResponseEntity.ok(studygroupJoinDtos);
    }

    /**
     * 스터디 그룹에 가입 신청한다.
     * @param studygroupId
     * @return
     */
    @PostMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_JOIN_URL)
    public ResponseEntity postClassmateOnCandidated(@PathVariable("studygroup-id") @Positive Long studygroupId) {
        studygroupJoinService.createStudygroupJoin(studygroupId);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 본인이 직접 가입 신청을 철회한다
     * @param studygroupId
     * @return
     */
    @DeleteMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_JOIN_URL)
    public ResponseEntity deleteCandidatedMember(@PathVariable("studygroup-id") @Positive Long studygroupId) {
        studygroupJoinService.deleteSelfStudygroupJoinCandidate(studygroupId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

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

    /**
     * 가입 승인한다. (스터디장)
     * @param studygroupId
     * @return
     */
    @PostMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_CANDIDATE_URL)
    public ResponseEntity approveCandidatedMember(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                  @Valid @RequestBody StudygroupJoinDto.Dto studygroupJoinDto) {
        studygroupJoinService.approveStudygroupJoin(studygroupId, studygroupJoinDto.getNickName());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * 가입 거부한다. (스터디장)
     * @param studygroupId
     * @return
     */
    @DeleteMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_CANDIDATE_URL)
    public ResponseEntity rejectCandidatedMember(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                 @Valid @RequestBody StudygroupJoinDto.Dto studygroupJoinDto) {
        studygroupJoinService.deleteStudygroupJoinCandidate(studygroupId, studygroupJoinDto.getNickName());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

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
}
