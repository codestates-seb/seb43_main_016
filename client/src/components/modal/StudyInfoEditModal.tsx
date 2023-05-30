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
    width: `90%`,
    maxWidth: `600px`,
    maxHeight: `500px`,
    backgroundColor: "white",
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
        <Form>
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
          <CheckboxGroup>
            <CheckboxLabel>
              <CheckboxInput
                type="checkbox"
                name="daysOfWeek"
                value="일"
                checked={modalState.daysOfWeek.includes("일")}
                onChange={handleCheckboxChange}
              />
              일
            </CheckboxLabel>
            <CheckboxLabel>
              <CheckboxInput
                type="checkbox"
                name="daysOfWeek"
                value="월"
                checked={modalState.daysOfWeek.includes("월")}
                onChange={handleCheckboxChange}
              />
              월
            </CheckboxLabel>
            <CheckboxLabel>
              <CheckboxInput
                type="checkbox"
                name="daysOfWeek"
                value="화"
                checked={modalState.daysOfWeek.includes("화")}
                onChange={handleCheckboxChange}
              />
              화
            </CheckboxLabel>
            <CheckboxLabel>
              <CheckboxInput
                type="checkbox"
                name="daysOfWeek"
                value="수"
                checked={modalState.daysOfWeek.includes("수")}
                onChange={handleCheckboxChange}
              />
              수
            </CheckboxLabel>
            <CheckboxLabel>
              <CheckboxInput
                type="checkbox"
                name="daysOfWeek"
                value="목"
                checked={modalState.daysOfWeek.includes("목")}
                onChange={handleCheckboxChange}
              />
              목
            </CheckboxLabel>
            <CheckboxLabel>
              <CheckboxInput
                type="checkbox"
                name="daysOfWeek"
                value="금"
                checked={modalState.daysOfWeek.includes("금")}
                onChange={handleCheckboxChange}
              />
              금
            </CheckboxLabel>
            <CheckboxLabel>
              <CheckboxInput
                type="checkbox"
                name="daysOfWeek"
                value="토"
                checked={modalState.daysOfWeek.includes("토")}
                onChange={handleCheckboxChange}
              />
              토
            </CheckboxLabel>
          </CheckboxGroup>
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
        </Form>
      </Modal>
    </>
  );
};

export default StudyInfoEditModal;

const Form = styled.form`
  padding: 16px;
`;

const ModalExplain = styled.div`
  margin-bottom: 8px;
`;

const UserInfoEditInput = styled.input`
  margin-bottom: 16px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  width: 100%;
`;

const CheckboxGroup = styled.div`
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 16px;

  label {
    display: flex;
    align-items: center;
    margin-right: 16px;
    margin-bottom: 8px;
    cursor: pointer;
  }
`;

const CheckboxInput = styled.input`
  margin-right: 4px;
`;

const CheckboxLabel = styled.label`
  color: #555;
`;

const ModalButton = styled.button`
  display: inline-block;
  margin-right: 8px;
  padding: 8px 16px;
  background-color: #1e77d1;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;

  &:hover {
    background-color: #1c6bb7;
  }

  &:active {
    background-color: #195890;
  }
`;