import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
function Redirect() {
  const navigate = useNavigate();
  useEffect(() => {
    // URL에서 Access Token 추출
    const urlParams = new URLSearchParams(window.location.search);
    const accessToken = urlParams.get("access_token");
    const refreshToken = urlParams.get("refresh_token");

    // Access Token이 유효한 경우, 로컬 스토리지에 저장
    if (accessToken && refreshToken) {
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);
    }

    // 로그인 후에 메인 페이지로 리다이렉트
    navigate("/");
  }, []);

  return <div>로그인 중입니다...</div>;
}

export default Redirect;
