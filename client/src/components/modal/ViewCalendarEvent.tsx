import Modal from "react-modal";
import { useRecoilValue } from "recoil";
import { LogInState } from "../../recoil/atoms/LogInState";
import { useEffect, useState } from "react";
import { StudyInfoDto, getStudyGroupInfo } from "../../apis/StudyGroupApi";
import { useNavigate } from "react-router-dom";

const customStyles = {
  overlay: {
    zIndex: 9999,
    backgroundColor: "rgba(0, 0, 0, 0.5)", // Add a semi-transparent overlay background
  },
  content: {
    top: "50%",
    left: "50%",
    right: "auto",
    bottom: "auto",
    marginRight: "-50%",
    transform: "translate(-50%, -50%)",
    backgroundColor: "white",
    borderRadius: "10px", // Add rounded corners
    border: `2px solid #4D74B1`, // Set border color to #4D74B1
    padding: "20px", // Add padding for spacing
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
            <h2>{studyInfo.studyName}</h2>
            <p>스터디 기간: {studyInfo.studyPeriodStart} - {studyInfo.studyPeriodEnd}</p>
            <p>스터디 시간: {studyInfo.studyTimeStart} - {studyInfo.studyTimeEnd}</p>
            <p>현재 멤버 수: {studyInfo.memberCountCurrent}</p>
            <p>Platform: {studyInfo.platform}</p>
            <p>모집상태 : {studyInfo.isRecruited ? "모집완료" : "모집중"}</p>
            <p>리더 명: {studyInfo.leaderNickName}</p>
            <p onClick={goToGroupPage} style={{ cursor: "pointer", color: "#4D74B1" }}>
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
