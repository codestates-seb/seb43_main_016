import { BrowserRouter, Routes, Route } from "react-router-dom";
import GlobalStyle from "./GlobalStyle";
import Login from "./pages/login";
import SignUp from "./pages/signup";
import Profile from "./pages/profile";
import Calender from "./pages/calender";
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
