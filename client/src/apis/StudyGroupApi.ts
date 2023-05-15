import axios from "axios";

interface Study {
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

export function getStudyInfo(id: number, accessToken: string) {
  throw new Error("Function not implemented.");
}
// TODO : AccessToken 정의
// const accessToken = localStorage.getItem("accessToken");

// TODO : StudyGroup의 정보를 조회하는 코드

// TODO :
