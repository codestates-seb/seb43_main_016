import { createGlobalStyle } from "styled-components";

const GlobalStyle = createGlobalStyle`
  * {
    box-sizing: border-box;
    padding: 0;
    margin: 0; 
  }
  button:hover{
    cursor: pointer;
  }
  input {
    background-color: #ffffff;
    padding: 6px;
    border: 1px solid #e9e9e9;
    border-radius: 3px;

    &:focus{
        outline: 1px solid #e9e9e9;
    }
  }
  body {
    font-family: "Helvetica", "Arial", sans-serif;
    line-height: 1.5;
    
  }
`;

export default GlobalStyle;
