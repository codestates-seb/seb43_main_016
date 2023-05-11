import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import "./index.css";
import { GoogleOAuthProvider } from "@react-oauth/google";
import { RecoilRoot } from "recoil";

const GoogleClientID = `${import.meta.env.GOOGLE_CLIENT_ID}`;
const GoogleClientPW = `${import.meta.env.GOOGLE_CLIENT_PW}`;

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <RecoilRoot>
    <GoogleOAuthProvider clientId={GoogleClientID}>
      <App />
    </GoogleOAuthProvider>
  </RecoilRoot>
);