package com.codestates.edusync.model.study.studygroupjoin.controller;

import com.codestates.edusync.model.common.utils.MemberUtils;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.study.studygroup.dto.StudyListResponseDto;
import com.codestates.edusync.model.study.studygroup.dto.StudygroupResponseDto;
import com.codestates.edusync.model.study.studygroup.entity.Studygroup;
import com.codestates.edusync.model.study.studygroup.mapper.StudygroupMapper;
import com.codestates.edusync.model.study.studygroup.service.StudygroupService;
import com.codestates.edusync.model.study.studygroupjoin.dto.StudygroupJoinDto;
import com.codestates.edusync.model.study.studygroupjoin.entity.StudygroupJoin;
import com.codestates.edusync.model.study.studygroupjoin.mapper.StudygroupJoinMapper;
import com.codestates.edusync.model.study.studygroupjoin.service.StudygroupJoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Validated
@RestController
public class StudygroupJoinController {
    private static final String DEFAULT_STUDYGROUP_URL = "/studygroup";
    private static final String DEFAULT_JOIN_URL = "/join";
    private static final String DEFAULT_CANDIDATE_URL = "/candidate";
    private final StudygroupJoinService studygroupJoinService;
    private final StudygroupJoinMapper studygroupJoinmapper;
    private final StudygroupService studygroupService;
    private final StudygroupMapper studygroupMapper;
    private final MemberUtils memberUtils;

    /**
     * 스터디 멤버 리스트 및 가입 대기 리스트 조회
     * @param studygroupId
     * @param join
     * @param authentication
     * @return
     */
    @GetMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}/member")
    public ResponseEntity getStudygroupJoins(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                             @RequestParam("join") Boolean join,
                                             Authentication authentication) {
        List<StudygroupJoin> studygroupJoinList;

        if (join) {
            studygroupJoinList = studygroupJoinService.getAllMemberList(studygroupId); // 멤버 리스트
        }
        else {
            Member loginMember = memberUtils.getLoggedInWithAuthenticationCheck(authentication);

            studygroupJoinList = studygroupJoinService.getAllCandidateList
                    (studygroupId, loginMember.getEmail(), true); // 대기 리스트
        }

        StudygroupJoinDto.Response studygroupJoinDtos =
                studygroupJoinmapper.studygroupJoinToStudygroupJoinDtos(studygroupJoinList);
        return ResponseEntity.ok(studygroupJoinDtos);
    }

    /**
     * 스터디 그룹에 가입 신청한다.
     * @param studygroupId
     * @param authentication
     * @return
     */
    @PostMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_JOIN_URL)
    public ResponseEntity postStudygroupJoin(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                             Authentication authentication) {
        Member loginMember = memberUtils.getLoggedInWithAuthenticationCheck(authentication);

        studygroupJoinService.createCandidate(studygroupId, loginMember);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 본인이 직접 가입 신청을 철회한다
     * @param studygroupId
     * @param authentication
     * @return
     */
    @DeleteMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_JOIN_URL)
    public ResponseEntity deleteStudygroupJoinCandidate(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                        Authentication authentication) {
        Member loginMember = memberUtils.getLoggedInWithAuthenticationCheck(authentication);

        studygroupJoinService.deleteCandidateSelf(studygroupId, loginMember.getEmail());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 스터디 자진 탈퇴
     * @param studygroupId
     * @param authentication
     * @return
     */
    @DeleteMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}/member")
    public ResponseEntity deleteStudygroupJoin(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                               Authentication authentication) {
        Member loginMember = memberUtils.getLoggedInWithAuthenticationCheck(authentication);

        studygroupJoinService.deleteMemberSelf(studygroupId, loginMember.getEmail());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 가입 승인한다. (스터디장)
     * @param studygroupId
     * @param studygroupJoinDto
     * @param authentication
     * @return
     */
    @PostMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_CANDIDATE_URL)
    public ResponseEntity postStudygroupJoinApprove(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                    @Valid @RequestBody StudygroupJoinDto.Dto studygroupJoinDto,
                                                    Authentication authentication) {
        Member loginMember = memberUtils.getLoggedInWithAuthenticationCheck(authentication);

        studygroupJoinService.approveCandidateByNickName(
                studygroupId,
                studygroupJoinDto.getNickName(),
                loginMember.getEmail()
        );
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * 가입 거부한다. (스터디장)
     * @param studygroupId
     * @param studygroupJoinDto
     * @param authentication
     * @return
     */
    @DeleteMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_CANDIDATE_URL)
    public ResponseEntity deleteStudygroupJoinReject(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                     @Valid @RequestBody StudygroupJoinDto.Dto studygroupJoinDto,
                                                     Authentication authentication) {
        Member loginMember = memberUtils.getLoggedInWithAuthenticationCheck(authentication);

        studygroupJoinService.rejectCandidateByNickName(
                studygroupId,
                studygroupJoinDto.getNickName(),
                loginMember.getEmail()
        );
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * 스터디 멤버 강퇴
     * @param studygroupId
     * @param studygroupJoinDto
     * @param authentication
     * @return
     */
    @DeleteMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}/kick")
    public ResponseEntity deleteStudygroupJoinKick(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                   @Valid @RequestBody StudygroupJoinDto.Dto studygroupJoinDto,
                                                   Authentication authentication) {
        Member loginMember = memberUtils.getLoggedInWithAuthenticationCheck(authentication);

        studygroupJoinService.kickOutMemberByNickName(
                studygroupId,
                studygroupJoinDto.getNickName(),
                loginMember.getEmail()
        );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // TODO: 2023-05-19 리펙토링 필요
    /**
     * 사용자가 신청한 | 가입된 스터디 리스트 조회
     * @param authentication
     * @param approved
     * @return
     */
    @GetMapping(DEFAULT_STUDYGROUP_URL + "/myList")
    public ResponseEntity getMyStudygroupList(Authentication authentication,
                                              @RequestParam("approved") Boolean approved) {
        Member loginMember = memberUtils.getLoggedInWithAuthenticationCheck(authentication);

        List<Studygroup> studygroupList =
                studygroupJoinService.getMyStudygroupList(loginMember, approved);

        List<StudygroupResponseDto.DtoList> myStudygroupList =
                studygroupMapper.StudygroupListToStudygroupResponseDtoList(studygroupList);

        if (approved) {
            List<StudygroupResponseDto.DtoList> iLeaderStudygroupList = getLeaderStudygroupList(loginMember);
            return ResponseEntity.ok(new StudyListResponseDto.studyList<>(iLeaderStudygroupList, myStudygroupList));
        } else {
            return ResponseEntity.ok(new StudyListResponseDto.beStudyList<>(myStudygroupList));
        }
    }

    /**
     * 사용자가 가입된 스터디 리스트를 조회할 때, 본인이 리더인 스터디 그룹 리스트 조회
     * @param loginMember
     * @return
     */
    private List<StudygroupResponseDto.DtoList> getLeaderStudygroupList(Member loginMember) {
        List<Studygroup> leaderStudygroupList =
                studygroupService.getLeaderStudygroupList(loginMember);

        leaderStudygroupList = // Stream 내부에서 Service 다량 쿼리 발생, 로직 수정 필요
                leaderStudygroupList.stream().map(e -> studygroupService.get(e.getId())).collect(Collectors.toList());

        return studygroupMapper.StudygroupListToStudygroupResponseDtoList(leaderStudygroupList);
    }
}
