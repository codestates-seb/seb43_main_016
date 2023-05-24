import styled from "styled-components";
// import { useNavigate } from "react-router-dom";

const Home = () => {
  return (
    <HomeContainer>
      <HomeTitle>
        <h1>
          스터디 통합 관리 앱, <span>EduSync</span>입니다.
        </h1>
        <p>어쩌구저쩌구 저쩌구어쩌구</p>
      </HomeTitle>
      <HomeButton>
        <button>로그인 화면으로 이동</button>
      </HomeButton>
    </HomeContainer>
  );
};

// 스터디리스트: 어쩌구
// 캘린더: 저쩌구
// 동적인 효과?

export default Home;

const HomeContainer = styled.div`
  width: 100%;
  height: 100vh;
  background-color: #0093e9;
  background-image: linear-gradient(160deg, #0093e9 0%, #80d0c7 100%);

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const HomeTitle = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  h1 {
    font-size: 48px;
    color: #fff;
    margin-bottom: 30px;
  }
  h1 > span {
    color: #9be3ff;
  }
  p {
    color: #fff;
    font-size: 16px;
    margin-bottom: 30px;
  }
`;

const HomeButton = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  button {
    border: solid 3px #fff;
    border-radius: none;
    background-color: transparent;
    color: #fff;
    font-size: 16px;
  }
`;
