package com.codestates.edusync.study.studygroupjoin.controller;

import com.codestates.edusync.study.studygroupjoin.service.StudygroupJoinService;
import com.codestates.edusync.util.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.net.URI;

@RequiredArgsConstructor
@Validated
@RestController
public class StudygroupJoinController {
    private final StudygroupJoinService studygroupJoinService;
    private static final String DEFAULT_STUDYGROUP_URL = "/studygroup";
    private static final String DEFAULT_CLASSMATE_URL = "/classmate";
    private static final String DEFAULT_CANDIDATE_URL = "/candidate";


    /**
     * 스터디 그룹에 가입 신청한다.
     * @param studygroupId
     * @return
     */
    @PostMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_CANDIDATE_URL)
    public ResponseEntity postClassmateOnCandidated(@PathVariable("studygroup-id") @Positive Long studygroupId) {
        long candidatedId = 1L;     // fixme: 임시로 만들어둠

        URI location = UriCreator.createUri(
                UriCreator.createUri(DEFAULT_STUDYGROUP_URL, studygroupId) +
                        DEFAULT_CANDIDATE_URL, candidatedId);  // FIXME: 2023-05-08 테스트 해봐야함 !!!

        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    /**
     * 가입 승인한다. (스터디장)
     * @param studygroupId
     * @param candidateId
     * @return
     */
    @PostMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_CANDIDATE_URL + "/{candidate-id}/approve")
    public ResponseEntity approveCandidatedMember(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                  @PathVariable("candidate-id") @Positive Long candidateId) {

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * 가입 거부한다. (스터디장)
     * @param studygroupId
     * @param candidateId
     * @return
     */
    @DeleteMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_CANDIDATE_URL + "/{candidate-id}/reject")
    public ResponseEntity rejectCandidatedMember(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                 @PathVariable("candidate-id") @Positive Long candidateId) {

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * 본인이 직접 가입 신청을 철회한다
     * @param studygroupId
     * @param candidateId
     * @return
     */
    @DeleteMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_CANDIDATE_URL + "/{candidate-id}")
    public ResponseEntity deleteCandidatedMember(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                 @PathVariable("candidate-id") @Positive Long candidateId) {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
