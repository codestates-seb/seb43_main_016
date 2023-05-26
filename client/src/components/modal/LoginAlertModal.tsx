import styled from "styled-components";
import Modal from "react-modal";
import { useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { LogInState } from "../../recoil/atoms/LogInState";

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
}

const LoginAlertModal = ({ isOpen, closeModal }: ModalProps) => {
  const navigate = useNavigate();
  const isLoggedIn = useRecoilValue(LogInState);

  // useEffect(() => {
  setTimeout(() => {
    closeModal();
    if (!isLoggedIn) navigate("/login");
  }, 1500);
  //}, []);

  return (
    <>
      <Modal
        isOpen={isOpen}
        onRequestClose={closeModal}
        style={customStyles}
        contentLabel="loginAlertModal"
      >
        <ModalExplain>로그인이 필요한 페이지 입니다!</ModalExplain>
      </Modal>
    </>
  );
};

const ModalExplain = styled.div`
  font-size: 15px;
  margin: 10px 0;
  text-align: center;
`;

export default LoginAlertModal;
