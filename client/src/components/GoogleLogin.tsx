import googleLogo from "../assets/google-icon.png";
import styled from "styled-components";

function Google() {
  const googleHref = `${
    import.meta.env.VITE_APP_API_URL
  }/oauth2/authorization/google`;

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
    width: 45px;
    border: 2px solid #e9e9e9;
    border-radius: 50%;
  }
`;
export default Google;
