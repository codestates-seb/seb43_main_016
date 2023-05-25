import Modal from "react-modal";
import { useRecoilValue } from "recoil";
import { LogInState } from "../../recoil/atoms/LogInState";
import { useEffect, useState } from "react";
import { StudyInfoDto, getStudyGroupInfo } from "../../apis/StudyGroupApi";
import { useNavigate } from "react-router-dom";

const customStyles = {
  overlay: {
    zIndex: 9999,
    backgroundColor: "rgba(0, 0, 0, 0.5)",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
  },
  content: {
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    maxWidth: "400px",
    width: "90%",
    maxHeight: "350px",
    backgroundColor: "white",
    borderRadius: "10px",
    border: `2px solid #4D74B1`,
    padding: "20px",
  },
};

interface ViewEventDetailModalProps {
  isOpen: boolean;
  closeModal: () => void;
  id: number;
}

const ViewCalendarEvent = ({
  isOpen,
  closeModal,
  id,
}: ViewEventDetailModalProps) => {
  const isLoggedIn = useRecoilValue(LogInState);
  const [studyInfo, setStudyInfo] = useState<StudyInfoDto | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    if (!isLoggedIn) {
      alert("로그인 상태를 확인하세요");
    } else {
      getStudyGroupInfo(id, isLoggedIn)
        .then((studyInfo) => setStudyInfo(studyInfo))
        .catch((error) => alert(error.message));
    }
  }, [id, isLoggedIn]);

  const goToGroupPage = () => {
    navigate(`/profile/${id}`);
  };

  return (
    <>
      <Modal
        isOpen={isOpen}
        onRequestClose={closeModal}
        style={customStyles}
        contentLabel="ViewEventDetailModal"
      >
        {studyInfo ? (
          <>
            <h2 style={{ marginBottom: "10px", color: "#4D74B1" }}>{studyInfo.studyName}</h2>
            <p style={{marginBottom: "2px"}}>스터디 기간: {studyInfo.studyPeriodStart} - {studyInfo.studyPeriodEnd}</p>
            <p style={{marginBottom: "2px"}}>스터디 시간: {studyInfo.studyTimeStart} - {studyInfo.studyTimeEnd}</p>
            <p style={{marginBottom: "2px"}}>현재 멤버 수: {studyInfo.memberCountCurrent}</p>
            <p style={{marginBottom: "2px"}}>Platform: {studyInfo.platform}</p>
            <p style={{marginBottom: "2px"}}>모집상태 : {studyInfo.isRecruited ? "모집완료" : "모집중"}</p>
            <p style={{marginBottom: "2px"}}>리더 명: {studyInfo.leaderNickName}</p>
            <p
              onClick={goToGroupPage}
              style={{
                cursor: "pointer",
                color: "#4D74B1",
                marginTop: "20px",
              }}
            >
              그룹 페이지로 가기
            </p>
          </>
        ) : (
          <p>Loading study group information...</p>
        )}
      </Modal>
    </>
  );
};

export default ViewCalendarEvent;
