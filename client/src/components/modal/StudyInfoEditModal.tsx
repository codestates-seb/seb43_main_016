import { useState } from "react";
import Modal from "react-modal";
import styled from "styled-components";
import {
  updateStudyGroupInfo,
  StudyGroupUpdateDto,
  StudyInfoDto,
} from "../../apis/StudyGroupApi";

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

const UserInfoEditModal = ({ isOpen, closeModal }: UserInfoEditModalProps) => {
  const [modalState, setModalState] = useState<StudyGroupUpdateDto>({
    id: 123, // Replace with the actual study group ID
    studyName: "",
    studyPeriodStart: "",
    studyPeriodEnd: "",
    daysOfWeek: [],
    studyTimeStart: "",
    studyTimeEnd: "",
    memberCountMin: 1, // Replace with the actual member count minimum value
    memberCountMax: 5, // Replace with the actual member count maximum value
    platform: "",
    introduction: "", // Replace with the actual introduction value
    tags: {}, // Replace with the actual tags object
  });

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setModalState((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSaveClick = async () => {
    // if (accessToken === undefined) {
    //   alert("권한이 없습니다.");
    //   return;
    // }

    try {
      await updateStudyGroupInfo(modalState);
      closeModal();
    } catch (error) {
      alert("스터디 그룹 정보를 업데이트하는 중에 오류가 발생했습니다.");
      console.error("Error updating study group information:", error);
    }
  };

  const handleCancelClick = () => {
    closeModal();
  };

  return (
    <>
      <Modal
        isOpen={isOpen}
        onRequestClose={closeModal}
        style={customStyles}
        contentLabel="Example Modal"
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
              월
              <input
                type="checkbox"
                name="daysOfWeek"
                value="월"
                checked={modalState.daysOfWeek.includes(1)}
                onChange={handleInputChange}
              />
            </label>
            <label>
              화
              <input
                type="checkbox"
                name="daysOfWeek"
                value="화"
                checked={modalState.daysOfWeek.includes(2)}
                onChange={handleInputChange}
              />
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

export default UserInfoEditModal;

const ModalExplain = styled.div``;
const UserInfoEditInput = styled.input``;
const ModalButton = styled.button``;
