import { BrowserRouter, Routes, Route } from "react-router-dom";
import GlobalStyle from "./GlobalStyle";
import GNB from "./components/gnb/GNB";
import Login from "./pages/login";
import SignUp from "./pages/signup";
import "./App.css";

function App() {
  return (
    <>
      <BrowserRouter>
        <GNB />
        <GlobalStyle />
        <Routes>
          <Route path="/" element={<>home</>} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
