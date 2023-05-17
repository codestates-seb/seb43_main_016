import googleLogo from "../assets/google-icon.png";
import styled from "styled-components";
import { socialLoginApi } from "../../apis/EduApi";

function GoogleButton() {
  const googleHref = `${socialLoginApi}/google`;

  return (
    <div>
      <GoogleLoginButton>
        <a href={googleHref}>
          <img src={googleLogo} alt="goole-login" />
        </a>
      </GoogleLoginButton>
    </div>
  );
}

const GoogleLoginButton = styled.div`
  img {
    width: 50px;
    border-radius: 50%;
    margin: 15px;
  }
`;
export default GoogleButton;
