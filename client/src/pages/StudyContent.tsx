import styled from "styled-components";
// import { useState } from "react";
import { Link } from "react-router-dom";
// import axios from "axios";

const StudyContent = () => {
  return (
    <StudyContentContainer>
      <StudyContentMain>
        <StudyContentTop>
          <h2>여러분의 스터디를 만들어보세요!</h2>
          <Link to="/">
            <StudyPostButton>스터디 모집!</StudyPostButton>
          </Link>
        </StudyContentTop>
      </StudyContentMain>
    </StudyContentContainer>
  );
};

const StudyContentContainer = styled.div`
  height: 100vh;
  background-color: #e0e0e0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const StudyContentMain = styled.div`
  width: 960px;
  height: 100vh;
  padding-top: 80px;
  background-color: #fff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const StudyContentTop = styled.div`
  width: 960px;
  display: flex;
  margin-bottom: 30px;
  justify-content: space-between;
  align-items: center;

  h2 {
    text-align: left;
    font-size: 2rem;
    font-weight: 700;
    margin-left: 20px;
  }
`;

const StudyPostButton = styled.button`
  width: 160px;
  height: 48px;
  font-size: 1.2rem;
  color: #ffffff;
  background-color: #4994da;
  margin-right: 20px;

  &:hover {
    opacity: 85%;
  }
  &:active {
    opacity: 100%;
  }
`;

export default StudyContent;
