import { BrowserRouter, Routes, Route } from "react-router-dom";
import GlobalStyle from "./GlobalStyle";
import Login from "./pages/login";
import Signup from "./pages/signup";
import "./App.css";

function App() {
  return (
    <>
      <BrowserRouter>
        <GlobalStyle />
        <Routes>
          <Route path="/" element={<>home</>} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
