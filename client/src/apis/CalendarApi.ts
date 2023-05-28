import tokenRequestApi from "./TokenRequestApi";
import { getStudyGroupList } from "./StudyGroupApi";
import axios, { AxiosResponse } from "axios";

// ====================== 개인이 속한 스터디의 스케줄을 가져오는 로직  ===========================
// 1. 개인이 속한 스터디 조회
// 2. 조회 데이터의 id 추출
// 3. id를 인자로 전달하여 각 스터디의 상세정보를 추출하고, 변수에 담기
// 4. 변수에 담은 스터디 정보를 fullCalendar 라이브러리에 맞게 맵핑
// 5. fullCalendar 라이브러리에 전달하여 이벤트 생성
export interface StudyEvent {
  id: string;
  title: string;
  daysOfWeek?: string[];
  start: string;
  end: string;
  overlap: boolean;
  color: string;
  divide: string;
}

export interface StudyEventDto {
  studyTimeStart: string;
  studyTimeEnd: string;
  id: number;
  title: string;
  color: string;
}

// TODO : 개인이 속한 스터디 리스트의 일정 전체 조회
export const getStudyGroupEvents = async (id: number) => {
  try {
    const response = await axios.get(
      `${import.meta.env.VITE_APP_API_URL}/calendars/studygroups/${id}`
    );
    const fetchedEvents = response.data;
    return fetchedEvents;
  } catch (error) {
    console.error("에러가 발생했습니다:", error);
    throw error;
  }
};

// TODO : getStudyGroupEvents을 활용해서, 개인이 속한 스터디의 스케줄을 가져오는 로직
export const generateStudyEvents = async (): Promise<StudyEvent[]> => {
  const myStudyGroups = await getStudyGroupList();
  const studyGroupIds: number[] = [];

  for (const member of myStudyGroups.data.members) {
    studyGroupIds.push(member.id);
  }

  const studyGroupInfo: StudyEventDto[] = [];

  try {
    for (const id of studyGroupIds) {
      const response = await getStudyGroupEvents(id);
      studyGroupInfo.push(...response);
    }
  } catch (error) {
    alert("스터디 정보를 불러오는데 실패했습니다");
  }

  const events: StudyEvent[] = studyGroupInfo.map(
    (studyGroup: StudyEventDto) => {
      const event: StudyEvent = {
        id: studyGroup.id.toString(),
        title: studyGroup.title,
        start: studyGroup.studyTimeStart,
        end: studyGroup.studyTimeEnd,
        color: studyGroup.color,
        overlap: true,
        divide: "studyGroup",
      };
      return event;
    }
  );
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
  return response;
};

// TODO : 개인 커스텀 일정 조회
export interface FullCalendarEvent {
  id: string;
  title: string;
  start: string;
  end: string;
  color?: string;
  divide: string;
}

export const getCustomEvent = async (isLoggedIn: boolean) => {
  if (!isLoggedIn) throw new Error("로그인이 필요합니다.");

  const response = await tokenRequestApi.get("/calendars/members");

  const customEventData: FullCalendarEvent[] = response.data.map(
    (data: any) => {
      const customEvent: FullCalendarEvent = {
        id: data.id.toString(),
        title: data.title,
        start: data.studyTimeStart,
        end: data.studyTimeEnd,
        color: data.color,
        divide: "customEvent",
      };
      return customEvent;
    }
  );
  return customEventData;
};

// TODO : 개인 커스텀 일정 삭제
export const deleteCustomEvents = async (isLoggedIn: boolean, id: number) => {
  if (!isLoggedIn) throw new Error("로그인이 필요합니다.");

  const response = await tokenRequestApi.delete(`/calendars/${id}/members`);
  return response;
};

// TODO : 개인 커스텀 일정 상세 조회
export interface Schedule {
  studyTimeStart: string;
  studyTimeEnd: string;
}

export interface EventData {
  calendarId: number;
  title: string;
  schedule: Schedule;
  description: string;
  overlap: boolean;
  color: string;
}

export const getCustomEvents = async (
  isLoggedIn: boolean,
  id: number
): Promise<EventData> => {
  if (!isLoggedIn) throw new Error("로그인이 필요합니다.");

  const response: AxiosResponse<EventData> = await tokenRequestApi.get(
    `/calendars/${id}/members`
  );
  return response.data;
};
