import tokenRequestApi from "./TokenRequestApi";

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

export interface MemberUpdateDto {
  nickName: string;
  password: string;
}

export interface MemberProfileUpdateImageDto {
  profileImage: string;
}

export interface MemberDetailDto {
  aboutMe: string;
  withMe: string;
}

export interface MemberPasswordCheckDto {
  password: string;
}

// TODO : AccessToken 정의
// const accessToken = localStorage.getItem("accessToken");

// TODO : Member의 정보를 가져오는 코드
// url = http://ec2-43-202-20-65.ap-northeast-2.compute.amazonaws.com/members
// method = GET
// response = MemberInfoResponseDto
// headers = { Authorization: `Bearer ${accessToken}` }

export const getMemberInfo = async (/*accessToken: string | null*/) => {
  //if (!accessToken) throw new Error("Access token is not defined.");

  try {
    const response = await tokenRequestApi.get<MemberInfoResponseDto>(
      `/members`
    );
    const data = response.data;
    return data;
  } catch (error) {
    console.error(error);
    throw new Error("유저 정보를 불러오는데 실패했습니다.");
  }
};

// TODO : Member의 아이디와 비밀번호 수정 요청을 보내는 코드
// url = http://ec2-43-202-20-65.ap-northeast-2.compute.amazonaws.com/members
// method = PATCH
// headers = { Authorization: `Bearer ${accessToken}` }
export const updateMember = async (
  //accessToken: string | null,
  data: MemberUpdateDto
) => {
  //if (!accessToken) throw new Error("로그인 상태를 확인해주세요.");
  if (!data) throw new Error("입력값을 확인해주세요.");

  try {
    await tokenRequestApi.patch(`/members`);
  } catch (error) {
    throw new Error("유저정보를 업데이트 하는데 실패했습니다.");
  }
};

// TODO : Member의 프로필 사진의 수정 요청을 보내는 코드
// url = http://ec2-43-202-20-65.ap-northeast-2.compute.amazonaws.com/members/profile-image
// method = PATCH
// headers = { Authorization: `Bearer ${accessToken}` }
export const updateMemberProfileImage = async (
  //accessToken: string | null,
  data: MemberProfileUpdateImageDto
) => {
  const config = {
    data,
  };

  try {
    await tokenRequestApi.patch(`/members/profile-image`, { config });
  } catch (error) {
    throw new Error("프로필 사진을 업로드하는데 실패했습니다.");
  }
};

// TODO : Member의 자기소개, 선호하는 사람 수정 요청을 보내는 코드
// url = http://ec2-43-202-20-65.ap-northeast-2.compute.amazonaws.com/members/detail
// method = PATCH
// headers = { Authorization: `Bearer ${accessToken}` }

export const updateMemberDetail = async (
  //accessToken: string | null,
  memberDetailDto: MemberDetailDto
) => {
  try {
    await tokenRequestApi.patch(`/members/detail`, memberDetailDto);
  } catch (error) {
    throw new Error("상세정보를 업데이트하는데 실패했습니다.");
  }
};

// TODO : Member의 회원 탈퇴 요청을 보내는 코드
// url = http://ec2-43-202-20-65.ap-northeast-2.compute.amazonaws.com/members
// method = DELETE
// headers = { Authorization: `Bearer ${accessToken}` }

export const deleteMember = async (/*accessToken: string | null*/) => {
  try {
    await tokenRequestApi.delete(`/members`);
  } catch (error) {
    throw new Error("회원탈퇴에 실패했습니다.");
  }
};

// TODO : Member의 비밀번호를 확인하는 코드
// url = http://ec2-43-202-20-65.ap-northeast-2.compute.amazonaws.com/members/password
// method = POST
// headers = { Authorization: `Bearer ${accessToken}` }

export const checkMemberPassword = async (
  //accessToken: string | null,
  memberPasswordCheckDto: MemberPasswordCheckDto
) => {
  try {
    await tokenRequestApi.post(`/members/password`, memberPasswordCheckDto);
  } catch (error) {
    throw new Error("비밀번호가 일치하지 않습니다.");
  }
};

// TODO : (Advance) Member의 수정된 사진을 S3에 업로드하는 코드
//

// TODO : (Advance) S3에 업로드된 사진의 URL을 받아오는 코드
//
