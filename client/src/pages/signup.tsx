import styled from "styled-components";
import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";
import { validateEmptyInput } from "./utils/loginUtils";
const SignUp = () => {
  const [nickName, setNickName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
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

  const handleSignupButton = () => {
    if (
      validateEmptyInput(nickName) ||
      validateEmptyInput(email) ||
      validateEmptyInput(password)
    )
      alert("닉네임과 이메일, 패스워드를 모두 입력해주세요!");
    else if (emailTest(email) === false) alert("이메일 형식이 잘못되었습니다.");
    else {
      axios
        .post(`${import.meta.env.VITE_APP_API_URL}/members`, {
          nickName,
          email,
          password,
        })
        .then(() => navigate("/login"))
        .catch((error) => {
          if (error.response.status === 500) {
            console.log(error.response.data.message);
            alert("이미 가입된 이메일 입니다.");
          } else {
            console.log(error);
          }
        })
        .finally(() => {});
    }
  };

  return (
    <Container>
      <SignUpDiv>
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
            required
          />
        </SignUpForm>
        <ButtonDiv>
          <button onClick={handleSignupButton}>Sign up</button>
          <div>구글</div>
        </ButtonDiv>
      </SignUpDiv>
      <LoginLink to="/login">로그인하러 가기</LoginLink>
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
const ButtonDiv = styled.div``;
const LoginLink = styled(Link)`
  text-decoration-line: none;
  color: #ffffff;
`;

export default SignUp;
