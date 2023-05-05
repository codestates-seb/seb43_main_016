import styled from "styled-components";
import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";
import { validateEmptyInput } from "./utils/loginUtils";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleEmail = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };
  const handlePwd = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const handleLoginBtn = () => {
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
            onChange={handlePwd}
            type="password"
            placeholder="Password"
            required
          />
        </LoginForm>
        <BtnDiv>
          <button onClick={handleLoginBtn}>Log In</button>
          <div>구글</div>
        </BtnDiv>
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
const BtnDiv = styled.div``;
const SignUpLink = styled(Link)`
  text-decoration-line: none;
  color: #ffffff;
`;

export default Login;
