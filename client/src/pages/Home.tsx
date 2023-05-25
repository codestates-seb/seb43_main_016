import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { TbUsers } from "react-icons/tb";
import { TbNotebook } from "react-icons/tb";
import { TbCalendar } from "react-icons/tb";

const Home = () => {
  const navigate = useNavigate();

  return (
    <HomeContainer>
      <TitlePage>
        <h1>
          스터디 통합 관리 앱, <span>EduSync</span>입니다.
        </h1>
        <p>
          EduSync는 여러분이 스터디를 함께할 동료를 만나고
          <br />
          일정을 효과적으로 관리할 수 있도록 도와드립니다.
        </p>
        <TitleButton>
          <button onClick={() => navigate("/login")}>
            로그인 화면으로 이동
          </button>
        </TitleButton>
      </TitlePage>
      <IntroPage>
        <h2>소개 제목</h2>
        <IntroMain>
          <IntroBox>
            <TbUsers size="160" color="#fff" />
            <h3>스터디 동료 모집</h3>
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
              eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
              enim ad minim veniam, quis nostrud exercitation ullamco laboris
              nisi ut aliquip ex ea commodo c
            </p>
          </IntroBox>
          <IntroBox>
            <TbNotebook size="160" color="#fff" />
            <h3>스터디 관리</h3>
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
              eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
              enim ad minim veniam, quis nostrud exercitation ullamco laboris
              nisi ut aliquip ex ea commodo c
            </p>
          </IntroBox>
          <IntroBox>
            <TbCalendar size="160" color="#fff" />
            <h3>일정 캘린더</h3>
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
              eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
              enim ad minim veniam, quis nostrud exercitation ullamco laboris
              nisi ut aliquip ex ea commodo c
            </p>
          </IntroBox>
        </IntroMain>
      </IntroPage>
    </HomeContainer>
  );
};

export default Home;

const HomeContainer = styled.div`
  width: 100%;
  height: 100%;
  background-color: #0093e9;
  background-image: linear-gradient(160deg, #0093e9 0%, #80d0c7 100%);

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const TitlePage = styled.div`
  width: 960px;
  height: 900px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  h1 {
    font-size: 48px;
    color: #fff;
    margin-bottom: 28px;
    animation: fadein 2.4s;
  }
  h1 > span {
    color: #77ffef;
  }
  p {
    color: #fff;
    font-size: 18px;
    font-weight: 500;
  }

  @keyframes fadein {
    from {
      opacity: 0;
    }
    to {
      opacity: 1;
    }
  }
`;

const TitleButton = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 50px;

  > button {
    border: solid 3px #fff;
    border-radius: 0;
    background-color: transparent;
    padding: 7px 12px;
    color: #fff;
    font-size: 20px;
    font-weight: 700;
  }
  > button:hover {
    color: #0093e9;
    background-color: #fff;
    transition: all ease-in 0.3s;
  }
`;

const IntroPage = styled.div`
  width: 100%;
  height: 900px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  h2 {
    font-size: 40px;
    color: #fff;
    margin-bottom: 24px;
  }
`;

const IntroMain = styled.div`
  width: 960px;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const IntroBox = styled.div`
  width: 300px;
  height: 600px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  h3 {
    font-size: 28px;
    color: #fff;
    margin: 24px 0 20px;
  }
  p {
    width: 280px;
    font-size: 16px;
    color: #fff;
  }
`;
