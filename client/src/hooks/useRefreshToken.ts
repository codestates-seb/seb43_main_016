import { useEffect } from "react";
import tokenRequestApi from "../apis/TokenRequestApi";
import { eduApi } from "../apis/EduApi";
import { useState } from "react";
import { getRefreshToken } from "../pages/utils/Auth";
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { LogInState } from "../recoil/atoms/LogInState";
import { removeTokens } from "../pages/utils/Auth";

function useRefreshToken() {
  const [isLoginState, setIsLoggedIn] = useRecoilState(LogInState);
  const [fetched, setFetched] = useState(false);
  const refreshToken = getRefreshToken();
  const navigate = useNavigate();

  useEffect(() => {
    if (!isLoginState) setFetched(true);

    if (!isLoginState && refreshToken) {
      removeTokens();
      setFetched(true);
      navigate("/login");
    }
    if (
      isLoginState &&
      (refreshToken === null || refreshToken === "undefined")
    ) {
      removeTokens();
      setIsLoggedIn(false);
      setFetched(true);
      navigate("/login");
    } else if (refreshToken) {
      eduApi
        .post("/refresh", null, {
          headers: {
            refresh: `${refreshToken}`,
          },
        })
        .then((res) => {
          tokenRequestApi.setAccessToken(res.headers.authorization);
          setFetched(true);
        })
        .catch((err) => {
          if (err.response.status > 299) {
            removeTokens();
            setIsLoggedIn(false);
            setFetched(true);
            navigate("/login");
            alert("토큰이 만료되었습니다. 재로그인을 시도해주세요!");
          }
        });
    }
  }, []);
  return fetched;
}

export default useRefreshToken;
