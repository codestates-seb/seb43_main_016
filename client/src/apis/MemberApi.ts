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

  try {
    // tokenRequestApi를 사용하여 /members 엔드포인트로 GET 요청 전송
    const response = await tokenRequestApi.get<MemberInfoResponseDto>(
      "/members"
    );
    // 응답 데이터 추출
    const data = response.data;
    return data; // 데이터 반환
  } catch (error) {
    console.error("유저 정보를 불러오는데 실패했습니다.", error);
    throw new Error("유저 정보를 불러오는데 실패했습니다."); // 실패 시 에러 발생
  }
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

  try {
    // tokenRequestApi를 사용하여 /members 엔드포인트로 PATCH 요청 전송
    console.log("전송되는 데이터:", data);
    await tokenRequestApi.patch("/members", data);
  } catch (error) {
    console.error("유저정보를 업데이트 하는데 실패했습니다.", error);
    throw new Error("유저정보를 업데이트 하는데 실패했습니다."); // 실패 시 에러 발생
  }
};

// =============== 유저 프로필 사진 업데이트(PATCH) ===============
export interface MemberProfileUpdateImageDto {
  profileImage: string;
}
// TODO : Member의 프로필 사진의 수정 요청을 보내는 코드
export const updateMemberProfileImage = async (
  data: MemberProfileUpdateImageDto
) => {
  try {
    await tokenRequestApi.patch("/members/profile-image", data);
  } catch (error) {
    console.error("프로필 사진을 업로드하는데 실패했습니다.", error);
    throw new Error("프로필 사진을 업로드하는데 실패했습니다.");
  }
};

// =============== 유저 자기소개 / 선호하는 사람 업데이트(PATCH) ===============
export interface MemberDetailDto {
  aboutMe: string;
  withMe: string;
}
// TODO : Member의 자기소개, 선호하는 사람 수정 요청을 보내는 코드
export const updateMemberDetail = async (memberDetailDto: MemberDetailDto) => {
  try {
    await tokenRequestApi.patch("/members/detail", memberDetailDto);
  } catch (error) {
    console.error("상세정보를 업데이트하는데 실패했습니다.", error);
    throw new Error("상세정보를 업데이트하는데 실패했습니다.");
  }
};

// =============== 유저 탈퇴(DELETE) ===============
// TODO : Member의 회원 탈퇴 요청을 보내는 코드
export const deleteMember = async () => {
  try {
    await tokenRequestApi.delete("/members");
  } catch (error) {
    throw new Error("회원탈퇴에 실패했습니다.");
  }
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
    await tokenRequestApi.post("/members/password", memberPasswordCheckDto);
  } catch (error) {
    throw new Error("비밀번호가 일치하지 않습니다.");
  }
};

// TODO : (Advance) Member의 수정된 사진을 S3에 업로드하는 코드

// TODO : (Advance) S3에 업로드된 사진의 URL을 받아오는 코드
