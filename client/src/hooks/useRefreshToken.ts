import { useEffect } from "react";
import tokenRequestApi from "../apis/TokenRequestApi";
import eduApi from "../apis/EduApi";
import { getRefreshToken } from "../pages/utils/Auth";
import { useNavigate } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import { LogInState } from "../recoil/atoms/LogInState";

function useRefreshToken() {
  const setIsLoggedIn = useSetRecoilState(LogInState);
  const refreshToken = getRefreshToken();
  const navigate = useNavigate();
  useEffect(() => {
    if (refreshToken === null) {
      setIsLoggedIn(false);
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

          //setIsLoggedIn(true);
        });
    }
  }, []);
}

export default useRefreshToken;
