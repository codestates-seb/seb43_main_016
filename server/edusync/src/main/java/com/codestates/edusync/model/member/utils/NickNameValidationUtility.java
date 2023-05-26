package com.codestates.edusync.model.member.utils;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class NickNameValidationUtility {

    public void checkValidNickName(String nickName) {
        Set<String> nickNameNotAllowed = new HashSet<>();

        // 영어
        nickNameNotAllowed.addAll(List.of(
                "admin", "address",
                "calendar", "category", "codestates",
                "EDUsync",
                "login",
                "name", "new", "nickname",
                "password",
                "study", "studygroup",
                "you",

                "닉네임",
                "등록",
                "로그인",
                "모집",
                "비밀번호",
                "수정", "스터디",
                "업로드", "에듀싱크", "운영자",
                "카테고리", "캘린더", "코드스테이츠",
                "회원가입"
        ));

        if( nickNameNotAllowed.contains(nickName) ) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NICKNAME_NOT_ALLOWED);
        }
    }
}
