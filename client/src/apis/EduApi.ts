import axios, { AxiosInstance } from "axios";

const eduApi: AxiosInstance = axios.create({
  baseURL: `${import.meta.env.VITE_APP_API_URL}`,
});

export default eduApi;
