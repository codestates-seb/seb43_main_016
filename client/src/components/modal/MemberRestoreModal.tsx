import styled from "styled-components";
import Modal from "react-modal";
import { useNavigate } from "react-router-dom";
import { ChangeEvent, FormEvent, useState } from "react";
import { eduApi } from "../../apis/EduApi";
import { validateEmptyInput } from "../../pages/utils/loginUtils";

const customStyles = {
  overlay: {
    zIndex: 9999,
    backgroundColor: "rgba(0, 0, 0, 0.5)",
  },
  content: {
    top: "50%",
    left: "50%",
    right: "auto",
    bottom: "auto",
    marginRight: "-50%",
    transform: "translate(-50%, -50%)",
    padding: "30px",
  },
};

interface ModalProps {
  isOpen: boolean;
  closeModal: () => void;
  email: string;
}

const MemberRestoreModal = ({ isOpen, closeModal, email }: ModalProps) => {
  const [password, setPassword] = useState("");
  const [isSuccess, setIsSuccess] = useState(false);
  const navigate = useNavigate();

  const handlePassword = (e: ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const handleFormSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (validateEmptyInput(email) || validateEmptyInput(password))
      alert("닉네임과 이메일, 패스워드를 모두 입력해주세요!");
    else {
      eduApi
        .patch("/members/reactive", {
          email,
          password,
        })
        .then(() => {
          setIsSuccess(true);
          setTimeout(() => {
            closeModal();
            setIsSuccess(false);
            navigate("/login");
          }, 800);
        });
    }
  };

  return (
    <>
      <Modal
        isOpen={isOpen}
        onRequestClose={closeModal}
        style={customStyles}
        contentLabel="MemberRestoreModal"
      >
        {isSuccess ? (
          <ModalExplain>
            복구에 성공했습니다! 로그인 화면으로 이동합니다.
          </ModalExplain>
        ) : (
          <>
            <ModalExplain>탈퇴한 회원입니다.</ModalExplain>
            <ModalExplain>계정을 복구하시겠습니까?</ModalExplain>
            <ModalForm onSubmit={handleFormSubmit}>
              <input disabled value={email} type="email" required />
              <input
                onChange={handlePassword}
                type="password"
                placeholder="Password"
                required
              />

              <ModalButton type="submit">복구하기</ModalButton>
            </ModalForm>
          </>
        )}
      </Modal>
    </>
  );
};

const ModalExplain = styled.div`
  font-size: 15px;
  margin: 10px 0;
  text-align: center;
`;
const ModalForm = styled.form`
  display: flex;
  flex-direction: column;
  input {
    margin: 3px;
  }
`;
const ModalButton = styled.button`
  margin-top: 10px;
  font-size: 14px;
  width: 13rem;
  background-color: #858da8;
  color: #ffffff;
`;

export default MemberRestoreModal;
