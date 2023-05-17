package com.codestates.edusync.model.study.studygroupjoin.controller;

import com.codestates.edusync.model.common.utils.MemberUtils;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.studygroupjoin.service.StudygroupJoinService;
import com.codestates.edusync.model.study.studygroupjoin.dto.StudygroupJoinDto;
import com.codestates.edusync.model.study.studygroupjoin.entity.StudygroupJoin;
import com.codestates.edusync.model.study.studygroupjoin.mapper.StudygroupJoinMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private final StudygroupJoinMapper studygroupJoinmapper;
    private final MemberUtils memberUtils;

    /**
     * 스터디 멤버 리스트 및 가입 대기 리스트 조회
     * @param studygroupId
     * @return
     */
    @GetMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}/member")
    public ResponseEntity getStudygroupJoins(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                             @RequestParam("join") Boolean join,
                                             Authentication authentication) {

        Member loginMember = memberUtils.getLoggedIn(authentication);
        List<StudygroupJoin> studygroupJoinList;

        if (join)   studygroupJoinList = studygroupJoinService.getAllMemberList(studygroupId); // 멤버 리스트
        else        studygroupJoinList = studygroupJoinService.getAllCandidateList(studygroupId, loginMember); // 대기 리스트

        StudygroupJoinDto.Response studygroupJoinDtos =
                studygroupJoinmapper.studygroupJoinToStudygroupJoinDtos(studygroupJoinList);
        return ResponseEntity.ok(studygroupJoinDtos);
    }

    /**
     * 스터디 그룹에 가입 신청한다.
     * @param studygroupId
     * @return
     */
    @PostMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_JOIN_URL)
    public ResponseEntity postStudygroupJoin(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                             Authentication authentication) {
        Member loginMember = memberUtils.getLoggedIn(authentication);

        studygroupJoinService.createCandidate(studygroupId, loginMember);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 본인이 직접 가입 신청을 철회한다
     * @param studygroupId
     * @return
     */
    @DeleteMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_JOIN_URL)
    public ResponseEntity deleteStudygroupJoinCandidate(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                        Authentication authentication) {
        Member loginMember = memberUtils.getLoggedIn(authentication);

        studygroupJoinService.deleteCandidateSelf(studygroupId, loginMember);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 스터디 자진 탈퇴
     * @param studygroupId
     * @return
     */
    @DeleteMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}/member")
    public ResponseEntity deleteStudygroupJoin(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                               Authentication authentication) {
        Member loginMember = memberUtils.getLoggedIn(authentication);

        studygroupJoinService.deleteMemberSelf(studygroupId, loginMember);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 가입 승인한다. (스터디장)
     * @param studygroupId
     * @param studygroupJoinDto
     * @return
     */
    @PostMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_CANDIDATE_URL)
    public ResponseEntity postStudygroupJoinApprove(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                    @Valid @RequestBody StudygroupJoinDto.Dto studygroupJoinDto,
                                                    Authentication authentication) {
        Member loginMember = memberUtils.getLoggedIn(authentication);

        studygroupJoinService.approveCandidateByNickName(studygroupId, studygroupJoinDto.getNickName(), loginMember);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * 가입 거부한다. (스터디장)
     * @param studygroupId
     * @param studygroupJoinDto
     * @return
     */
    @DeleteMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_CANDIDATE_URL)
    public ResponseEntity deleteStudygroupJoinReject(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                     @Valid @RequestBody StudygroupJoinDto.Dto studygroupJoinDto,
                                                     Authentication authentication) {
        Member loginMember = memberUtils.getLoggedIn(authentication);

        studygroupJoinService.rejectCandidateByNickName(studygroupId, studygroupJoinDto.getNickName(), loginMember);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * 스터디 멤버 강퇴
     * @param studygroupId
     * @param studygroupJoinDto
     * @return
     */
    @DeleteMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}/kick")
    public ResponseEntity deleteStudygroupJoinKick(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                   @Valid @RequestBody StudygroupJoinDto.Dto studygroupJoinDto,
                                                   Authentication authentication) {
        Member loginMember = memberUtils.getLoggedIn(authentication);

        studygroupJoinService.kickOutMemberByNickName(studygroupId, studygroupJoinDto.getNickName(), loginMember);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
