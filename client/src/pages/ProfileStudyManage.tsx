import { useState, useEffect } from "react";
import { deleteStudyGroupInfo, getStudyGroupInfo, StudyInfoDto } from "../apis/StudyGroupApi";
import styled from "styled-components";
import StudyInfoEditModal from "../components/modal/StudyInfoEditModal";

interface ReadStudyInfoProps {
  id: 1;
}

const ProfileStudyManage = ({ id }: ReadStudyInfoProps) => {
  const [studyInfo, setStudyInfo] = useState<StudyInfoDto | null>(null);
  const [isModalOpen, setModalOpen] = useState(false);

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
  }, [id]);

  // TODO : 스터디 정보를 수정하는 코드
  const handleEditClick = () => {
    setModalOpen(true);
  };

  // TODO : 스터디 정보를 삭제하는 코드
  const handleDeleteClick = async () => {
    try {
      await deleteStudyGroupInfo(id);
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
    </Wrapper>
  );
};

export default ProfileStudyManage;

const Wrapper = styled.div``;