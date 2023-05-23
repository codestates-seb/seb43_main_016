import styled from "styled-components";
import Modal from "react-modal";
import { ChangeEvent, FormEvent, useState } from "react";
import { MemberPasswordCheckDto } from "../../apis/MemberApi";

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
  setPasswordCheckResult: React.Dispatch<
    React.SetStateAction<MemberPasswordCheckDto>
  >;
}

const CheckPasswordModal = ({
  isOpen,
  closeModal,
  setPasswordCheckResult,
}: CheckPasswordModalProps) => {
  const [passwordState, setPasswordState] = useState("");

  const handleInputChange = (event: ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    setPasswordState(value);
  };

  const handleFormSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setPasswordCheckResult({ password: passwordState });
    closeModal();
  };

  return (
    <>
      <Modal
        isOpen={isOpen}
        onRequestClose={closeModal}
        style={customStyles}
        contentLabel="CheckPasswordModal"
      >
        <ModalExplain>개인정보 수정 전, 비밀번호를 재확인합니다.</ModalExplain>
        <form onSubmit={handleFormSubmit}>
          <ModalInput
            type="password"
            placeholder="비밀번호를 입력하세요."
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
