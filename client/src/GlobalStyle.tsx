import { createGlobalStyle } from "styled-components";

const GlobalStyle = createGlobalStyle`
  * {
    box-sizing: border-box;
    padding: 0;
    margin: 0; 
  }
  button {
    border-radius: 8px;
    border: 1px solid transparent;
    padding: 0.3em 1.2em;
    font-size: 1em;
    font-weight: 500;
    font-family: inherit;
    background-color: #ffffff;
    cursor: pointer;
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
    background-color: #e9e9e9;
  }
  a{
    text-decoration: none;
  }
`;

export default GlobalStyle;
