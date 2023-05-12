import { useState, useEffect } from "react";
import styled from "styled-components";
import { getStudyInfo } from "../API/StudyGroupApi";

// Props로 전달받은 id(스터디 그룹의 아이디)를 정의
interface ReadStudyInfoProps {
  id: number;
}

// res.data로 전달받은 스터디 그룹 정보의 타입을 정의
interface StudyResponseDto {
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
  tags: { [key: string]: string };
  leader: {
    id: number;
    nickName: string;
  };
}

const ReadStudyInfo = ({ id }: ReadStudyInfoProps) => {
  const [studyInfo, setStudyInfo] = useState<StudyResponseDto | null>(null);
  const accessToken = localStorage.getItem("accessToken");

  useEffect(() => {
    const fetchData = async () => {
      const data = await getStudyInfo(id, accessToken);
      setStudyInfo(data);
    };
    fetchData();
  }, []);

  // JSX를 리턴하는 코드
  return (
    <Wrapper>
      {/* 아직 서버에 연결되어 있지 않을 때 어떻게 테스트 할 수 있을까? */}
      {/* 여러 페이지에서 해당 코드에서 가져온 여러 데이터를 다양하게 렌더링하려면 어떻게 할 수 있을까? */}
      <div>{studyInfo?.id}</div>
    </Wrapper>
  );
};

export default ReadStudyInfo;

const Wrapper = styled.div``;