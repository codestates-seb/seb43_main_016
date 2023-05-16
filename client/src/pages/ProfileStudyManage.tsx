import { useState, useEffect } from "react";
<<<<<<< HEAD
import {
  deleteStudyGroupInfo,
  getStudyGroupInfo,
  StudyInfoDto,
} from "../apis/StudyGroupApi";
import styled from "styled-components";
import StudyInfoEditModal from "../components/modal/StudyInfoEditModal";
=======
import styled from "styled-components";
import { getStudyInfo } from "../apis/StudyGroupApi";
>>>>>>> 07650a94e7d83111bd34b6e152fb8d16e31faf84

// Props로 전달받은 id(스터디 그룹의 아이디)를 정의
interface ReadStudyInfoProps {
  id: number;
}

<<<<<<< HEAD
const ProfileStudyManage = ({ id }: ReadStudyInfoProps) => {
  const [studyInfo, setStudyInfo] = useState<StudyInfoDto | null>(null);
  const [isModalOpen, setModalOpen] = useState(false);

  const accessToken = localStorage.getItem("accessToken");

  // TODO : 최초 페이지 진입 시 스터디 정보를 조회하는 코드
  useEffect(() => {
    const fetchStudyGroupInfo = async () => {
      try {
        const studyInfo = await getStudyGroupInfo(id);
        setStudyInfo(studyInfo);
      } catch (error) {
        console.error("권한이 없습니다");
      }
    };

    fetchStudyGroupInfo();
  }, []);

  // TODO : 스터디 정보를 수정하는 코드
  const handleEditClick = () => {
    setModalOpen(true);
  };

  // TODO : 스터디 정보를 삭제하는 코드
  const handleDeleteClick = async () => {
    if (accessToken === undefined) {
      alert("권한이 없습니다.");
      return;
    }
    try {
      await deleteStudyGroupInfo(id, accessToken);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Wrapper>
      <div>스터디 명: {studyInfo?.studyName}</div>
      <div>스터디 인원: {studyInfo?.memberCountCurrent}</div>
      <div>스터디 플랫폼: {studyInfo?.platform}</div>
      <div>
        스터디 기간: {studyInfo?.studyPeriodStart} ~ {studyInfo?.studyPeriodEnd}
      </div>
      <div>태그: </div>
      <button type="button" onClick={handleEditClick}>
        스터디 정보 수정
      </button>
      <div>{studyInfo?.introduction}</div>

      {/* StudyInfoEditModal */}
      {isModalOpen && (
        <StudyInfoEditModal
          isOpen={isModalOpen}
          closeModal={() => setModalOpen(false)}
          studyInfo={studyInfo}
        />
      )}
      <button type="button" onClick={handleDeleteClick}>
        스터디 삭제
      </button>
=======
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

const ProfileStudyManage = ({ id }: ReadStudyInfoProps) => {
  const [studyInfo, setStudyInfo] = useState<StudyResponseDto | null>(null);
  // const accessToken = localStorage.getItem("accessToken");
  // meta 데이터는 index.html에 위치하며, 모든 계층의 상위에 위치한 전역 데이터이다. env 파일은 meta 데이터에 저장된 특별한 전역 변수 관리 툴이며, 이를 통해 .env 파일에 저장된 데이터를 가져올 수 있다.
  const accessToken = `Bearer ${import.meta.env.VITE_TEST_ACCESS_TOKEN}`;

  useEffect(() => {
    const fetchData = async () => {
      const data = await getStudyInfo(id, accessToken);
      setStudyInfo(data);
    };
    fetchData();
  }, []);

  return (
    <Wrapper>
      {/* 아직 서버에 연결되어 있지 않을 때 어떻게 테스트 할 수 있을까? */}
      {/* 여러 페이지에서 해당 코드에서 가져온 여러 데이터를 다양하게 렌더링하려면 어떻게 할 수 있을까? */}
      <div>스터디 명 : {studyInfo?.studyName}</div>
      <div>스터디 인원 : {studyInfo?.memberCountCurrent}</div>
      <div>스터디 플랫폼 : {studyInfo?.platform}</div>
      <div>
        스터디 기간 : {studyInfo?.studyPeriodStart} ~{" "}
        {studyInfo?.studyPeriodEnd}
      </div>
      {/* 태그는 어떻게 가져오지? */}
      <div>태그 : </div>
      <button type="button">스터디 정보 수정</button>
      <div></div>
      <div>{studyInfo?.introduction}</div>
>>>>>>> 07650a94e7d83111bd34b6e152fb8d16e31faf84
    </Wrapper>
  );
};

export default ProfileStudyManage;

const Wrapper = styled.div``;
