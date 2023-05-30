import styled from "styled-components";
import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { eduApi } from "../apis/EduApi";
import logo from "../assets/edusync-logo.png";
import GoogleButton from "../components/social-login-button/GoogleButton";
import KakaoButton from "../components/social-login-button/KakaoButton";
import NaverButton from "../components/social-login-button/NaverButton";
import MemberRestoreModal from "../components/modal/MemberRestoreModal";
import { validateEmptyInput } from "./utils/loginUtils";

const SignUp = () => {
  const [nickName, setNickName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [memberRestoreModalOpen, setMemberRestoreModalOpen] = useState(false);
  const navigate = useNavigate();

  const handleNickName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickName(e.target.value);
  };
  const handleEmail = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };
  const handlePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const emailTest = (data: string) => {
    return /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/g.test(
      data
    );
  };

  const handleSignUpButton = () => {
    if (
      validateEmptyInput(nickName) ||
      validateEmptyInput(email) ||
      validateEmptyInput(password)
    )
      alert("닉네임과 이메일, 패스워드를 모두 입력해주세요!");
    else if (emailTest(email) === false) alert("이메일 형식이 잘못되었습니다.");
    else {
      eduApi
        .post(`/members`, {
          email,
          password,
          nickName,
        })
        .then(() => navigate("/login"))
        .catch((error) => {
          if (error.response.data.message === "이메일이 이미 존재") {
            alert("이미 가입된 이메일 입니다.");
          }
          if (error.response.data.message === "닉네임이 이미 존재") {
            alert("사용할 수 없는 닉네임입니다.");
          }

          if (error.response.data.message === "탈퇴한 회원입니다.") {
            setMemberRestoreModalOpen(true);
          }
          if(error.response.data.message  === "해당 닉네임은 금지되어있습니다!!") {
            alert("사용할 수 없는 닉네임입니다.")
          }
        })
        .finally(() => {});
    }
  };
  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      event.preventDefault();
      handleSignUpButton();
    }
  };

  return (
    <Container>
      <SignUpDiv onKeyDown={handleKeyDown}>
        <LogoDiv>
          <img src={logo} />
        </LogoDiv>
        <SignUpForm>
          <input
            onChange={handleNickName}
            type="text"
            placeholder="Nickname"
            required
          />
        </SignUpForm>
        <SignUpForm>
          <input
            onChange={handleEmail}
            type="email"
            placeholder="Email"
            required
          />
        </SignUpForm>
        <SignUpForm>
          <input
            onChange={handlePassword}
            type="password"
            placeholder="Password"
            autoComplete="new-password"
            required
          />
        </SignUpForm>
        <ButtonDiv>
          <button type="button" onClick={handleSignUpButton}>
            Sign up
          </button>
        </ButtonDiv>
      </SignUpDiv>
      <MemberRestoreModal
        isOpen={memberRestoreModalOpen}
        closeModal={() => setMemberRestoreModalOpen(false)}
        email={email}
      />
      <LoginLink to="/login">로그인하러 가기</LoginLink>
      <SocialLoginDiv>
        <GoogleButton />
        <KakaoButton />
        <NaverButton />
      </SocialLoginDiv>
    </Container>
  );
};

const Container = styled.div`
  height: 100vh;
  background-color: #4994da;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;
const LogoDiv = styled.div`
  margin-bottom: 1rem;
  img {
    width: 10rem;
  }
`;

const SignUpDiv = styled.div`
  background-color: #ffffff;
  border-radius: 10px;
  width: 20rem;
  height: 30rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-bottom: 10px;

  button {
    color: #ffffff;
    background-color: #2759a2;
    &:hover {
      opacity: 85%;
    }
    &:active {
      opacity: 100%;
    }
  }
`;
const SignUpForm = styled.form`
  width: 75%;
  margin: 15px;
  input {
    width: 100%;
    padding: 10px;
  }
`;
const ButtonDiv = styled.div`
  margin-top: 1rem;
  width: 75%;
  display: flex;
  justify-content: space-between;
  button {
    width: 100%;
    height: 45px;
  }
`;
const LoginLink = styled(Link)`
  text-decoration-line: none;
  color: #ffffff;
  font-size: 11px;
`;
const SocialLoginDiv = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

export default SignUp;
