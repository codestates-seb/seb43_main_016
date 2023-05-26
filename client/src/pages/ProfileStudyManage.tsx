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
import { getMemberInfo } from "../apis/MemberApi";

const ProfileStudyManage = () => {
  const [studyInfo, setStudyInfo] = useState<StudyInfoDto | null>(null);
  const [isModalOpen, setModalOpen] = useState(false);
  const [LoggedInUser, setLoggedInUser] = useState<string | null>(null);
  const { id } = useParams();
  const parsedId = Number(id);
  const navigate = useNavigate();
  const isLoggedIn = useRecoilValue(LogInState);
  const isRecruiting = studyInfo?.isRecruited;

  // TODO : 로그인 상태가 변경되면, 홈 화면으로 이동하는 코드
  useEffect(() => {
    if (!isLoggedIn) {
      navigate("/");
    }
  }, [isLoggedIn]);

  // TODO : 최초 페이지 진입 시 스터디 정보를 조회하는 코드
  useEffect(() => {
    if (!isLoggedIn) {
      navigate("/login");
    }
    const fetchStudyGroupInfo = async () => {
      if (isNaN(parsedId)) {
        alert("잘못된 접근입니다");
        navigate("/profile");
        return;
      }
      try {
        const studyInfo = await getStudyGroupInfo(parsedId, isLoggedIn);
        setStudyInfo(studyInfo);
      } catch (error) {}
    };
    getMemberInfo(isLoggedIn).then((data) => {
      if (data) {
        setLoggedInUser(data.nickName);
      } else {
        setLoggedInUser(null);
      }
    });
    fetchStudyGroupInfo();
  }, [parsedId]);

  // TODO : 스터디 정보를 수정하는 코드
  const handleEditClick = () => {
    if (LoggedInUser !== studyInfo?.leaderNickName) {
      alert("스터디장만 스터디를 수정할 수 있습니다");
      return;
    }
    setModalOpen(true);
  };

  // TODO : 스터디 정보를 삭제하는 코드
  const handleDeleteClick = async () => {
    if (LoggedInUser !== studyInfo?.leaderNickName) {
      alert("선넘네...?");
      return;
    }
    if (!window.confirm("정말로 스터디를 삭제하시겠습니까?")) return;
    await deleteStudyGroupInfo(parsedId, isLoggedIn);
    navigate("/profile/manage-group");
  };

  // TODO : 스터디에서 탈퇴하는 코드
  const handleExitClick = async () => {
    if (LoggedInUser === studyInfo?.leaderNickName) {
      alert("스터디장은 스터디에서 탈퇴할 수 없습니다");
      return;
    }
    if (!window.confirm("정말로 스터디를 탈퇴하시겠습니까?")) return;
    exitStudyGroup(parsedId, isLoggedIn);
    navigate("/profile/manage-group");
    window.location.reload(); // 페이지를 새로고침
  };

  // TODO : 스터디 모집 상태를 수정하는 코드
  const handleRecuitCloseClick = async () => {
    getMemberInfo(isLoggedIn).then((data) => {
      if (data.nickName !== studyInfo?.leaderNickName) {
        alert("스터디장만 스터디의 모집 상태를 수정할 수 있습니다");
        return;
      }
    });
    await changeStudyGroupRecruitmentStatus(parsedId, isLoggedIn);
    location.reload();
  };

  // TODO : HTML 태그로 이뤄진 문자열을 일반 문자열로 변경하는 함수
  const removeHtmlTag = (str: string | undefined) => {
    if (str === undefined) return str;
    return str.replace(/(<([^>]+)>)/gi, "");
  };

  return (
    <StoryManageContainer>
      <ManageTitle>
        <h2>{studyInfo?.studyName}</h2>
      </ManageTitle>
      <ManageInfo>
        {" "}
        <ManageSpan>모집 상태</ManageSpan>
        {!isRecruiting ? (
          <button type="button" onClick={handleRecuitCloseClick}>
            모집 중
          </button>
        ) : (
          <div>모집 완료</div>
        )}
      </ManageInfo>

      <ManageInfo>
        <ManageSpan>현재 인원</ManageSpan> {studyInfo?.memberCountCurrent}
      </ManageInfo>
      <ManageInfo>
        <ManageSpan>스터디장</ManageSpan> {studyInfo?.leaderNickName}
      </ManageInfo>
      <ManageInfo>
        <ManageSpan>플랫폼</ManageSpan> {studyInfo?.platform}
      </ManageInfo>
      <ManageInfo>
        <ManageSpan>기간</ManageSpan> {studyInfo?.studyPeriodStart} ~{" "}
        {studyInfo?.studyPeriodEnd}
      </ManageInfo>
      <ManageInfo>
        <ManageSpan>태그</ManageSpan>
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
      </ManageInfo>
      <ManageInfo>
        <ManageSpan>일정</ManageSpan> 매주 {studyInfo?.daysOfWeek}{" "}
        {studyInfo?.studyTimeStart} ~ {studyInfo?.studyTimeEnd}
      </ManageInfo>
      <ManageIntro>{removeHtmlTag(studyInfo?.introduction)}</ManageIntro>
      <ManageButtonContainer>
        <button type="button" onClick={handleEditClick}>
          스터디 정보 수정
        </button>
      </ManageButtonContainer>
      {isModalOpen && (
        <StudyInfoEditModal
          isOpen={isModalOpen}
          closeModal={() => setModalOpen(false)}
          studyInfo={studyInfo}
        />
      )}
      <MemberManage studyLeader={studyInfo?.leaderNickName} />
      <CandidateManage studyLeader={studyInfo?.leaderNickName} />
      <ManageButtonContainer>
        <button
          type="button"
          className="delete-exit-button"
          onClick={handleDeleteClick}
        >
          스터디 삭제
        </button>
        <button
          type="button"
          className="delete-exit-button"
          onClick={handleExitClick}
        >
          스터디 탈퇴
        </button>
      </ManageButtonContainer>
    </StoryManageContainer>
  );
};

