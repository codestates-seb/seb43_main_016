import styled from "styled-components";
// import { useState } from "react";
// import { Link } from "react-router-dom";
// import axios from "axios";

const StudyListBox = () => {
  return (
    <StudyListBoxContainer>
      <StudyListBoxImage></StudyListBoxImage>
    </StudyListBoxContainer>
  );
};

const StudyListBoxContainer = styled.div`
  flex-basis: 280px;
  height: 320px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
  margin: 10px 20px;
`;
const StudyListBoxImage = styled.div``;

export default StudyListBox;
