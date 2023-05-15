import { QueryClientProvider, QueryClient } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import GlobalStyle from "./GlobalStyle";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";
import Profile from "./pages/Profile";
import StudyPost from "./pages/StudyPost";
import "./App.css";
import StudyList from "./pages/StudyList";
import StudyContent from "./pages/StudyContent";
import GNB from "./components/gnb/GNB";
import ProfileCalendar from "./pages/ProfileCalendar";
import { useRecoilValue } from "recoil";
import { LogInState } from "./recoil/atoms/LogInState";
import Redirect from "./pages/Redirect";
const queryClient = new QueryClient();

// 개발 모드로 실행되었을 때, mocking 라이브러리가 실행되도록 명시하는 코드
if (process.env.NODE_ENV === "development") {
  import("./mocks/browser").then(({ worker }) => {
    worker.start();
  });
}

function App() {
  const logInState = useRecoilValue(LogInState);
  return (
    <>
      <QueryClientProvider client={queryClient}>
        <BrowserRouter>
          <GNB />
          <GlobalStyle />
          <Routes>
            <Route
              path="/"
              element={<>{console.log("loginState", logInState)}</>}
            />
            <Route path="/profile/*" element={<Profile />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/oauth/redirect" element={<Redirect />} />
            <Route path="/studylist" element={<StudyList />} />
            <Route path="/studycontent" element={<StudyContent />} />
            <Route path="/studypost" element={<StudyPost />} />
            <Route path="/calendar" element={<ProfileCalendar />} />
          </Routes>
        </BrowserRouter>
      </QueryClientProvider>
    </>
  );
}

export default App;
