import { QueryClientProvider, QueryClient } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { myIdState } from "./recoil/atoms/myIdState";
import GlobalStyle from "./GlobalStyle";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";
import Profile from "./pages/Profile";
import Calender from "./pages/Calender";
import StudyList from "./pages/StudyList";
import StudyContent from "./pages/StudyContent";
import GNB from "./components/gnb/GNB";
import "./App.css";


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
            <Route path="/profile" element={<Profile />} />
            <Route path="/calendar" element={<Calender />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/studylist" element={<StudyList />} />
            <Route path="/studycontent" element={<StudyContent />} />
            <Route />
          </Routes>
        </BrowserRouter>
      </QueryClientProvider>
    </>
  );
}

export default App;
