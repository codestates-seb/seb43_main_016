import { BrowserRouter, Routes, Route } from "react-router-dom";
import GlobalStyle from "./GlobalStyle";
import Login from "./pages/Login";
import SignUp from "./pages/signup";
import Profile from "./pages/Profile";
import Calender from "./pages/Calender";
import "./App.css";

function App() {
  return (
    <>
      <BrowserRouter>
        <GlobalStyle />
        <Routes>
          <Route path="/profile" element={<Profile />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/calender" element={<Calender />} />
          <Route />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
