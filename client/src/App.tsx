import { BrowserRouter, Routes, Route } from "react-router-dom";
import GlobalStyle from "./GlobalStyle";
import StudyList from "./pages/StudyList";
import StudyContent from "./pages/StudyContent";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";
import "./App.css";

function App() {
  return (
    <>
      <BrowserRouter>
        <GlobalStyle />
        <Routes>
          <Route path="/" element={<StudyList />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/studycontent" element={<StudyContent />} />
          <Route />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
