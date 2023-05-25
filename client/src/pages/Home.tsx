import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { LogInState } from "../recoil/atoms/LogInState";

import { TbUsers } from "react-icons/tb";
import { TbNotebook } from "react-icons/tb";
import { TbCalendar } from "react-icons/tb";

const Home = () => {
  const navigate = useNavigate();
  const isLoggedIn = useRecoilValue(LogInState);

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
          {isLoggedIn ? (
            <button onClick={() => navigate("/studylist")}>
              모두의 스터디 보러가기
            </button>
          ) : (
            <button onClick={() => navigate("/login")}>
              로그인 화면으로 이동
            </button>
          )}
        </TitleButton>
      </TitlePage>
      <IntroPage>
        <h2>동료와 함께하는 계획적인 스터디</h2>
        <IntroMain>
          <IntroBox>
            <TbUsers size="160" color="#fff" />
            <h3>스터디 동료 모집</h3>
            <p>
              함께 학습할 동료를 모아보세요.
              <br />
              스터디 모집글을 작성하여 팀원을 모으고, 원하는 스터디를 찾아 가입
              신청할 수 있습니다.
            </p>
          </IntroBox>
          <IntroBox>
            <TbNotebook size="160" color="#fff" />
            <h3>스터디 관리</h3>
            <p>
              스터디장이 되어 스터디원 목록을 관리할 수 있고, 스터디에 관련된
              정보를 팀원들끼리 확인할 수 있습니다.
            </p>
          </IntroBox>
          <IntroBox>
            <TbCalendar size="160" color="#fff" />
            <h3>일정 캘린더</h3>
            <p>
              가입한 스터디의 날짜, 요일, 시간이 자동으로 표시되어 효율적인
              스터디 일정 관리가 가능합니다.
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
    animation: fadein 5s;
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
    animation: fadein 2.4s;
  }
  > button:hover {
    color: #0093e9;
    background-color: #fff;
    transition: all ease-in 0.5s;
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
    margin-bottom: 30px;
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
  height: 500px;
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
    width: 240px;
    height: 200px;
    font-size: 16px;
    color: #fff;
  }
`;
