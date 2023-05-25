import styled from "styled-components";
import { useState } from "react";
import { useMutation } from "@tanstack/react-query";
import { useNavigate, Link } from "react-router-dom";
import logo from "../assets/edusync-logo.png";
import tokenRequestApi from "../apis/TokenRequestApi";
import { AxiosResponse, AxiosError } from "axios";
import { validateEmptyInput } from "./utils/loginUtils";
import { useSetRecoilState } from "recoil";
import { LogInState } from "../recoil/atoms/LogInState";
import { setRefreshToken } from "./utils/Auth";
import GoogleButton from "../components/social-login-button/GoogleButton";
import KakaoButton from "../components/social-login-button/KakaoButton";
import NaverButton from "../components/social-login-button/NaverButton";
import MemberRestoreModal from "../components/modal/MemberRestoreModal";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [memberRestoreModalOpen, setMemberRestoreModalOpen] = useState(false);
  const setIsLoggedIn = useSetRecoilState(LogInState);

  const navigate = useNavigate();

  const handleEmail = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };

  const handlePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const loginMutation = useMutation<AxiosResponse, AxiosError>(
    () =>
      tokenRequestApi.post("/members/login", {
        email,
        password,
      }),
    {
      onSuccess: (data) => {
        setIsLoggedIn(true);
        const accessToken = data.headers.authorization;
        const refreshToken = data.headers.refresh;
        tokenRequestApi.setAccessToken(accessToken);
        setRefreshToken(refreshToken);
        navigate("/");
      },
      onError: (error) => {
        if (error.message === "Request failed with status code 403") {
          setMemberRestoreModalOpen(true);
        } else {
          alert("이메일과 패스워드를 올바르게 입력했는지 확인해주세요!!");
        }
      },
    }
  );

  const handleLoginButton = () => {
    if (validateEmptyInput(email) || validateEmptyInput(password)) {
      alert("이메일과 패스워드를 올바르게 입력했는지 확인해주세요!");
    } else {
      loginMutation.mutate();
    }
  };

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      e.preventDefault();
      handleLoginButton();
    }
  };

  //  const handleGoogleLogin = () => {
  // Google 로그인 처리
  // };

  return (
    <Container>
      <LoginDiv onKeyDown={handleKeyDown}>
        <LogoDiv>
          <img src={logo} />
        </LogoDiv>
        <LoginForm>
          <input
            onChange={handleEmail}
            type="email"
            placeholder="Email"
            required
          />
        </LoginForm>
        <LoginForm>
          <input
            onChange={handlePassword}
            type="password"
            placeholder="Password"
            required
          />
        </LoginForm>
        <ButtonDiv>
          <button onClick={handleLoginButton}>Log In</button>
        </ButtonDiv>
      </LoginDiv>
      <MemberRestoreModal
        isOpen={memberRestoreModalOpen}
        closeModal={() => setMemberRestoreModalOpen(false)}
        email={email}
      />
      <SignUpLink to="/signup">회원가입하러 가기</SignUpLink>
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
  img {
    width: 10rem;
  }
`;
const LoginDiv = styled.div`
  background-color: #ffffff;
  border-radius: 10px;
  width: 20rem;
  height: 20rem;
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
const LoginForm = styled.form`
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
  img {
  }
`;
const SignUpLink = styled(Link)`
  text-decoration-line: none;
  color: #ffffff;
  font-size: 11px;
`;
const SocialLoginDiv = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

export default Login;
