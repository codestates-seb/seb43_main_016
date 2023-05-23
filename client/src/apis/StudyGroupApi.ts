import axios from "axios";
import tokenRequestApi from "./TokenRequestApi";

// ====================== 마이 스터디 리스트 조회 (GET) ===========================
<<<<<<< HEAD
export interface StudyGroup {
=======

export interface StudyGroupListDto {
>>>>>>> 264d02e71507dc6e59e67c70e1d0436e9b1a2d8d
  id: number;
  title: string;
  tagValues: string[];
}

<<<<<<< HEAD
export interface StudyGroupListDto {
  leaders: StudyGroup[];
  members: StudyGroup[];
}

export const getStudyGroupList = async () => {
  const response = await tokenRequestApi.get<StudyGroupListDto>(
    `/studygroup/myList?approved=true`
  );
  return response;
=======
export const getStudyGroupList = async (): Promise<StudyGroupListDto[]> => {
  try {
    const response = await tokenRequestApi.get<StudyGroupListDto[]>(
      "/studygroup/myList?approved=false"
    );
    const data = response.data;
    return data;
  } catch (error) {
    console.log(error);
    throw new Error("스터디 그룹 리스트를 불러오는데 실패했습니다.");
  }
>>>>>>> 264d02e71507dc6e59e67c70e1d0436e9b1a2d8d
};

// ====================== 가입 대기중인 스터디 리스트 조회 (GET) ===========================
export interface WaitingStudyGroupItemDto {
  id: number;
  title: string;
}

export interface WaitingStudyGroupListDto {
  beStudys: WaitingStudyGroupItemDto[];
}

export const getWaitingStudyGroupList = async (): Promise<
  WaitingStudyGroupItemDto[]
> => {
  const response = await tokenRequestApi.get<WaitingStudyGroupItemDto[]>(
    `/studygroup/myList?approved=false`
  );
  const data = response.data;
  return data;
};

// ====================== 스터디원 가입 신청 철회 ===========================
// TODO : StudyGroup의 가입 신청을 철회하는 코드
export async function cancelStudyGroupApplication(
  id: number,
  isLoggedIn: boolean
) {
  if (!isLoggedIn) throw new Error("로그인 상태를 확인하세요");
  const response = await tokenRequestApi.delete(`/studygroup/${id}/join`);
  console.log("해당 그룹에 가입신청을 철회합니다", response);
}

// ====================== 스터디 그룹 정보 조회 (GET) ===========================
// TODO : StudyGroup의 정보를 조회할 때 데이터 타입 정의
export interface StudyTags {
  [key: string]: string[];
}

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
  tags: StudyTags;
  leaderNickName: string;
  leader: boolean;
}

// TODO : StudyGroup의 정보를 조회하는 코드
export async function getStudyGroupInfo(id: number, isLoggedIn: boolean) {
  if (!isLoggedIn) throw new Error("로그인 상태를 확인하세요");
  const response = await tokenRequestApi.get<StudyInfoDto>(`/studygroup/${id}`);
  const studyInfo = response.data;
  studyInfo.studyTimeStart = studyInfo.studyTimeStart
    .split("T")[1]
    .substring(0, 5);
  studyInfo.studyTimeEnd = studyInfo.studyTimeEnd.split("T")[1].substring(0, 5);
  return studyInfo;
}

// ====================== 스터디 그룹 수정 (PATCH) ===========================
// TODO : StudyGroup의 정보를 조회할 때 데이터 타입 정의
export interface StudyGroupUpdateDto {
  id: number;
  studyName: string;
  studyPeriodStart: string;
  studyPeriodEnd: string;
  daysOfWeek: string[];
  studyTimeStart: string;
  studyTimeEnd: string;
  memberCountMin: number;
  memberCountMax: number;
  platform: string;
  introduction: string;
  tags: StudyTags;
}

export async function updateStudyGroupInfo(
  data: StudyGroupUpdateDto,
  isLoggedIn: boolean,
  id: number
) {
  if (!isLoggedIn) throw new Error("로그인 상태를 확인해주세요");

  // studyPeriodStart 및 studyPeriodEnd 값을 ISO 8601 형식으로 변환
  const formattedData = {
    ...data,
    studyPeriodStart: `${data.studyPeriodStart}T${data.studyTimeStart}:00`,
    studyPeriodEnd: `${data.studyPeriodEnd}T${data.studyTimeEnd}:00`,
    studyTimeStart: `${data.studyPeriodStart}T${data.studyTimeStart}:00`,
    studyTimeEnd: `${data.studyPeriodEnd}T${data.studyTimeEnd}:00`,
  };

  console.log(formattedData);

  const response = await tokenRequestApi.patch(
    `/studygroup/${id}`,
    formattedData
  );
  console.log("성공적으로 스터디 정보를 업데이트 했습니다", response.data);
}

// ====================== 스터디 그룹 삭제 (DELETE) ===========================
// TODO : StudyGroup의 정보를 삭제하는 코드
export async function deleteStudyGroupInfo(id: number, isLoggedIn: boolean) {
  if (!isLoggedIn) throw new Error("로그인 상태를 확인해주세요");
  const response = await tokenRequestApi.delete(`/studygroup/${id}`);
  console.log("스터디가 삭제되었습니다.", response);
}

// ====================== 스터디 그룹 모집 상태 수정 (UPDATE) ===========================
// TODO : StudyGroup의 모집상태를 수정할 때, 서버로 보내는 데이터 양식
interface StudyGroupRecruitmentStatusUpdateDto {
  state: boolean;
}

// TODO : StudyGroup의 모집 상태를 수정
export async function updateStudyGroupRecruitmentStatus(
  id: number,
  data: StudyGroupRecruitmentStatusUpdateDto,
  isLoggedIn: boolean
) {
  if (!isLoggedIn) throw new Error("로그인 상태를 확인해주세요");
  const response = await tokenRequestApi.patch(`/studygroup/${id}`, data);
  console.log("스터디 모집 상태를 최신화하는데 성공했습니다", response);
}

// ====================== 스터디 그룹장의 가입 승인/거절/강제 탈퇴 기능 ===========================
// TODO : 스터디 그룹장이 가입을 승인/거절할 때 + 강제 탈퇴 시킬 때, 서버로 보내는 데이터 양식
export interface StudyGroupMemberApprovalDto {
  nickName: string;
}

// TODO: 스터디 그룹장이 가입을 승인/거절할 때 + 강제 탈퇴 시킬 때, 서버로 보내는 데이터 양식
export interface StudyGroupMemberApprovalDto {
  nickName: string;
}

// TODO: StudyGroup으로의 가입을 승인하는 코드
export async function approveStudyGroupApplication(
  id: number,
  nickname: string,
  isLoggedIn: boolean
) {
  if (!isLoggedIn) throw new Error("로그인 상태를 확인해주세요");
  const data: StudyGroupMemberApprovalDto = {
    nickName: nickname,
  };
  const response = await tokenRequestApi.post(
    `/studygroup/${id}/candidate`,
    data
  );
  console.log("해당 회원의 가입을 허가합니다", response);
}

// TODO: StudyGroup으로의 가입을 거절하는 코드
export async function rejectStudyGroupApplication(
  id: number,
  nickname: string
) {
  const data: StudyGroupMemberApprovalDto = {
    nickName: nickname,
  };
  const config = {
    data,
  };
  const response = await tokenRequestApi.delete(
    `/studygroup/${id}/candidate`,
    config
  );
  console.log("가입 거절", response);
}

// TODO : StudyGroup에서 강제 퇴출시키는 코드
export async function forceExitStudyGroup(
  id: number,
  data: StudyGroupMemberApprovalDto
) {
  const config = {
    data,
  };
  const response = await tokenRequestApi.delete(
    `/studygroup/${id}/kick`,
    config
  );
  console.log("강제 탈퇴에 성공했습니다", response);
}

// TODO : StudyGroup에서 특정 회원에게 스터디장의 권한을 위임하는 코드
export async function delegateStudyGroupLeader(
  id: number,
  data: StudyGroupMemberApprovalDto
) {
  const config = {
    data,
  };
  const response = await tokenRequestApi.patch(
    `/studygroup/${id}/privileges`,
    config
  );
  console.log("스터디장 권한 위임에 성공했습니다", response);
}

// ====================== 회원의 가입 대기 리스트 ===========================
// TODO 회원이 스터디에 가입하기 위해 대기하는 리스트를 조회할 때, 서버에서 받은 양식에 대한 타입 정의
export interface StudyGroupMemberWaitingListDto {
  nickName: [string];
}

// TODO 회원이 스터디에 가입하기 위해 대기하는 리스트를 조회하는 코드
export async function getStudyGroupMemberWaitingList(
  id: number,
  isLoggedIn: boolean
) {
  if (!isLoggedIn) throw new Error("로그인 상태를 확인해주세요");

  const response = await tokenRequestApi.get<StudyGroupMemberWaitingListDto>(
    `/studygroup/${id}/member?join=false`
  );
  console.log("성공적으로 대기 리스트를 호출했습니다", response);
  console.log(response.data);
  return response.data;
}
// ====================== 회원 리스트 ===========================
// TODO 스터디 그룹에 가입된 회원 리스트
export interface StudyGroupMemberListDto {
  nickName: [string];
}

// TODO : StudyGroup에 가입된 멤버 리스트
<<<<<<< HEAD
export async function getStudyGroupMemberList(id: number, isLoggedIn: boolean) {
  if (!isLoggedIn) throw new Error("Access token is not defined.");
  const response = await axios.get<StudyGroupMemberListDto>(
    `${import.meta.env.VITE_APP_API_URL}/studygroup/${id}/member?join=true`
  );
  if (response === undefined) return;
  return response.data as StudyGroupMemberListDto;
}

// ====================== 스터디에서 탈퇴 ===========================
// TODO : 스터디에서 탈퇴하는 코드
export async function exitStudyGroup(id: number, isLoggedIn: boolean) {
  if (!isLoggedIn) throw new Error("Access token is not defined.");
  const response = await tokenRequestApi.delete(`/studygroup/${id}/member`);
  console.log("스터디에서 탈퇴했습니다", response);
}

// ====================== 스터디 그룹 모집상태 변경 ===========================
// TODO : 스터디의 모집 상태를 변경하는 코드
interface StudyGroupRecruitmentStatusUpdateDto {
  state: boolean;
}

export async function changeStudyGroupRecruitmentStatus(
  id: number,
  isLoggedIn: boolean,
) {
  if (!isLoggedIn) throw new Error("Access token is not defined.");

  const config = {
    status : false,
  };

  const response = await tokenRequestApi.patch(
    `/studygroup/${id}/status`,
    config
  );
  console.log("스터디 모집 상태를 변경했습니다", response);
=======
export async function getStudyGroupMemberList (id: number, isLoggedIn : boolean) {
  if (!isLoggedIn)
    throw new Error("Access token is not defined.");
  try {
    const response = await axios.get<StudyGroupMemberListDto>(
      `${import.meta.env.VITE_APP_API_URL}/studygroup/${id}/member?join=true`
    );
    console.log("성공적으로 멤버 목록을 불러왔습니다", response);
    return response.data as StudyGroupMemberListDto;
  } catch (error) {
    console.error("멤버 목록을 불러오는데 실패했습니다", error);
  }
>>>>>>> 264d02e71507dc6e59e67c70e1d0436e9b1a2d8d
}
