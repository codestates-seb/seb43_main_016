import { GoogleLogin, useGoogleLogin } from "@react-oauth/google";
import googleLogo from "../assets/google-icon.png";

import styled from "styled-components";
function Google() {
  const login = useGoogleLogin({
    onSuccess: (tokenResponse) => console.log(tokenResponse),
    onError: () => console.log("Login Failed"),
  });

  return (
    <div>
      <GoogleLoginButton onClick={() => login()}>
        <img src={googleLogo} alt="goole-login" />
      </GoogleLoginButton>
    </div>
  );
}
const GoogleLoginButton = styled.div`
  img {
    width: 45px;
    border: 2px solid #e9e9e9;
    border-radius: 50%;
  }
`;
export default Google;
