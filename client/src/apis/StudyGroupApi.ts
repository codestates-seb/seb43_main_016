import axios from "axios";

// ====================== 스터디 그룹 정보 조회 (GET) ===========================
// TODO : StudyGroup의 정보를 조회할 때 데이터 타입 정의
export interface StudyInfoDto {
  id: number;
  studyName: string;
  studyPeriodStart: string;
  studyPeriodEnd: string;
  daysOfWeek: string;
  studyTimeStart: string;
  studyTimeEnd: string;
  memberCountMin: number;
  memberCountMax: number;
  memberCountCurrent: number;
  platform: string;
  introduction: string;
  requited: boolean;
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
    `${import.meta.env.VITE_APP_API_URL}/studygroups/${id}`
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
export async function updateStudyGroupInfo(
  data: StudyGroupUpdateDto,
  accessToken: string | null
) {
  // * (accessToken === null)이 안되는 이유 : null이 아닌 undefined를 검사해야 하기 때문에!
  if (!accessToken) throw new Error("Access token is not defined.");

  const config = {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  };

  try {
    const response = await axios.patch(
      `${import.meta.env.VITE_APP_API_URL}/studygroup`,
      data,
      config
    );
    console.log("Study group information updated successfully:", response.data);
  } catch (error) {
    console.error("Error updating study group information:", error);
  }
}

// ====================== 스터디 그룹 삭제 (DELETE) ===========================
// TODO : StudyGroup의 정보를 삭제하는 코드
export async function deleteStudyGroupInfo(
  id: number,
  accessToken: string | null
) {
  if (!accessToken) throw new Error("Access token is not defined.");

  const config = {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  };

  try {
    const response = await axios.delete(
      `${import.meta.env.VITE_APP_API_URL}/studygroup/${id}`,
      config
    );
    console.log("Study group information deleted successfully:", response);
  } catch (error) {
    console.error("Error deleting study group information:", error);
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
  accessToken: string | null,
  data: StudyGroupRecruitmentStatusUpdateDto
) {
  if (!accessToken) throw new Error("Access token is not defined.");

  const config = {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
    data,
  };

  try {
    const response = await axios.patch(
      `${import.meta.env.VITE_APP_API_URL}/studygroup/${id}`,

      config
    );
    console.log(
      "Study group recruitment status updated successfully:",
      response
    );
  } catch {
    console.error("Error updating study group recruitment status:", Error);
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
  accessToken: string | null,
  data: StudyGroupMemberApprovalDto
) {
  if (!accessToken) throw new Error("Access token is not defined.");

  const config = {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
    data,
  };

  try {
    const response = await axios.post(
      `${import.meta.env.VITE_APP_API_URL}/studygroup/${id}/candidate`,
      config
    );
    console.log("Study group application approved successfully:", response);
  } catch (error) {
    console.error("Error approving study group application:", error);
  }
}

// TODO : StudyGroup으로의 가입을 거절하는 코드
export async function rejectStudyGroupApplication(
  id: number,
  accessToken: string | null,
  data: StudyGroupMemberApprovalDto
) {
  if (!accessToken) throw new Error("Access token is not defined.");

  const config = {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
    data,
  };

  try {
    const response = await axios.delete(
      `${import.meta.env.VITE_APP_API_URL}/studygroup/${id}/candidate`,
      config
    );
    console.log("Study group application rejected successfully:", response);
  } catch (error) {
    console.error("Error rejecting study group application:", error);
  }
}

// TODO : StudyGroup에서 강제 퇴출시키는 코드
export async function forceExitStudyGroup(
  id: number,
  accessToken: string | null,
  data: StudyGroupMemberApprovalDto
) {
  if (!accessToken) throw new Error("Access token is not defined.");

  const config = {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
    data,
  };

  try {
    const response = await axios.delete(
      `${import.meta.env.VITE_APP_API_URL}/studygroup/${id}/kick`,
      config
    );
    console.log("Study group member forced to exit successfully:", response);
  } catch (error) {
    console.error("Error forcing study group member to exit:", error);
  }
}

// ====================== 스터디원 가입 신청 철회 ===========================
// TODO : StudyGroup의 가입 신청을 철회하는 코드
export async function cancelStudyGroupApplication(
  id: number,
  accessToken: string | null
) {
  if (!accessToken) throw new Error("Access token is not defined.");

  const config = {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  };

  try {
    const response = await axios.delete(
      `${import.meta.env.VITE_APP_API_URL}/studygroup/${id}/join`,
      config
    );
    console.log("Study group application canceled successfully:", response);
  } catch (error) {
    console.error("Error canceling study group application:", error);
  }
}

// ====================== 회원의 가입 대기 리스트 ===========================
// TODO 회원이 스터디에 가입하기 위해 대기하는 리스트를 조회할 때, 서버에서 받은 양식에 대한 타입 정의
interface StudyGroupMemberWaitingListDto {
  nickName: [string];
}

// TODO 회원이 스터디에 가입하기 위해 대기하는 리스트를 조회하는 코드
export async function getStudyGroupMemberWaitingList(id: number, accessToken: string | null) {
  if (!accessToken) throw new Error("Access token is not defined.");

  const config = {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  };

  try {
    const response = await axios.get<StudyGroupMemberWaitingListDto>(
      `${import.meta.env.VITE_APP_API_URL}/studygroup/${id}/member?join-false`,
      config
    );
    console.log("Study group member waiting list retrieved successfully:", response);
    return response.data;
  } catch (error) {
    console.error("Error retrieving study group member waiting list:", error);
  }
}

// ====================== 회원 리스트 ===========================

// TODO 스터디 그룹에 가입된 회원 리스트
interface StudyGroupMemberListDto {
  nickName: [string];
}

// TODO : StudyGroup에 가입된 멤버 리스트
export async function getStudyGroupMemberList(id: number, accessToken: string | null) {
  if(!accessToken) throw new Error("Access token is not defined.");

  const config = {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  };

  try {
    const response = await axios.get<StudyGroupMemberListDto>(
      `${import.meta.env.VITE_APP_API_URL}/studygroup/${id}/member`,
      config
    );
    console.log("Study group member list retrieved successfully:", response);
    return response.data;
    } catch (error) {
    console.error("Error retrieving study group member list:", error);
  }
}