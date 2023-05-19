import axios from "axios";
import { useRecoilValue } from "recoil";
import { LogInState } from "../recoil/atoms/LogInState";
import tokenRequestApi from "./TokenRequestApi";

// ====================== 개인이 속한 스터디 리스트 조회 (GET) ===========================
export interface StudyGroupListDto {
  id: number;
  title: string;
  tagValue: string[];
}

export const getStudyGroupList = async () => {
  try {
    const response = await tokenRequestApi.get<StudyGroupListDto[]>("/studygroup/myList?approved=false"
    );
    const data = response.data;
    return data;
  } catch (error) {
    console.error("스터디 그룹 리스트를 불러오는데 실패했습니다.", error);
    throw new Error("스터디 그룹 리스트를 불러오는데 실패했습니다.");
  }
}
// ====================== 스터디 그룹 정보 조회 (GET) ===========================
// TODO : StudyGroup의 정보를 조회할 때 데이터 타입 정의
export interface StudyInfoDto {
  id: number;
  studyName: string;
  studyPeriodStart: string;
  studyPeriodEnd: string;
  daysOfWeek: string[];
  studyTimeStart: string;
  studyTimeEnd: string;
  memberCountMin: number;
  memberCountMax: number;
  memberCountCurrent: number;
  platform: string;
  introduction: string;
  isRecruited: boolean;
  tags: {
    [key: string]: string;
  };
  leader: {
    id: number;
    nickName: string;
  };
}

// TODO : StudyGroup의 정보를 조회하는 코드
export async function getStudyGroupInfo(id: number) {
  const response = await axios.get<StudyInfoDto>(
    `${import.meta.env.VITE_APP_API_URL}/studygroup/${id}`
  );
  return response.data;
}

// ====================== 스터디 그룹 수정 (PATCH) ===========================
// TODO : StudyGroup의 정보를 조회할 때 데이터 타입 정의
export interface StudyGroupUpdateDto {
  id: number;
  studyName: string;
  studyPeriodStart: string;
  studyPeriodEnd: string;
  daysOfWeek: number[];
  studyTimeStart: string;
  studyTimeEnd: string;
  memberCountMin: number;
  memberCountMax: number;
  platform: string;
  introduction: string;
  tags: {
    [key: string]: string;
  };
}

// TODO : StudyGroup의 정보를 수정하는 코드
export async function updateStudyGroupInfo(data: StudyGroupUpdateDto) {
  const isLoggedIn = useRecoilValue(LogInState);
  if (!isLoggedIn) throw new Error("로그인 상태를 확인해주세요");

  try {
    const response = await tokenRequestApi.patch("/studygroup", data);
    console.log("성공적으로 스터디 정보를 업데이트 했습니다", response.data);
  } catch (error) {
    console.error("스터디 정보를 업데이트 하는데 실패했습니다", error);
  }
}

// ====================== 스터디 그룹 삭제 (DELETE) ===========================
// TODO : StudyGroup의 정보를 삭제하는 코드
export async function deleteStudyGroupInfo(id: number) {
  const isLoggedIn = useRecoilValue(LogInState);
  if (!isLoggedIn) throw new Error("로그인 상태를 확인해주세요");

  try {
    const response = await tokenRequestApi.delete(`/studygroup/${id}`);
    console.log("스터디가 삭제되었습니다.", response);
  } catch (error) {
    console.error(
      "스터디 정보를 삭제하는데 실패했습니다. 권한을 확인하세요",
      error
    );
  }
}

// ====================== 스터디 그룹 모집 상태 수정 (UPDATE) ===========================
// TODO : StudyGroup의 모집상태를 수정할 때, 서버로 보내는 데이터 양식
interface StudyGroupRecruitmentStatusUpdateDto {
  state: boolean;
}

// TODO : StudyGroup의 모집 상태를 수정
export async function updateStudyGroupRecruitmentStatus(
  id: number,
  data: StudyGroupRecruitmentStatusUpdateDto
) {
  const isLoggedIn = useRecoilValue(LogInState);
  if (!isLoggedIn) throw new Error("로그인 상태를 확인해주세요");

  try {
    const response = await tokenRequestApi.patch(`/studygroup/${id}`, data);
    console.log("스터디 모집 상태를 최신화하는데 성공했습니다", response);
  } catch {
    console.error("스터디 모집 상태를 최신화하는데 실패했습니다", Error);
  }
}

