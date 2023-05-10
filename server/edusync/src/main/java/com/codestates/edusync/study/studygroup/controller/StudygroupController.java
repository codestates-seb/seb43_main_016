package com.codestates.edusync.study.studygroup.controller;

import com.codestates.edusync.study.studygroup.dto.StudygroupDto;
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
    private final StudygroupService studygroupService;

    private static final String STUDYGROUP_DEFAULT_URI = "/studygroup";

    @PostMapping(STUDYGROUP_DEFAULT_URI)
    public ResponseEntity postStudygroup(@Valid @RequestBody StudygroupDto.Post postDto) {

        long studygroupId = 1L;     // fixme: 임시로 만들어둠

        URI location = UriCreator.createUri(STUDYGROUP_DEFAULT_URI, studygroupId);

        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    @PatchMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}")
    public ResponseEntity patchStudygroup(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                          @Valid @RequestBody StudygroupDto.Patch patchDto) {

        // TODO: 2023-05-08 작업 해야함 
        
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}/status")
    public ResponseEntity patchStudygroupStatus(@PathVariable("studygroup-id") @Positive Long studygroupId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}")
    public ResponseEntity getStudygroupDetail(@PathVariable("studygroup-id") @Positive Long studygroupId) {
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping(STUDYGROUP_DEFAULT_URI + "s")   // 복수형으로 만들어주기 위해서 ( 오타 아님 )
    public ResponseEntity getStudygroupPage(@RequestParam("page") @Positive Integer page,
                                            @RequestParam("size") @Positive Integer size) {
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @DeleteMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}")
    public ResponseEntity deleteStudygroup(@PathVariable("studygroup-id") @Positive Long studygroupId) {
        
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @DeleteMapping(STUDYGROUP_DEFAULT_URI + "/{studygroup-id}/classmate/{classmate-id}")
    public ResponseEntity deleteStudygroup(@PathVariable("studygroup-id") @Positive Long studygroupId,
                                           @PathVariable("classmate-id") @Positive Long classmateId) {

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
