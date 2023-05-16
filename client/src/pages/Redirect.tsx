import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import tokenRequestApi from "../apis/TokenRequestApi";

function Redirect() {
  const navigate = useNavigate();
  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const accessToken = urlParams.get("access_token");
    const refreshToken = urlParams.get("refresh_token");

    if (accessToken && refreshToken) {
      tokenRequestApi.setAccessToken(accessToken);
      localStorage.setItem("refreshToken", refreshToken);
    }

    // 로그인 후에 메인 페이지로 리다이렉트
    navigate("/");
  }, []);

  return <div>로그인 중입니다...</div>;
}

export default Redirect;
