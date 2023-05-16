import { QueryClientProvider, QueryClient } from "@tanstack/react-query";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
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
<<<<<<< HEAD
import { worker } from "./mocks/browser";
import Modal from "react-modal";
=======
import useRefreshToken from "./hooks/useRefreshToken";
>>>>>>> 07650a94e7d83111bd34b6e152fb8d16e31faf84
const queryClient = new QueryClient();

// 개발 모드로 실행되었을 때, mocking 라이브러리가 실행되도록 명시하는 코드
if (process.env.NODE_ENV === "development") {
  worker.start();
}
Modal.setAppElement("#root");
function App() {
  return (
<<<<<<< HEAD
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
=======
    <QueryClientProvider client={queryClient}>
      <Router>
        <AppContent />
      </Router>
    </QueryClientProvider>
>>>>>>> 07650a94e7d83111bd34b6e152fb8d16e31faf84
  );
}

function AppContent() {
  const logInState = useRecoilValue(LogInState);
  useRefreshToken();

  return (
    <>
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
        <Route />
      </Routes>
    </>
  );
}
export default App;
