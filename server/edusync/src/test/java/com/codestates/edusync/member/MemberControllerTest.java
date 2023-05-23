package com.codestates.edusync.member;

import com.codestates.edusync.config.TestSecurityConfig;
import com.codestates.edusync.model.common.utils.MemberUtils;
import com.codestates.edusync.model.member.controller.MemberController;
import com.codestates.edusync.model.member.dto.MemberDto;
import com.codestates.edusync.model.member.dto.MemberJoinResponseDto;
import com.codestates.edusync.model.member.entity.Member;
import com.codestates.edusync.model.member.mapper.MemberMapper;
import com.codestates.edusync.model.member.service.MemberService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import(TestSecurityConfig.class) // JWT 인증과정을 무시하기 위해 사용
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper mapper;

    @Autowired
    private Gson gson;
    @MockBean
    private MemberUtils memberUtils;

    @Test
    @WithMockUser
    public void postMemberTest() throws Exception {
        // given
        MemberDto.Post post = new MemberDto.Post("test1@gmail.com", "firstPW111", "NickName1");
        String content = gson.toJson(post);

        MemberJoinResponseDto responseDto = makeMemberResponse(1L);

        given(mapper.memberPostToMember(Mockito.any(MemberDto.Post.class))).willReturn(new Member());

        Member mockResultMember = new Member();
        mockResultMember.setId(1L);
        given(memberService.createMember(Mockito.any(Member.class))).willReturn(mockResultMember);

        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(responseDto);

        ResultActions actions =
                mockMvc.perform(
                        post("/members")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        actions
                .andExpect(status().isCreated())
                .andDo(document("post-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("uuid").type(JsonFieldType.STRING).description("uuid"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("profileImage").type(JsonFieldType.STRING).description("프로필 이미지"),
                                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("aboutMe").type(JsonFieldType.STRING).description("자기소개"),
                                        fieldWithPath("withMe").type(JsonFieldType.STRING).description("함께하고 싶은 유형"),
                                        fieldWithPath("memberStatus").type(JsonFieldType.STRING).description("활동상태"),
                                        fieldWithPath("roles").type(JsonFieldType.ARRAY).description("권한")
                                )
                        )
                ));
    }

    @Test
    public void patchMemberTest() throws Exception {
        MemberDto.Patch patch = new MemberDto.Patch("changedNickName222", "changedPW222");
        String content = gson.toJson(patch);

        MemberJoinResponseDto responseDto = new MemberJoinResponseDto(makeUuid(),
                "test1@gmail.com",
                "https://avatars.githubusercontent.com/u/120456261?v=4",
                "changedNickName222",
                "",
                "",
                Member.MemberStatus.MEMBER_ACTIVE,
                List.of("user"));



        Authentication atc = new TestingAuthenticationToken("test1@gmail.com", null, "USER");

        given(mapper.memberPatchToMember(Mockito.any(MemberDto.Patch.class))).willReturn(new Member());

        given(memberService.updateMember(Mockito.any(Member.class),Mockito.anyString())).willReturn(new Member());

        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(responseDto);

        ResultActions actions =
                mockMvc.perform(
                        patch("/members")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJVU0VSIl0sIm1lbWJlckVtYWlsIjoidGVzdDFAZ21haWwuY29tIiwic3ViIjoidGVzdDFAZ21haWwuY29tIiwiaWF0IjoxNjgxODIxOTEwLCJleHAiOjE2ODE4MjM3MTB9.h_V93dhS-RhzqVdYuRkxHHIxYjG61LSn87a_8HtpBgM")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                                .with(authentication(atc))
                );

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(responseDto.getEmail()))
                .andExpect(jsonPath("$.profileImage").value(responseDto.getProfileImage()))
                .andExpect(jsonPath("$.nickName").value(patch.getNickName()))
                .andDo(document("patch-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT Access토큰")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임").optional(),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호").optional()
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("uuid").type(JsonFieldType.STRING).description("uuid"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("profileImage").type(JsonFieldType.STRING).description("프로필 이미지"),
                                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("aboutMe").type(JsonFieldType.STRING).description("자기소개"),
                                        fieldWithPath("withMe").type(JsonFieldType.STRING).description("함께하고 싶은 유형"),
                                        fieldWithPath("memberStatus").type(JsonFieldType.STRING).description("활동상태"),
                                        fieldWithPath("roles").type(JsonFieldType.ARRAY).description("권한")
                                )
                        )
                ));
    }

    @Test
    void getMemberTest() throws Exception {
        MemberJoinResponseDto response = makeMemberResponse(1L);


        Authentication atc = new TestingAuthenticationToken("test1@gmail.com", null, "USER");

        given(memberUtils.get(Mockito.anyString())).willReturn(new Member());
        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(response);


        mockMvc.perform(
                get("/members")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJVU0VSIl0sIm1lbWJlckVtYWlsIjoidGVzdDFAZ21haWwuY29tIiwic3ViIjoidGVzdDFAZ21haWwuY29tIiwiaWF0IjoxNjgxODIxOTEwLCJleHAiOjE2ODE4MjM3MTB9.h_V93dhS-RhzqVdYuRkxHHIxYjG61LSn87a_8HtpBgM")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(authentication(atc))
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.email").value(response.getEmail()),
                jsonPath("$.profileImage").value(response.getProfileImage()),
                jsonPath("$.nickName").value(response.getNickName())
        ).andDo(document("get-member",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                        headerWithName("Authorization").description("JWT Access토큰")
                ),
                responseFields(
                        List.of(
                                fieldWithPath("uuid").type(JsonFieldType.STRING).description("uuid"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("profileImage").type(JsonFieldType.STRING).description("프로필 이미지"),
                                fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임"),
                                fieldWithPath("aboutMe").type(JsonFieldType.STRING).description("자기소개"),
                                fieldWithPath("withMe").type(JsonFieldType.STRING).description("함께하고 싶은 유형"),
                                fieldWithPath("memberStatus").type(JsonFieldType.STRING).description("활동상태"),
                                fieldWithPath("roles").type(JsonFieldType.ARRAY).description("권한")
                        )
                )
        ));

    }

    @Test
    void deleteMemberTest() throws Exception {

        Authentication atc = new TestingAuthenticationToken("test1@gmail.com", null, "USER");

        doNothing().when(memberService).deleteMember(Mockito.anyString());

        mockMvc.perform(
                        delete("/members")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJVU0VSIl0sIm1lbWJlckVtYWlsIjoidGVzdDFAZ21haWwuY29tIiwic3ViIjoidGVzdDFAZ21haWwuY29tIiwiaWF0IjoxNjgxODIxOTEwLCJleHAiOjE2ODE4MjM3MTB9.h_V93dhS-RhzqVdYuRkxHHIxYjG61LSn87a_8HtpBgM") // JWT 토큰 값 설정
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(authentication(atc))
                ).andExpect(status().isNoContent())
                .andDo(document(
                        "delete-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT Access토큰")
                        )
                ));
    }

    public MemberJoinResponseDto makeMemberResponse(Long num) {
        MemberJoinResponseDto response = new MemberJoinResponseDto(makeUuid(),
                "test"+ num +"@gmail.com",
                "https://avatars.githubusercontent.com/u/120456261?v=4",
                "NickName" + num,
                "",
                "",
                Member.MemberStatus.MEMBER_ACTIVE,
                List.of("user"));
        return response;
    }

    public Member makeMember(Long num){
        Member member = new Member(num,
                "NickName" + num,
                "test"+ num +"@gmail.com",
                "https://avatars.githubusercontent.com/u/120456261?v=4");
        return member;
    }

    public String makeUuid(){
        return UUID.randomUUID().toString();
    }
}
