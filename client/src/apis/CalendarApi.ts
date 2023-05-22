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
    console.log(myStudyGroups);

    // 2. 조회 데이터의 id 추출
    const studyGroupIds: number[] = [];
    // members 배열에서 스터디 그룹의 ID 추출
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
        const event: Event = {
          id: studyGroupInfo.id.toString(),
          title: studyGroupInfo.studyName,
          // daysOfWeek?: studyGroupInfo.studyDays,
          startTime: `${studyGroupInfo.studyTimeStart}:00`,
          endTime: `${studyGroupInfo.studyTimeEnd}:00`,
          startRecur: studyGroupInfo.studyPeriodStart,
          endRecur: studyGroupInfo.studyPeriodEnd,
          description: studyGroupInfo.introduction,
          overlap: true,
        };
        console.log(event);
        return event;
      }
    );

    // 5. fullCalendar 이벤트 배열 반환
    return events;
  }
