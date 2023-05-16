import axios, { AxiosInstance } from "axios";
import eduApi from "./EduApi";
import { getRefreshToken } from "../pages/utils/Auth";

let accessToken: string | null = null;
const tokenRequestApi: AxiosInstance = axios.create({
  baseURL: `${import.meta.env.VITE_APP_API_URL}`,
});

tokenRequestApi.interceptors.request.use(
  (config) => {
    config.headers = config.headers || {};
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

const extendAccessToken = async () => {
  const expirationTime = 60 * 1000; // 1분
  const timeToExpire =
    new Date(Number(new Date()) + expirationTime).getTime() -
    new Date().getTime();

  // 1분 미만일 때 자동으로 accessToken을 갱신
  setTimeout(async () => {
    const refreshToken = getRefreshToken();

    try {
      const response = await eduApi.post(`/refresh`, null, {
        headers: {
          Refresh: `${refreshToken}`,
        },
      });

      const { accessToken: newAccessToken } = response.data;
      tokenRequestApi.setAccessToken(newAccessToken);

      console.log("accessToken 갱신됨");
    } catch (error) {
      console.error("accessToken 갱신 실패:", error);
    }
  }, timeToExpire);
};

tokenRequestApi.setAccessToken = (token): void => {
  accessToken = token;
  extendAccessToken();
};

export default tokenRequestApi;
