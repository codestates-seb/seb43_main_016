import styled from "styled-components";
// import { useState } from "react";
import { Link } from "react-router-dom";
// import axios from "axios";

const StudyListBox = () => {
  return (
    <StudyListBoxContainer>
      <Link to="/">
        <StudyListImage></StudyListImage>
        <StudyListTitle>
          <h3>TypeScript 스터디 모집</h3>
        </StudyListTitle>
      </Link>
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
  display: flex;
  flex-flow: column wrap;
  justify-content: center;
  align-items: center;
`;

const StudyListImage = styled.div`
  width: 260px;
  height: 180px;
  background-color: aliceblue;
`;

const StudyListTitle = styled.div`
  width: 260px;
  padding: 15px 0;
  display: flex;
  justify-content: flex-start;
  align-items: center;

  h3 {
    font-size: 1.125rem;
    font-weight: 700;
  }
`;

export default StudyListBox;
