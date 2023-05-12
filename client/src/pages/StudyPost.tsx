import styled from "styled-components";
import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";

import TextEditor from "../components/TextEditor";

// const API_URL = "https://wish17.store";

const StudyPost = () => {
  const [title, setTitle] = useState<string>("");
  const [startDate, setStartDate] = useState<string>("");
  const [endDate, setEndDate] = useState<string>("");
  const [startTime, setStartTime] = useState<string>("00:00");
  const [endTime, setEndTime] = useState<string>("00:00");
  const [minPeople, setMinPeople] = useState<number>(1);
  const [maxPeople, setMaxPeople] = useState<number>(1);
  const [platform, setPlatform] = useState<string>("");
  const [postText, setPostText] = useState<string>("");

  const navigate = useNavigate();

  const handleTitle = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };
  const handleStartDate = (e: React.ChangeEvent<HTMLInputElement>) => {
    setStartDate(e.target.value);
  };
  const handleEndDate = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEndDate(e.target.value);
  };
  const handleStartTime = (e: React.ChangeEvent<HTMLInputElement>) => {
    setStartTime(e.target.value);
  };
  const handleEndTime = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEndTime(e.target.value);
  };
  const handleMinPeople = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMinPeople(+e.target.value);
  };
  const handleMaxPeople = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMaxPeople(+e.target.value);
  };
  const handlePlatform = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPlatform(e.target.value);
  };

  const handlePostButton = async () => {
    console.log("Submitting form with values:", {
      studyName: `${title}`,
      studyPeriodStart: `${startDate}`,
      studyPeriodEnd: `${endDate}`,
      daysOfWeek: [3, 4, 5],
      studyTimeStart: `${startTime}`,
      studyTimeEnd: `${endTime}`,
      minClassmateCount: minPeople,
      maxClassmateCount: maxPeople,
      platform: `${platform}`,
      introduction: `${postText}`,
      tags: [
        {
          taKey: "프론트엔드",
          tagValue: "javascript",
        },
      ],
    });
    try {
      const res = await axios.post(
        `${import.meta.env.VITE_APP_API_URL}/studygroup/1`,
        {
          studyName: `${title}`,
          studyPeriodStart: `${startDate}`,
          studyPeriodEnd: `${endDate}`,
          daysOfWeek: [3, 4, 5],
          studyTimeStart: `${startTime}`,
          studyTimeEnd: `${endTime}`,
          minClassmateCount: minPeople,
          maxClassmateCount: maxPeople,
          platform: `${platform}`,
          introduction: `${postText}`,
          tags: [
            {
              taKey: "프론트엔드",
              tagValue: "javascript",
            },
          ],
        }
      );
      console.log("POST request successful:", res.data);
      alert("스터디 등록이 완료되었습니다!");
      navigate("/studylist");
    } catch (error) {
      alert("스터디 등록이 실패했습니다!");
      console.error("Error during POST request:", error);
    }
  };

  return (
    <StudyPostContainer>
      <StudyPostBody>
        <StudyPostTop>
          <span>스터디 등록</span>
          <input
            type="text"
            placeholder="제목을 입력하세요"
            value={title}
            onChange={handleTitle}
            required
          />
        </StudyPostTop>

        <StudyPostMain>
          <StudyPostInfo>
            <span>일정</span>
            <input
              type="date"
              value={startDate}
              onChange={handleStartDate}
              required
            />
            <p>~</p>
            <input
              type="date"
              value={endDate}
              onChange={handleEndDate}
              required
            />
          </StudyPostInfo>
          <StudyPostInfo>
            <span>요일</span>
            <input type="text"></input>
          </StudyPostInfo>
          <StudyPostInfo>
            <span>시각</span>
            <input
              type="time"
              value={startTime}
              onChange={handleStartTime}
              required
            />
            <p>~</p>
            <input
              type="time"
              value={endTime}
              onChange={handleEndTime}
              required
            />
          </StudyPostInfo>
          <StudyPostInfo>
            <span>인원</span>
            <input
              type="number"
              min="1"
              value={minPeople}
              onChange={handleMinPeople}
              required
            />
            <p>~</p>
            <input
              type="number"
              min={minPeople}
              value={maxPeople}
              onChange={handleMaxPeople}
              required
            />
          </StudyPostInfo>
          <StudyPostInfo>
            <span>플랫폼</span>
            <input type="url" value={platform} onChange={handlePlatform} />
          </StudyPostInfo>
          <StudyPostInfo>
            <span>태그</span>
            <input type="text" />
          </StudyPostInfo>
          <StudyPostInput>
            <TextEditor handleContentChange={setPostText} />
          </StudyPostInput>
          <StudyPostButtonWrapper>
            <StudyPostButton onClick={handlePostButton}>
              스터디 등록
            </StudyPostButton>
          </StudyPostButtonWrapper>
        </StudyPostMain>
      </StudyPostBody>
    </StudyPostContainer>
  );
};

const StudyPostContainer = styled.div`
  width: 100%;
  height: 100%;
  background-color: #e0e0e0;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const StudyPostBody = styled.div`
  width: 960px;
  padding-top: 120px;
  background-color: #fff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const StudyPostTop = styled.div`
  width: 800px;
  padding-bottom: 5px;
  margin-bottom: 30px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  border-bottom: 1px solid #ccc;

  span {
    font-size: 1.5rem;
    font-weight: 700;
    color: #2759a2;
    margin-bottom: 20px;
  }
  input {
    width: 800px;
    height: 60px;
    border: none;
    background-color: transparent;
    font-size: 1.25rem;
  }
  input::placeholder {
    font-size: 1.25rem;
    color: #d8d3d3;
  }
`;

const StudyPostMain = styled.div`
  width: 800px;
  margin: 15px 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
`;

const StudyPostInfo = styled.div`
  width: 800px;
  margin-bottom: 20px;
  display: flex;
  justify-content: flex-start;
  align-items: center;

  span {
    width: 90px;
    text-align: left;
    font-size: 1.25rem;
    font-weight: 700;
    color: #2759a2;
    margin-right: 15px;
  }
  input {
    width: 240px;
    height: 40px;
    border: 1px solid #ccc;
    border-radius: 0;
  }
  p {
    padding: 0 10px;
  }
`;

const StudyPostInput = styled.div`
  margin: 16px 0;
`;

const StudyPostButtonWrapper = styled.div`
  width: 800px;
  margin: 15px 0;
  display: flex;
  justify-content: flex-end;
`;

const StudyPostButton = styled.button`
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

export default StudyPost;
