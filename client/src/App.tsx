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
import Redirect from "./pages/Redirect";
import useRefreshToken from "./hooks/useRefreshToken";
import Modal from "react-modal";
import Home from "./pages/Home";
import HTestPage from "./pages/HTestPage";


// import { worker } from "./mocks/browser";
// // 개발 모드로 실행되었을 때, mocking 라이브러리가 실행되도록 명시하는 코드
// if (process.env.NODE_ENV === "development") {
//   worker.start();
// }

const queryClient = new QueryClient();

Modal.setAppElement("#root");
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
  const fetched = useRefreshToken(); // 이 코드 때문에, 개발서버에서 렌더링이 되지 않고 있음!
  return (
    <>
      {fetched && (
        <>
          <GNB />
          <GlobalStyle />
          <Routes>
            <Route
              path="/"
              element={
                <>
                  <Home />
                </>
              }
            />
            <Route path="/profile/*" element={<Profile />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/oauth/redirect" element={<Redirect />} />
            <Route path="/studylist" element={<StudyList />} />
            <Route path="/studycontent" element={<StudyContent />} />
            <Route path="/studypost" element={<StudyPost />} />
            <Route path="/calendar" element={<ProfileCalendar />} />
            <Route path="/test" element={<TestPage />} />
            <Route path="/haeun" element={<HTestPage />} />
            <Route />
          </Routes>
        </>
      )}
    </>
  );
}
export default App;
