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
    if (!isLoginState) setFetched(true);
    console.log("훅", fetched);

    if (isLoginState && refreshToken === null) {
      console.log("훅1");

      setIsLoggedIn(false);
      setFetched(true);
      navigate("/login");
    }
    if (refreshToken) {
      console.log("훅2");

      eduApi
        .post("/refresh", null, {
          headers: {
            refresh: `${refreshToken}`,
          },
        })
        .then((res) => {
          console.log("훅3");

          tokenRequestApi.setAccessToken(res.headers.authorization);
          setFetched(true);
        });
    }
  }, []);
  return fetched;
}

export default useRefreshToken;
