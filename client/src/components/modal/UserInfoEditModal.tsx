import { ChangeEvent, useState } from "react";
import Modal from "react-modal";
import styled from "styled-components";
import { updateMember, MemberUpdateDto } from "../../apis/MemberApi";
import { LogInState } from "../../recoil/atoms/LogInState";
import { useRecoilValue } from "recoil";

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
  userNickname: string | undefined;
}

const UserInfoEditModal = ({
  isOpen,
  closeModal,
  userNickname,
}: UserInfoEditModalProps) => {
  const isLoggedIn = useRecoilValue(LogInState);

  const [modalState, setModalState] = useState({
    nickname: "",
    password: "",
    passwordCheck: "",
  });

  const handleInputChange = (event: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setModalState((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSaveClick = async () => {
    if (
      modalState.nickname === "" ||
      modalState.password === "" ||
      modalState.passwordCheck === ""
    ) {
      alert("입력되지 않은 정보가 있습니다.");
    } else {
      if (modalState.password !== modalState.passwordCheck) {
        alert("새로운 비밀번호와 비밀번호 확인이 서로 일치하지 않습니다.");
        return;
      }

      try {
        const updateDto: MemberUpdateDto = {
          nickName: modalState.nickname,
          password: modalState.password,
        };
        await updateMember(isLoggedIn, updateDto); // Await the updateMember function call
        closeModal();
      } catch (error: any) {
        alert(error.response.data.message);
      }
    }
  };

  const handleCancelClick = () => {
    closeModal();
    setModalState({
      nickname: "",
      password: "",
      passwordCheck: "",
    });
  };

  return (
    <>
      <Modal
        isOpen={isOpen}
        onRequestClose={closeModal}
        style={customStyles}
        contentLabel="UserInfoEditModal"
      >
        <form>
          <ModalExplain>변경할 Nickname</ModalExplain>
          <UserInfoEditInput
            name="nickname"
            value={modalState.nickname}
            onChange={handleInputChange}
            placeholder={userNickname}
            required
          />
          <ModalExplain>변경할 비밀번호</ModalExplain>
          <UserInfoEditInput
            name="password"
            type="password"
            value={modalState.password}
            onChange={handleInputChange}
            required
          />
          <ModalExplain>변경할 비밀번호 확인</ModalExplain>
          <UserInfoEditInput
            name="passwordCheck"
            type="password"
            value={modalState.passwordCheck}
            onChange={handleInputChange}
            required
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
