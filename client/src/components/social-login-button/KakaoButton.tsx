import kakaoLogo from "../assets/kakao-icon.png";
import styled from "styled-components";
import { socialLoginApi } from "../../apis/EduApi";

function KakaoButton() {
  const kakaoHref = `${socialLoginApi}/kakao`;

  return (
    <div>
      <KakaoLoginButton>
        <a href={kakaoHref}>
          <img src={kakaoLogo} alt="kakao-login" />
        </a>
      </KakaoLoginButton>
    </div>
  );
}

const KakaoLoginButton = styled.div`
  img {
    width: 45px;
    border-radius: 50%;
    margin: 15px;
  }
`;
export default KakaoButton;
