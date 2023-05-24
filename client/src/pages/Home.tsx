import styled from "styled-components";
import { useNavigate } from "react-router-dom";

const Home = () => {
  const navigate = useNavigate();

  return (
    <HomeContainer>
      <TitlePage>
        <h1>
          스터디 통합 관리 앱, <span>EduSync</span>입니다.
        </h1>
        <p>어쩌구저쩌구 저쩌구어쩌구</p>
        <TitleButton>
          <button onClick={() => navigate("/login")}>
            로그인 화면으로 이동
          </button>
        </TitleButton>
      </TitlePage>
      <IntroPage>
        <div>바보</div>
        <div>멍청이</div>
      </IntroPage>
    </HomeContainer>
  );
};

// 스터디리스트: 어쩌구
// 캘린더: 저쩌구
// 동적인 효과?

export default Home;

const HomeContainer = styled.div`
  width: 100%;
  height: 100%;
  background-color: #0093e9;
  background-image: linear-gradient(160deg, #0093e9 0%, #80d0c7 100%);
  /* background: linear-gradient(to right, #EAECC6, #2BC0E4); */

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const TitlePage = styled.div`
  width: 100%;
  height: 900px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  h1 {
    font-size: 48px;
    color: #fff;
    margin-bottom: 30px;
    animation: fadein 2.4s;
  }
  h1 > span {
    color: #77ffef;
  }
  p {
    color: #fff;
    font-size: 16px;
    margin-bottom: 30px;
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

const IntroPage = styled.div`
  width: 100%;
  height: 900px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const TitleButton = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

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
