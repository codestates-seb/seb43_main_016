import styled from "styled-components";
import { useState } from "react";
import { useEffect } from "react";
import { useMutation } from "@tanstack/react-query";
import jwtDecode from "jwt-decode";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";
import logo from "../assets/edusync-logo.png";
import googleLogo from "../assets/google-icon.png";
import { validateEmptyInput } from "./utils/loginUtils";
import { setAccessToken, setRefreshToken } from "./utils/Auth";
import { useSetRecoilState } from "recoil";
import { myIdState } from "../recoil/atoms/myIdState";

interface User {
  id: number;
  nickName: string;
  email: string;
  password: string;
}

const Login = () => {
  const [isLoading, setIsLoading] = useState(false);

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [members, setMembers] = useState<User[]>([]);
  const setMyId = useSetRecoilState(myIdState);
  const navigate = useNavigate();

  const handleEmail = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };

  const handlePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const loginMutation = useMutation(
    () =>
      axios.post(`${import.meta.env.VITE_APP_API_URL}/members/login`, {
        email,
        password,
      }),
    {
      onSuccess: (data) => {
        const accessToken = data.headers.authorization;
        const refreshToken = data.headers.refresh;
        setAccessToken(accessToken);
        setRefreshToken(refreshToken);
        const decodedToken = jwtDecode<any>(accessToken);
        const foundMember = members.find(
          (member) => member.email === decodedToken.email
        );
        setMyId(foundMember ? foundMember.id : 0);

        navigate("/");
      },
      onError: (error) => {
        console.error(error);
        alert("이메일과 패스워드를 올바르게 입력했는지 확인해주세요!!");
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

  const handleKeyDown = (event: React.KeyboardEvent<HTMLButtonElement>) => {
    if (event.key === "Enter") {
      event.preventDefault();
      handleLoginButton();
    }
  };

  //  const handleGoogleLogin = () => {
  // Google 로그인 처리
  // };
  useEffect(() => {
    setIsLoading(true);

    axios
      .get(`${import.meta.env.VITE_APP_API_URL}/members?page=1&size=10`)
      .then((res) => {
        // pageInfo 객체에서 totalElements 값을 size 변수에 할당한다.
        const {
          pageInfo: { totalElements: size },
        } = res.data;
        // 이후 size 값을 사용하여 처리를 수행한다.

        axios
          .get<User[]>(
            `${import.meta.env.VITE_APP_API_URL}/members?page=1&size=${size}`
          )
          .then((res) => {
            setMembers(Object.values(res.data));
            console.log(members);

            setIsLoading(false);
          });
      });
  }, []);

  return (
    <Container>
      {isLoading ? (
        <></>
      ) : (
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
            <button onClick={handleLoginButton} onKeyDown={handleKeyDown}>
              Log In
            </button>
            <div onClick={handleLoginButton}>
              <img src={googleLogo} alt="goole-login" />
            </div>
          </ButtonDiv>
        </LoginDiv>
      )}

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
