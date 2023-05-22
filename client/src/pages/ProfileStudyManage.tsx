import { useState, useEffect } from "react";
import {
  changeStudyGroupRecruitmentStatus,
  deleteStudyGroupInfo,
  exitStudyGroup,
  getStudyGroupInfo,
  StudyInfoDto,
} from "../apis/StudyGroupApi";
import styled from "styled-components";
import StudyInfoEditModal from "../components/modal/StudyInfoEditModal";
import { useParams, useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { LogInState } from "../recoil/atoms/LogInState";
import MemberManage from "../components/studyManage/MemberManage";
import CandidateManage from "../components/studyManage/CandidateManage";

const ProfileStudyManage = () => {
  const [studyInfo, setStudyInfo] = useState<StudyInfoDto | null>(null);
  const [isModalOpen, setModalOpen] = useState(false);
  const { id } = useParams();
  const parsedId = Number(id);
  const navigate = useNavigate();
  const isLoggedIn = useRecoilValue(LogInState);
  const isRecruiting = studyInfo?.isRecruited;

  console.log(studyInfo);

  // TODO : 최초 페이지 진입 시 스터디 정보를 조회하는 코드
  useEffect(() => {
    const fetchStudyGroupInfo = async () => {
      if (isNaN(parsedId)) {
        alert("잘못된 접근입니다");
        navigate("/profile");
        return;
      }
      try {
        const studyInfo = await getStudyGroupInfo(parsedId, isLoggedIn);
        setStudyInfo(studyInfo);
      } catch (error) {
        console.log(error);
      }
    };

    fetchStudyGroupInfo();
  }, [parsedId]);

  // TODO : 스터디 정보를 수정하는 코드
  const handleEditClick = () => {
    setModalOpen(true);
  };

  // TODO : 스터디 정보를 삭제하는 코드
  const handleDeleteClick = async () => {
    await deleteStudyGroupInfo(parsedId, isLoggedIn);
  };

  // TODO : 스터디에서 탈퇴하는 코드
  const handleExitClick = async () => {
    await exitStudyGroup(parsedId, isLoggedIn);
  };

  // TODO : 스터디 모집 상태를 수정하는 코드
  const handleRecuitCloseClick = async () => {
    await changeStudyGroupRecruitmentStatus(parsedId, isLoggedIn);
  };

  return (
    <Wrapper>
      {!isRecruiting ? (
        <button type="button" onClick={handleRecuitCloseClick}>
          스터디 모집 중
        </button>
      ) : (
        <div>스터디 모집 완료</div>
      )}
      <div>스터디 명: {studyInfo?.studyName}</div>
      <div>스터디 인원: {studyInfo?.memberCountCurrent}</div>
      <div>스터디장: {studyInfo?.leaderNickName}</div>
      <div>스터디 플랫폼: {studyInfo?.platform}</div>
      <div>
        스터디 기간: {studyInfo?.studyPeriodStart} ~ {studyInfo?.studyPeriodEnd}
      </div>
      <div>
        태그:
        {studyInfo?.tags && (
          <>
            {Object.entries(studyInfo.tags).map(([category, tags]) => (
              <div key={category}>
                {category}:
                {tags.map((tag) => (
                  <span key={tag}>{tag}</span>
                ))}
              </div>
            ))}
          </>
        )}
      </div>
      <div>
        만남의 날 : 매 주 {studyInfo?.daysOfWeek} {studyInfo?.studyTimeStart} ~{" "}
        {studyInfo?.studyTimeEnd}
      </div>
      <button type="button" onClick={handleEditClick}>
        스터디 정보 수정
      </button>
      <div>스터디 소개</div>
      <div>{studyInfo?.introduction}</div>
      {/* StudyInfoEditModal */}
      {isModalOpen && (
        <StudyInfoEditModal
          isOpen={isModalOpen}
          closeModal={() => setModalOpen(false)}
          studyInfo={studyInfo}
        />
      )}
      <MemberManage />
      <CandidateManage />
      <button type="button" onClick={handleDeleteClick}>
        스터디 삭제
      </button>
      <button type="button" onClick={handleExitClick}>
        스터디 탈퇴
      </button>
    </Wrapper>
  );
};

export default ProfileStudyManage;

const Wrapper = styled.div``;