// ====================== 스터디 그룹장의 가입 승인/거절/강제 탈퇴 기능 ===========================
// TODO : 스터디 그룹장이 가입을 승인/거절할 때 + 강제 탈퇴 시킬 때, 서버로 보내는 데이터 양식
interface StudyGroupMemberApprovalDto {
  nickname: string;
}

// TODO : StudyGroup으로의 가입을 승인하는 코드
export async function approveStudyGroupApplication(
  id: number,
  data: StudyGroupMemberApprovalDto
) {
  const isLoggedIn = useRecoilValue(LogInState);
  if (!isLoggedIn) throw new Error("로그인 상태를 확인해주세요");

  try {
    const response = await tokenRequestApi.post(
      `/studygroup/${id}/candidate`,
      data
    );
    console.log("해당 회원의 가입을 허가합니다", response);
  } catch (error) {
    console.error("허가 요청이 실패했습니다", error);
  }
}

// TODO : StudyGroup으로의 가입을 거절하는 코드
export async function rejectStudyGroupApplication(
  id: number,
  //accessToken: string | null,
  data: StudyGroupMemberApprovalDto
) {
  //if (!accessToken) throw new Error("Access token is not defined.");

  const config = {
    data,
  };

  try {
    const response = await tokenRequestApi.delete(
      `/studygroup/${id}/candidate`,
      config
    );
    console.log("가입 거절", response);
  } catch (error) {
    console.error("예기치 못한 오류로 요청을 처리할 수 없습니다", error);
  }
}

// TODO : StudyGroup에서 강제 퇴출시키는 코드
export async function forceExitStudyGroup(
  id: number,
  data: StudyGroupMemberApprovalDto
) {
  const config = {
    data,
  };

  try {
    const response = await tokenRequestApi.delete(
      `/studygroup/${id}/kick`,
      config
    );
    console.log("강제 탈퇴에 성공했습니다", response);
  } catch (error) {
    console.error("강제탈퇴에 실패했습니다. 권한을 확인하세요", error);
  }
}

// ====================== 스터디원 가입 신청 철회 ===========================
// TODO : StudyGroup의 가입 신청을 철회하는 코드
export async function cancelStudyGroupApplication(id: number) {
  const isLoggedIn = useRecoilValue(LogInState);
  if (!isLoggedIn) throw new Error("Access token is not defined.");

  try {
    const response = await tokenRequestApi.delete(`/studygroup/${id}/join`);
    console.log("해당 그룹에 가입신청을 철회합니다", response);
  } catch (error) {
    console.error("가입 신청 철회에 실패했습니다", error);
  }
}

// ====================== 회원의 가입 대기 리스트 ===========================
// TODO 회원이 스터디에 가입하기 위해 대기하는 리스트를 조회할 때, 서버에서 받은 양식에 대한 타입 정의
interface StudyGroupMemberWaitingListDto {
  nickName: [string];
}

// TODO 회원이 스터디에 가입하기 위해 대기하는 리스트를 조회하는 코드
export async function getStudyGroupMemberWaitingList(id: number) {
  const isLoggedIn = useRecoilValue(LogInState);
  if (!isLoggedIn) throw new Error("로그인 상태를 확인해주세요");

  try {
    const response = await tokenRequestApi.get<StudyGroupMemberWaitingListDto>(
      `/studygroup/${id}/member?join-false`
    );
    console.log("성공적으로 대기 리스트를 호출했습니다", response);
    return response.data;
  } catch (error) {
    console.error(
      "예기치 못한 오류로 인해 데이터를 불러오는데 실패했습니다",
      error
    );
  }
}

// ====================== 회원 리스트 ===========================
// TODO 스터디 그룹에 가입된 회원 리스트
interface StudyGroupMemberListDto {
  nickName: [string];
}

// TODO : StudyGroup에 가입된 멤버 리스트
export async function getStudyGroupMemberList(id: number) {
  const isLoggedIn = useRecoilValue(LogInState);
  if (!isLoggedIn) throw new Error("Access token is not defined.");
  try {
    const response = await tokenRequestApi.get<StudyGroupMemberListDto>(
      `/studygroup/${id}/member`
    );
    console.log("성공적으로 멤버 목록을 불러왔습니다", response);
    return response.data;
  } catch (error) {
    console.error("멤버 목록을 불러오는데 실패했습니다", error);
  }
}
