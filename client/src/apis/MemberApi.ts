import { AxiosResponse } from "axios";
import tokenRequestApi from "./TokenRequestApi";
// * recoil에서 전역 LogInState를 가져와서 isLogin 변수에 할당
// =============== 유저정보 요청(GET) ===============

// TODO : 유저정보 get 요청 DTO 타입 정의
export interface MemberInfoResponseDto {
  uuid: string;
  email: string;
  profileImage: string;
  nickName: string;
  aboutMe: string;
  withMe: string;
  memberStatus: "MEMBER_ACTIVE" | "MEMBER_INACTIVE";
  roles: string[];
}

// TODO: 유저정보 get 요청하는 axios 코드
export const getMemberInfo = async (isLoggedIn: boolean) => {
  // * 로그인 상태가 아닌 경우 에러 발생
  if (!isLoggedIn) throw new Error("로그인 상태를 확인해주세요.");
  // tokenRequestApi를 사용하여 /members 엔드포인트로 GET 요청 전송
  const response = await tokenRequestApi.get<MemberInfoResponseDto>("/members");
  // 응답 데이터 추출
  const data = response.data;
  return data; // 데이터 반환
};

// =============== 유저 기본정보 업데이트(PATCH) ===============
export interface MemberUpdateDto {
  nickName: string;
  password: string;
}

// TODO: Member의 아이디와 비밀번호 수정 요청을 보내는 코드
export const updateMember = async (
  isLoggedIn: boolean,
  data: MemberUpdateDto
) => {
  // 액세스 토큰이 정의되지 않았을 경우 에러 발생
  if (!isLoggedIn) throw new Error("로그인 상태를 확인해주세요.");
  // 입력 데이터가 없을 경우 에러 발생
  if (!data) throw new Error("입력값을 확인해주세요.");

  // tokenRequestApi를 사용하여 /members 엔드포인트로 PATCH 요청 전송
  console.log("전송되는 데이터:", data);
  await tokenRequestApi.patch("/members", data);
};

// =============== 유저 프로필 사진 업데이트(PATCH) ===============
export interface MemberProfileUpdateImageDto {
  profileImage: string;
}
// TODO : Member의 프로필 사진의 수정 요청을 보내는 코드
export const updateMemberProfileImage = async (
  data: MemberProfileUpdateImageDto
) => {
  await tokenRequestApi.patch("/members/profile-image", data);
};

// =============== 유저 자기소개 / 선호하는 사람 업데이트(PATCH) ===============
export interface MemberDetailDto {
  aboutMe: string;
  withMe: string;
}
// TODO : Member의 자기소개, 선호하는 사람 수정 요청을 보내는 코드
export const updateMemberDetail = async (memberDetailDto: MemberDetailDto) => {
  await tokenRequestApi.patch("/members/detail", memberDetailDto);
};

// =============== 유저 탈퇴(DELETE) ===============
// TODO : Member의 회원 탈퇴 요청을 보내는 코드
export const deleteMember = async () => {
  await tokenRequestApi.delete("/members");
};

// =============== 유저 비밀번호 체크 ===============
export interface MemberPasswordCheckDto {
  password: string;
}
// TODO : Member의 비밀번호를 확인하는 코드
export const checkMemberPassword = async (
  memberPasswordCheckDto: MemberPasswordCheckDto
) => {
  try {
    const response: AxiosResponse = await tokenRequestApi.post(
      "/members/password",
      memberPasswordCheckDto
    );
    if (response.status <= 299) return true;
    else return false;
  } catch (error) {
    console.log(error);
  }
};

// TODO : Oauth2.0 로그인 이용자 검증
export interface Oauth2MemberCheckDto {
  provider: string;
}

export const checkOauth2Member = async (isLoggedIn: boolean) => {
  if (!isLoggedIn) throw new Error("로그인 상태를 확인해주세요.");
  const response = await tokenRequestApi.get<Oauth2MemberCheckDto>(
    "/members/provider"
  );
  const data = response.data;
  return data;
};

// TODO : (Advance) S3에 업로드된 사진의 URL을 받아오는 코드
