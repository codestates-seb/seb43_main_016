import { useState } from "react";
import Modal from "react-modal";
import styled from "styled-components";
import {
  StudyInfoDto,
  updateStudyGroupInfo,
  StudyGroupUpdateDto,
} from "../../apis/StudyGroupApi";
import { useParams } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { LogInState } from "../../recoil/atoms/LogInState";

const customStyles = {
  content: {
    top: "50%",
    left: "50%",
    right: "auto",
    bottom: "auto",
    marginRight: "-50%",
    transform: "translate(-50%, -50%)",
  },
};

interface UserInfoEditModalProps {
  isOpen: boolean;
  closeModal: () => void;
  studyInfo: StudyInfoDto | null;
}

const StudyInfoEditModal = ({
  isOpen,
  closeModal,
  studyInfo,
}: UserInfoEditModalProps) => {
  const isLoggedIn = useRecoilValue(LogInState);
  const [modalState, setModalState] = useState<StudyGroupUpdateDto>({
    id: studyInfo?.id || 0,
    studyName: studyInfo?.studyName || "",
    studyPeriodStart: studyInfo?.studyPeriodStart || "",
    studyPeriodEnd: studyInfo?.studyPeriodEnd || "",
    daysOfWeek: studyInfo?.daysOfWeek || [],
    studyTimeStart: studyInfo?.studyTimeStart || "",
    studyTimeEnd: studyInfo?.studyTimeEnd || "",
    memberCountMin: studyInfo?.memberCountMin || 0,
    memberCountMax: studyInfo?.memberCountMax || 0,
    platform: studyInfo?.platform || "",
    introduction: studyInfo?.introduction || "",
    tags: studyInfo?.tags || {},
  });

  const { id } = useParams();
  const parsedId = Number(id);

  console.log(modalState);

  const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { value, checked } = event.target;
    let updatedDaysOfWeek = [...modalState.daysOfWeek];

    if (checked) {
      updatedDaysOfWeek.push(value);
    } else {
      updatedDaysOfWeek = updatedDaysOfWeek.filter((day) => day !== value);
    }

    setModalState((prevState) => ({
      ...prevState,
      daysOfWeek: updatedDaysOfWeek,
    }));
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setModalState((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSaveClick = async () => {
    try {
      await updateStudyGroupInfo(modalState, isLoggedIn, parsedId);
      closeModal();
    } catch (error) {
      alert("스터디 그룹 정보를 업데이트하는 중에 오류가 발생했습니다.");
      console.error("Error updating study group information:", error);
    }
  };

  const handleCancelClick = () => {
    closeModal();
  };

  const handleAfterClose = () => {
    location.reload();
  };

  return (
    <>
      <Modal
        isOpen={isOpen}
        onRequestClose={closeModal}
        style={customStyles}
        onAfterClose={handleAfterClose}
      >
        <form>
          <ModalExplain>스터디명</ModalExplain>
          <UserInfoEditInput
            name="studyName"
            value={modalState.studyName}
            onChange={handleInputChange}
          />
          <ModalExplain>스터디 시작 날짜</ModalExplain>
          <UserInfoEditInput
            name="studyPeriodStart"
            type="date"
            value={modalState.studyPeriodStart}
            onChange={handleInputChange}
          />
          <ModalExplain>스터디 종료 날짜</ModalExplain>
          <UserInfoEditInput
            name="studyPeriodEnd"
            type="date"
            value={modalState.studyPeriodEnd}
            onChange={handleInputChange}
          />
          <ModalExplain>스터디 요일 선택</ModalExplain>
          <div>
            <label>
              <input
                type="checkbox"
                name="daysOfWeek"
                value="일"
                checked={modalState.daysOfWeek.includes("일")}
                onChange={handleCheckboxChange}
              />
              일
            </label>
            <label>
              <input
                type="checkbox"
                name="daysOfWeek"
                value="월"
                checked={modalState.daysOfWeek.includes("월")}
                onChange={handleCheckboxChange}
              />
              월
            </label>
            <label>
              <input
                type="checkbox"
                name="daysOfWeek"
                value="화"
                checked={modalState.daysOfWeek.includes("화")}
                onChange={handleCheckboxChange}
              />
              화
            </label>
            <label>
              <input
                type="checkbox"
                name="daysOfWeek"
                value="수"
                checked={modalState.daysOfWeek.includes("수")}
                onChange={handleCheckboxChange}
              />
              수
            </label>
            <label>
              <input
                type="checkbox"
                name="daysOfWeek"
                value="목"
                checked={modalState.daysOfWeek.includes("목")}
                onChange={handleCheckboxChange}
              />
              목
            </label>
            <label>
              <input
                type="checkbox"
                name="daysOfWeek"
                value="금"
                checked={modalState.daysOfWeek.includes("금")}
                onChange={handleCheckboxChange}
              />
              금
            </label>
            <label>
              <input
                type="checkbox"
                name="daysOfWeek"
                value="토"
                checked={modalState.daysOfWeek.includes("토")}
                onChange={handleCheckboxChange}
              />
              토
            </label>
          </div>
          <ModalExplain>스터디 시작 시간</ModalExplain>
          <UserInfoEditInput
            name="studyTimeStart"
            type="time"
            value={modalState.studyTimeStart}
            onChange={handleInputChange}
          />
          <ModalExplain>스터디 종료 시간</ModalExplain>
          <UserInfoEditInput
            name="studyTimeEnd"
            type="time"
            value={modalState.studyTimeEnd}
            onChange={handleInputChange}
          />
          <ModalExplain>스터디 플랫폼</ModalExplain>
          <UserInfoEditInput
            name="platform"
            type="text"
            value={modalState.platform}
            onChange={handleInputChange}
          />
          <ModalExplain>Introduction</ModalExplain>
          <UserInfoEditInput
            name="introduction"
            type="text"
            value={modalState.introduction}
            onChange={handleInputChange}
          />
          <ModalButton type="button" onClick={handleSaveClick}>
            저장
          </ModalButton>
          <ModalButton type="button" onClick={handleCancelClick}>
            취소
          </ModalButton>
        </form>
      </Modal>
    </>
  );
};

export default StudyInfoEditModal;

const ModalExplain = styled.div``;
const UserInfoEditInput = styled.input``;
const ModalButton = styled.button``;
