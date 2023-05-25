import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import tokenRequestApi from "../apis/TokenRequestApi";
import { LogInState } from "../recoil/atoms/LogInState";
import { useSetRecoilState } from "recoil";

function Redirect() {
  const navigate = useNavigate();
  const setIsLoggedIn = useSetRecoilState(LogInState);

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const accessToken = urlParams.get("access_token");
    const refreshToken = urlParams.get("refresh_token");

    if (accessToken && refreshToken) {
      tokenRequestApi.setAccessToken(accessToken);
      localStorage.setItem("refreshToken", refreshToken);
      setIsLoggedIn(true);
      navigate("/");
    }
    navigate("/");
  }, []);

  return <div>로그인 중입니다...</div>;
}

export default Redirect;
