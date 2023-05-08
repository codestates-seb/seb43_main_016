import styled from "styled-components";
import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";
import logo from "../assets/edusync-logo.png";
import googleLogo from "../assets/google-icon.png";
import { validateEmptyInput } from "./utils/loginUtils";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleEmail = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };
  const handlePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const handleLoginButton = () => {
    if (validateEmptyInput(email) || validateEmptyInput(password)) {
      alert("이메일과 패스워드를 올바르게 입력했는지 확인해주세요!");
    } else {
      axios
        .post(`${import.meta.env.VITE_APP_API_URL}/members/login`, {
          email,
          password,
        })
        .then(() => navigate("/"))
        .catch((error) => {
          console.log(error);
          alert("이메일과 패스워드를 올바르게 입력했는지 확인해주세요!");
        });
    }
  };
  return (
    <Container>
      <LoginDiv>
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
          <div onClick={handleLoginButton}>
            <img src={googleLogo} alt="goole-login" />
          </div>
        </ButtonDiv>
      </LoginDiv>
      <SignUpLink to="/signup">회원가입하러 가기</SignUpLink>
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
    width: 71%;
    height: 45px;
  }
  img {
    width: 45px;
    border: 2px solid #e9e9e9;
    border-radius: 50%;
  }
`;
const SignUpLink = styled(Link)`
  text-decoration-line: none;
  color: #ffffff;
  font-size: 11px;
`;

export default Login;
