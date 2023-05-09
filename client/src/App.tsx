import { QueryClientProvider, QueryClient } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import GlobalStyle from "./GlobalStyle";
import GNB from "./components/gnb/GNB";
import Login from "./pages/login";
import SignUp from "./pages/signup";
import "./App.css";
import { useRecoilValue } from "recoil";
import { myIdState } from "./recoil/atoms/myIdState";

const queryClient = new QueryClient();

function App() {
  const myId = useRecoilValue(myIdState);
  return (
    <>
      <QueryClientProvider client={queryClient}>
        <BrowserRouter>
          <GNB />
          <GlobalStyle />
          <Routes>
            <Route path="/" element={<>{myId}</>} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<SignUp />} />
            <Route />
          </Routes>
        </BrowserRouter>
      </QueryClientProvider>
    </>
  );
}

export default App;
