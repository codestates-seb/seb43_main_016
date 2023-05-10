import { BrowserRouter, Routes, Route } from "react-router-dom";
import GlobalStyle from "./GlobalStyle";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";
import Profile from "./pages/Profile";
import Calender from "./pages/Calender";
import StudyList from "./pages/StudyList";
import StudyContent from "./pages/StudyContent";
import MyStudyManage from "./pages/ProfileStudyManage";

import "./App.css";

function App() {
  return (
    <>
      <BrowserRouter>
        <GlobalStyle />
        <Routes>
          <Route path="/profile/*" element={<Profile />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/calendar" element={<Calender />} />
          <Route path="/studylist" element={<StudyList />} />
          <Route path="/studycontent" element={<StudyContent />} />
          <Route path="/studymanage" element={<MyStudyManage />} />
          <Route />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
