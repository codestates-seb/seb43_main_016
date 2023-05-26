import axios, { AxiosInstance } from "axios";

export const eduApi: AxiosInstance = axios.create({
  baseURL: `${import.meta.env.VITE_APP_API_URL}`,
  headers: {
    "Content-Type": "application/json",
  },
});

export const socialLoginApi = `${
  import.meta.env.VITE_APP_API_URL
}/oauth2/authorization`;
