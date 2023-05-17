import naverLogo from "../../assets/naver-icon.png";
import styled from "styled-components";
import { socialLoginApi } from "../../apis/EduApi";

function NaverButton() {
  const naverHref = `${socialLoginApi}/naver`;

  return (
    <div>
      <NaverLoginButton>
        <a href={naverHref}>
          <img src={naverLogo} alt="naver-login" />
        </a>
      </NaverLoginButton>
    </div>
  );
}

const NaverLoginButton = styled.div`
  img {
    width: 47px;
    border-radius: 50%;
    margin: 15px;
  }
`;
export default NaverButton;
