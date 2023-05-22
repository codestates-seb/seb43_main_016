import styled from "styled-components";
import Modal from "react-modal";
import { ChangeEvent, useState } from "react";

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

interface CheckPasswordModalProps {
  isOpen: boolean;
  closeModal: () => void;
  onPasswordEntered: (enteredPassword: string) => void;
}

const CheckPasswordModal = ({
  isOpen,
  closeModal,
  onPasswordEntered,
}: CheckPasswordModalProps) => {
  const [modalState, setModalState] = useState("");

  const handleInputChange = (event: ChangeEvent<HTMLInputElement>) => {
    event.preventDefault();
    const { value } = event.target;
    setModalState(value);
  };

  const handleSubmitClick = () => {
    closeModal();
    onPasswordEntered(modalState);
  };

  const handleFormSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    handleSubmitClick();
  };

  return (
    <>
      <Modal
        isOpen={isOpen}
        onRequestClose={closeModal}
        style={customStyles}
        contentLabel="CheckPasswordModal"
      >
        <ModalExplain>개인정보 수정 전, 비밀번호를 재확인합니다</ModalExplain>
        <form onSubmit={handleFormSubmit}>
          <ModalInput
            type="password"
            placeholder="비밀번호를 입력하세요"
            onChange={handleInputChange}
            autoComplete="new-password"
          />
          <ModalButton type="submit">확인</ModalButton>
        </form>
      </Modal>
    </>
  );
};

export default CheckPasswordModal;

const ModalExplain = styled.div``;
const ModalInput = styled.input``;
const ModalButton = styled.button``;
