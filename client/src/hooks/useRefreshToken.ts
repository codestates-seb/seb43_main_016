import { useEffect } from "react";
import tokenRequestApi from "../apis/TokenRequestApi";
import eduApi from "../apis/EduApi";
import { useState } from "react";
import { getRefreshToken } from "../pages/utils/Auth";
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { LogInState } from "../recoil/atoms/LogInState";

function useRefreshToken() {
  const [isLoginState, setIsLoggedIn] = useRecoilState(LogInState);
  const [fetched, setFetched] = useState(false);
  const refreshToken = getRefreshToken();
  const navigate = useNavigate();

  useEffect(() => {
    if (isLoginState) setFetched(false);

    if (isLoginState && refreshToken === null) {
      setIsLoggedIn(false);
      setFetched(false);
      navigate("/login");
    }
    if (refreshToken) {
      eduApi
        .post("/refresh", null, {
          headers: {
            refresh: `${refreshToken}`,
          },
        })
        .then((res) => {
          tokenRequestApi.setAccessToken(res.headers.authorization);
          setFetched(true);
        });
    }
  }, []);
  return fetched;
}

export default useRefreshToken;
