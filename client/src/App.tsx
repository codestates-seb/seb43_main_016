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
import useRefreshToken from "./hooks/useRefreshToken";
const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <Router>
        <AppContent />
      </Router>
    </QueryClientProvider>
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
