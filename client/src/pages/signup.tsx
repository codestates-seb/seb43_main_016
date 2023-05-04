import styled from "styled-components";
import { useState } from "react";
import axios from "axios";
console.log(process.env.REACT_APP_API_URL);
const Signup = () => {
  const [nickName, setNickName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const handleNickName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickName(e.target.value);
  };
  const handleEmail = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };
  const handlePwd = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const handleSignupBtn = () => {
    if (!nickName || !email || !password)
      alert("닉네임과 이메일, 패스워드를 모두 입력해주세요!");
    else {
      axios
        .post(`${process.env.REACT_APP_API_URL}/members`, {
          nickName,
          email,
          password,
        })
        .then(() => (document.location.href = "/login"))
        .catch((error) => {
          if (error.response.status === 500) {
            console.log(error.response.data.message);
            alert("이미 가입된 이메일 입니다.");
          } else {
            console.log(error);
          }
        });
    }
  };

  return (
    <Container>
      <SignupDiv>
        <SignupForm>
          <input
            onChange={handleNickName}
            type="text"
            placeholder="Nickname"
            required
          />
        </SignupForm>
        <SignupForm>
          <input
            onChange={handleEmail}
            type="email"
            placeholder="Email"
            required
          />
        </SignupForm>
        <SignupForm>
          <input
            onChange={handlePwd}
            type="password"
            placeholder="Password"
            required
          />
        </SignupForm>
        <BtnDiv>
          <button onClick={handleSignupBtn}>Sign up</button>
          <div>구글</div>
        </BtnDiv>
      </SignupDiv>
    </Container>
  );
};

const Container = styled.div`
  background-color: blue;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;
const SignupDiv = styled.div`
  background-color: #ffffff;
  width: 20rem;
  height: 30rem;

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
const SignupForm = styled.div`
  margin: 15px;
`;
const BtnDiv = styled.div``;

export default Signup;
