import styled from "styled-components";
// import { useState } from "react";
import { Link } from "react-router-dom";
// import axios from "axios";
import StudyComment from "../components/StudyComment";

const StudyContent = () => {
  return (
    <StudyContentContainer>
      <StudyContentBody>
        <StudyContentTop>
          <span>모집중</span>
          <StudyContentTitle>
            <h2>타입스크립트 스터디 모집</h2>
            <StudyContentEdit>
              <div>수정</div>
              <div>삭제</div>
            </StudyContentEdit>
          </StudyContentTitle>
        </StudyContentTop>
        <StudyContentMain>
          <StudyContentInfo>
            <div>일정</div>
            <span>2023. 05. 01 ~ 2023. 07. 01</span>
          </StudyContentInfo>
          <StudyContentInfo>
            <div>시각</div>
            <span>월, 수, 금 20:30 ~ 22:30</span>
          </StudyContentInfo>
          <StudyContentInfo>
            <div>최대 인원</div>
            <span>6명</span>
          </StudyContentInfo>
          <StudyContentInfo>
            <div>플랫폼</div>
            <span>https://notion.so</span>
          </StudyContentInfo>
          <StudyContentText>
            TypeScript를 즐겁게 학습하실 스터디원 모집합니다!
            <br /> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam
            in libero luctus, varius eros in, ullamcorper neque. Morbi libero
            elit, facilisis aliquam lacinia quis, placerat quis orci. Phasellus
            iaculis ornare congue. Donec eu risus nisi. Mauris felis odio,
            rutrum quis mauris vitae, tempus ornare felis. Pellentesque habitant
            morbi tristique senectus et netus et malesuada fames ac turpis
            egestas. Duis in ante posuere, dignissim libero feugiat, aliquam
            metus. Donec non nisl massa. Sed luctus elit volutpat sapien
            placerat fermentum. Interdum et malesuada fames ac ante ipsum primis
            in faucibus. Donec ut pulvinar urna. Quisque ac felis efficitur,
            dignissim felis at, ultricies odio. Praesent vel metus at eros
            pretium suscipit et eget nisl. Sed dignissim augue nisl, ultrices
            varius sapien elementum at. Etiam gravida vulputate quam,
            scelerisque pellentesque turpis pulvinar eu. Sed arcu arcu,
            malesuada vel facilisis in, accumsan quis felis. Vivamus mattis
            tortor ante, id efficitur arcu convallis nec. Cras quis lacus a quam
            convallis tempor. Nunc eget sodales arcu. Aliquam porta, augue vitae
            blandit maximus, massa neque ultricies enim, vitae imperdiet lectus
            enim sed lorem. Nullam sagittis tortor turpis, pretium commodo dui
            dictum eget. Cras turpis lectus, euismod id elementum et, porta a
            nunc. Etiam quis nulla eu urna iaculis maximus. Donec erat arcu,
            pellentesque non nunc sit amet, tristique aliquet tellus.
            Suspendisse est tellus, lobortis vitae turpis ut, porttitor tempor
            ante. Mauris consequat sollicitudin neque, non bibendum velit
            malesuada sed. Aliquam blandit a lacus ac pharetra. Sed vel eros
            massa. Integer magna ligula, euismod at dignissim in, hendrerit ut
            elit. Nam fringilla eros sit amet massa vestibulum pellentesque.
            Nullam in iaculis sem. Vivamus viverra facilisis ultrices. Etiam
            luctus velit orci, a commodo arcu pharetra ut. Nam non cursus lorem.
            Fusce lacinia dictum vestibulum. Vivamus sit amet blandit metus.
            Integer quis semper erat, faucibus tincidunt lectus. Duis accumsan
            porta odio eu pulvinar. Maecenas id erat eget augue iaculis
            fringilla. Duis molestie nec lacus in tincidunt. Phasellus lectus
            ante, pharetra eu elit non,
          </StudyContentText>
          <StudyContentProfileWrapper>
            <StudyContentProfile>
              <div className="profile-name">lain-alice</div>
              <div>일반회원</div>
            </StudyContentProfile>
          </StudyContentProfileWrapper>
          <StudyContentTag>
            <div>javascript</div>
            <div>typescript</div>
          </StudyContentTag>
          <StudyJoinButtonWrapper>
            <StudyJoinButton>스터디 신청!</StudyJoinButton>
          </StudyJoinButtonWrapper>
        </StudyContentMain>
        <StudyComment />
      </StudyContentBody>
    </StudyContentContainer>
  );
};

const StudyContentContainer = styled.div`
  width: 100%;
  height: 100%;
  background-color: #e0e0e0;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const StudyContentBody = styled.div`
  width: 960px;
  padding-top: 120px;
  background-color: #fff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const StudyContentTop = styled.div`
  width: 800px;
  margin-bottom: 30px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;

  span {
    font-size: 1.5rem;
    font-weight: 700;
    color: #2759a2;
  }
`;

const StudyContentTitle = styled.div`
  width: 800px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  h2 {
    font-size: 2rem;
    font-weight: 700;
    color: #1f1f1f;
  }
`;

const StudyContentEdit = styled.div`
  width: 70px;
  display: flex;
  justify-content: space-between;
  align-items: flex-end;

  div {
    font-size: 0.875rem;
    color: #858da8;
    cursor: pointer;
  }
`;

const StudyContentMain = styled.div`
  width: 800px;
  margin: 15px 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;

  span {
    font-size: 1.5rem;
    font-weight: 700;
    color: #2759a2;
  }
`;

const StudyContentInfo = styled.div`
  width: 800px;
  margin-bottom: 12px;
  display: flex;
  justify-content: flex-start;
  align-items: center;

  div {
    width: 100px;
    text-align: left;
    font-size: 1.125rem;
    font-weight: 700;
    color: #2759a2;
    margin-right: 20px;
  }
  span {
    text-align: left;
    font-size: 1rem;
    font-weight: 300;
    color: #666;
  }
`;

const StudyContentText = styled.p`
  width: 800px;
  margin: 15px 0;
  padding: 20px 0;
  text-align: left;
  font-size: 1rem;
  font-weight: 300;
  color: #1f1f1f;
`;

const StudyContentProfileWrapper = styled.div`
  width: 800px;
  margin: 20px 0;
  display: flex;
  justify-content: flex-end;
`;

const StudyContentProfile = styled.div`
  width: 170px;
  height: 80px;
  border-radius: 5px;
  border: 1px solid #ccc;
  padding: 12px 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-end;

  .profile-name {
    font-size: 1rem;
    font-weight: 700;
    color: #2759a2;
    margin-bottom: 10px;
  }

  div {
    color: #999;
    font-size: 0.8125rem;
  }
`;

const StudyContentTag = styled.div`
  width: 260px;
  padding-top: 10px;
  display: flex;
  justify-content: flex-start;
  align-items: center;

  div {
    height: 24px;
    color: #39739d;
    font-size: 0.8125rem;
    border-radius: 4px;
    background-color: #e1ecf4;
    padding: 4.8px 6px;
    margin-right: 7px;
    cursor: pointer;
  }
`;

const StudyJoinButtonWrapper = styled.div`
  width: 800px;
  margin: 15px 0;
  display: flex;
  justify-content: flex-end;
`;

const StudyJoinButton = styled.button`
  width: 150px;
  height: 48px;
  font-size: 1.2rem;
  color: #ffffff;
  background-color: #4994da;

  &:hover {
    opacity: 85%;
  }
  &:active {
    opacity: 100%;
  }
`;

export default StudyContent;
