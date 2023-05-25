import Modal from "react-modal";
import { useRecoilValue } from "recoil";
import { LogInState } from "../../recoil/atoms/LogInState";
import { useEffect, useState } from "react";
import {
  getCustomEvents,
  EventData,
  deleteCustomEvents,
} from "../../apis/CalendarApi";

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
    border: "none",
    boxShadow: "0px 4px 10px rgba(0, 0, 0, 0.2)",
    padding: "20px",
    outline: "none",
  },
};

interface ViewEventDetailModalProps {
  isOpen: boolean;
  closeModal: () => void;
  id: number;
}

const ViewCustomCalendarEvent = ({
  isOpen,
  closeModal,
  id,
}: ViewEventDetailModalProps) => {
  const isLoggedIn = useRecoilValue(LogInState);
  const [studyInfo, setStudyInfo] = useState<EventData | null>(null);

  useEffect(() => {
    if (!isLoggedIn) {
      alert("로그인 상태를 확인하세요");
    } else {
      getCustomEvents(isLoggedIn, id).then((studyInfo) =>
        setStudyInfo(studyInfo)
      );
      // .catch((error) => alert(error.message));
    }
  }, [id, isLoggedIn]);

  const handleDeleteEvent = () => {
    if (studyInfo) {
      deleteCustomEvents(isLoggedIn, studyInfo.calendarId)
        .then(() => closeModal())
        .catch((error) => alert(error.message));
    }
  };

  // 시작일과 종료일 분할
  const startDate = studyInfo
    ? studyInfo.schedule.studyTimeStart.split("T")[0]
    : "";
  const endDate = studyInfo
    ? studyInfo.schedule.studyTimeEnd.split("T")[0]
    : "";

  // 시작시간과 종료시간 분할
  const startTime = studyInfo
    ? studyInfo.schedule.studyTimeStart.split("T")[1]
    : "";
  const endTime = studyInfo
    ? studyInfo.schedule.studyTimeEnd.split("T")[1]
    : "";

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
            <h2 style={{ marginBottom: "10px", color: "#4D74B1" }}>
              {" "}
              제목 : {studyInfo.title}
            </h2>
            <p style={{ marginBottom: "2px" }}>
              스터디 기간: {startDate} ~ {endDate}
            </p>
            <p style={{ marginBottom: "2px" }}>
              스터디 시간: {startTime} ~ {endTime}
            </p>
            <p style={{ marginBottom: "2px" }}>
              상세내용: {studyInfo.description}
            </p>
          </>
        ) : (
          <p>Loading study group information...</p>
        )}
        <button onClick={handleDeleteEvent}>삭제</button>
      </Modal>
    </>
  );
};

export default ViewCustomCalendarEvent;
