package com.codestates.edusync.study.postcomment.controller;

import com.codestates.edusync.study.postcomment.dto.StudygroupPostCommentDto;
import com.codestates.edusync.study.postcomment.entity.StudygroupPostComment;
import com.codestates.edusync.study.postcomment.service.StudygroupPostCommentService;
import com.codestates.edusync.study.studygroup.mapper.StudygroupMapper;
import com.codestates.edusync.util.UriCreator;
import lombok.RequiredArgsConstructor;
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
public class StudygroupPostCommentController {
    private final StudygroupPostCommentService studygroupPostCommentService;
    private final StudygroupMapper mapper;

    private static final String DEFAULT_STUDYGROUP_URL = "/studygroup";
    private static final String DEFAULT_STUDYGROUP_POST_COMMENT_URL = "/comment";

    /**
     * 댓글을 등록한다.
     * @param studygroupId
     * @param postDto
     * @return
     */
    @PostMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_STUDYGROUP_POST_COMMENT_URL)
    public ResponseEntity postStudygroupPostComment(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                    @Valid @RequestBody StudygroupPostCommentDto.Post postDto) {
        StudygroupPostComment createdStudygroupPostComment =
                studygroupPostCommentService.createStudygroupPostComment(
                        studygroupId,
                        mapper.studygroupPostCommentPostDtoToStudygroupPostComment(postDto)
                );

        URI location = UriCreator.createUri(
                UriCreator.createUri(DEFAULT_STUDYGROUP_URL, studygroupId) +
                        DEFAULT_STUDYGROUP_POST_COMMENT_URL, createdStudygroupPostComment.getId());

        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    /**
     * 댓글을 수정한다.
     * @param studygroupId
     * @param commentId
     * @param patchDto
     * @return
     */
    @PatchMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_STUDYGROUP_POST_COMMENT_URL + "/{comment-id}")
    public ResponseEntity patchStudygroupPostComment(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                     @PathVariable("comment-id") @Positive Long commentId,
                                                     @Valid @RequestBody StudygroupPostCommentDto.Patch patchDto) {
        StudygroupPostComment updatedStudygroupPostComment =
                studygroupPostCommentService.updateStudygroupPostComment(
                        studygroupId, commentId,
                        mapper.studygroupPostCommentPatchDtoToStudygroupPostComment(patchDto)
                );

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 댓글들을 전부 조회한다
     * @param studygroupId
     * @return
     */
    @GetMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_STUDYGROUP_POST_COMMENT_URL + "s")
    public ResponseEntity getStudygroupPostComment(@PathVariable("studygroup-id") @Positive Long studygroupId) {
        List<StudygroupPostComment> findComments = studygroupPostCommentService.getAllStudygroupPostComments(studygroupId);

        return new ResponseEntity<>(
                mapper.studygroupPostCommentToStudygroupPostCommentResponseDtos(findComments),
                HttpStatus.OK
        );
    }

    /**
     * 댓글을 삭제한다
     * @param studygroupId
     * @param commentId
     * @return
     */
    @DeleteMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}" + DEFAULT_STUDYGROUP_POST_COMMENT_URL + "/{comment-id}")
    public ResponseEntity deleteStudygroupPostComment(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                                      @PathVariable("comment-id") @Positive Long commentId) {

        studygroupPostCommentService.deleteStudygroupPostComment(studygroupId, commentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 스터디 모집글에 있는 댓글들을 전부 삭제한다
     * @param studygroupId
     * @return
     */
    @DeleteMapping(DEFAULT_STUDYGROUP_URL + "/{studygroup-id}/all")
    public ResponseEntity deleteAllStudygroupPostComment(@PathVariable("studygroup-id") @Positive Long studygroupId) {

        studygroupPostCommentService.deleteAllStudygroupPostCommentByStudygroupId(studygroupId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