export default ProfileStudyManage;

const StoryManageContainer = styled.div`
  width: 960px;
  height: 100%;
  padding: 80px 0 100px;
  background-color: #fff;
  border-radius: 4px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const ManageTitle = styled.div`
  width: 800px;
  margin: 0 0 30px 50px;
  display: flex;
  justify-content: flex-start;
  align-items: center;

  h2 {
    width: 700px;
    text-align: left;
    font-size: 24px;
    font-weight: 700;
    color: #1f1f1f;
  }
`;

const ManageInfo = styled.div`
  width: 800px;
  margin: 0 0 20px 50px;
  display: flex;
  justify-content: flex-start;
  align-items: center;

  div {
    text-align: left;
    font-size: 16px;
    font-weight: 300;
    color: #666;
  }
`;

const ManageSpan = styled.span`
  width: 100px;
  text-align: left;
  font-size: 18px;
  font-weight: 700;
  color: #2759a2;
  margin-right: 20px;
`;

const ManageIntro = styled.p`
  width: 750px;
  margin: 20px 40px;
  text-align: left;
  font-size: 15px;
  font-weight: 300;
  color: #1f1f1f;
`;

const ManageButtonContainer = styled.div`
  width: 750px;
  display: flex;
  justify-content: flex-end;
  align-items: center;

  button {
    width: 170px;
    height: 42px;
    margin: 30px 10px 0;
    font-size: 18px;
    color: #ffffff;
    background-color: #4994da;

    &:hover {
      opacity: 85%;
    }
    &:active {
      opacity: 100%;
    }
  }

  .delete-exit-button {
    width: 120px;
    font-size: 16px;
    background-color: #666;
    margin-right: 10px;

    &:hover {
      background-color: #5a0202;
    }
  }
`;
