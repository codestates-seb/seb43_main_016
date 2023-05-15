import googleLogo from "../assets/google-icon.png";
import styled from "styled-components";
import { useQuery } from "@tanstack/react-query";

function Google() {
  const googleHref = `${
    import.meta.env.VITE_APP_API_URL
  }/oauth2/authorization/google`;
  /*
  const { data, error } = useQuery(["tokens"], async () => {
    const response = await fetch(googleHref, {
      headers: {
        "Content-Type": "application/json",
      },
    });
    const headers = response.headers;
    const accessToken = headers.get("access-token");
    const refreshToken = headers.get("refresh-token");
    if (!accessToken || !refreshToken) {
      throw new Error("Access token or refresh token not found");
    }
    return { accessToken, refreshToken };
  });

  if (error) {
    console.log(error);
  }

  if (data) {
    localStorage.setItem("accessToken", data.accessToken);
    localStorage.setItem("refreshToken", data.refreshToken);
  }
*/
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
