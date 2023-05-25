import axios, { AxiosInstance } from "axios";
import { eduApi } from "./EduApi";
import { getRefreshToken } from "../pages/utils/Auth";

let accessToken: string | null = null;
let tokenRequestApi = axios.create({
  baseURL: `${import.meta.env.VITE_APP_API_URL}`,
  headers: {
    "Content-Type": "application/json", // 요청 헤더(content type) 설정
  },
}) as AxiosInstance & { setAccessToken: (token: string | null) => void };

tokenRequestApi.setAccessToken = (token: string | null): void => {
  if (token) {
    accessToken = token;
    extendAccessToken();
  }
};

tokenRequestApi.interceptors.request.use(
  (config) => {
    config.headers = config.headers || {};
    if (accessToken) {
      config.headers.authorization = `${accessToken}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

const extendAccessToken = async () => {
  const expirationTime = 4 * 60 * 1000;
  const timeToExpire =
    new Date(Number(new Date()) + expirationTime).getTime() -
    new Date().getTime();

  setTimeout(async () => {
    const refreshToken = getRefreshToken();
    if (!refreshToken) return;

    try {
      const response = await eduApi.post(`/refresh`, null, {
        headers: {
          Refresh: `${refreshToken}`,
        },
      });
      const { authorization: newAccessToken } = response.headers;
      tokenRequestApi.setAccessToken(newAccessToken);
      console.log("accessToken 갱신됨");
    } catch (error) {
      console.error("accessToken 갱신 실패:", error);
    }
  }, timeToExpire);
};

export default tokenRequestApi;
