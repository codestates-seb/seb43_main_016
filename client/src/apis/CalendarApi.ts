import tokenRequestApi from "./TokenRequestApi";

import {
  StudyInfoDto,
  getStudyGroupInfo,
  getStudyGroupList,
} from "./StudyGroupApi";

// ====================== 개인이 속한 스터디의 스케줄을 가져오는 로직  ===========================
// 1. 개인이 속한 스터디 조회
// 2. 조회 데이터의 id 추출
// 3. id를 인자로 전달하여 각 스터디의 상세정보를 추출하고, 변수에 담기
// 4. 변수에 담은 스터디 정보를 fullCalendar 라이브러리에 맞게 맵핑
// 5. fullCalendar 라이브러리에 전달하여 이벤트 생성
export interface Event {
  id: string;
  title: string;
  daysOfWeek?: string[];
  startTime: string;
  endTime: string;
  startRecur: string;
  endRecur: string;
  description: string;
  overlap: boolean;
}

export const generateStudyEvents = async (
  isLoggedIn: boolean
): Promise<Event[]> => {
  // 1. 개인이 속한 스터디 조회
  const myStudyGroups = await getStudyGroupList();
  // 2. 조회 데이터의 id 추출
  const studyGroupIds: number[] = [];

  for (const member of myStudyGroups.data.members) {
    studyGroupIds.push(member.id);
  }

  // 3. id를 인자로 전달하여 각 스터디의 상세정보를 추출하고, 변수에 담기
  const studyGroupInfos: StudyInfoDto[] = [];
  for (const id of studyGroupIds) {
    const studyGroupInfo = await getStudyGroupInfo(id, isLoggedIn);
    studyGroupInfos.push(studyGroupInfo);
  }

  // 4. 변수에 담은 스터디 정보를 fullCalendar 라이브러리에 맞게 맵핑
  const events: Event[] = studyGroupInfos.map(
    (studyGroupInfo: StudyInfoDto) => {
      const mappedDaysOfWeek: string[] = studyGroupInfo.daysOfWeek.map(
        (day: string) => {
          switch (day) {
            case "월":
              return "1"; // "월" -> 1
            case "화":
              return "2"; // "화" -> 2
            case "수":
              return "3"; // "수" -> 3
            case "목":
              return "4"; // "목" -> 4
            case "금":
              return "5"; // "금" -> 5
            case "토":
              return "6"; // "토" -> 6
            case "일":
              return "0"; // "일" -> 0
            default:
              return "";
          }
        }
      );

      const event: Event = {
        id: studyGroupInfo.id.toString(),
        title: studyGroupInfo.studyName,
        daysOfWeek: mappedDaysOfWeek,
        startTime: `${studyGroupInfo.studyTimeStart}:00`,
        endTime: `${studyGroupInfo.studyTimeEnd}:00`,
        startRecur: studyGroupInfo.studyPeriodStart,
        endRecur: studyGroupInfo.studyPeriodEnd,
        description: studyGroupInfo.introduction,
        overlap: true,
      };
      return event;
    }
  );

  // 5. fullCalendar 이벤트 배열 반환
  return events;
};

// TODO : 개인 커스텀 일정을 등록
export interface CustomEventDto {
  title: string;
  studyTimeStart: string;
  studyTimeEnd: string;
  description: string;
  color: string;
}

export const generateCustomEvents = async (
  isLoggedIn: boolean,
  eventInfo: CustomEventDto
) => {
  if (!isLoggedIn) throw new Error("로그인이 필요합니다.");

  const timeSchedule: CustomEventDto = {
    title: eventInfo.title,
    studyTimeStart: eventInfo.studyTimeStart,
    studyTimeEnd: eventInfo.studyTimeEnd,
    description: eventInfo.description,
    color: eventInfo.color,
  };

  const response = await tokenRequestApi.post("/calendars/members", {
    timeSchedule,
  });
  console.log(timeSchedule);
  return response;
};

// TODO : 개인 커스텀 일정을 수정
export const updateCustomEvents = async (
  isLoggedIn: boolean,
  data: CustomEventDto,
  id: number
) => {
  if (!isLoggedIn) throw new Error("로그인이 필요합니다.");

  const response = await tokenRequestApi.patch(
    `/calendars/${id}/members`,
    data
  );
  return response;
};

// TODO : 개인 커스텀 일정 삭제
export const deleteCustomEvents = async (isLoggedIn: boolean, id: number) => {
  if (!isLoggedIn) throw new Error("로그인이 필요합니다.");

  const response = await tokenRequestApi.delete(`/calendars/${id}/members`);
  return response;
};

// TODO : 개인 커스텀 일정 상세 조회
export const getCustomEvents = async (isLoggedIn: boolean, id: number) => {
  if (!isLoggedIn) throw new Error("로그인이 필요합니다.");

  const response = await tokenRequestApi.get(`/calendars/${id}/members`);
  return response;
};
