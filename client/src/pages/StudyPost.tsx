import styled from "styled-components";
import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";

import TextEditor from "../components/TextEditor";

const API_URL = "http://localhost:8080";

const StudyPost = () => {
  const [postText, setPostText] = useState<string>("");
  const [maxPeople, setMaxPeople] = useState<number>(0);
  const [platform, setPlatform] = useState<string>("");

  const navigate = useNavigate();

  const handleMaxPeople = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMaxPeople(+e.target.value);
  };
  const handlePlatform = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPlatform(e.target.value);
  };

  const handlePostButton = async () => {
    // console.log("Submitting form with values:", {
    //   maxClassmateCount: maxPeople,
    //   platform: `${platform}`,
    //   introduction: `${postText}`,
    // });
    try {
      const response = await axios.post(`${API_URL}/studygroup/1`, {
        maxClassmateCount: maxPeople,
        platform: `${platform}`,
        introduction: `${postText}`,
      });
      console.log("POST request successful:", response.data);
      alert("스터디 등록이 완료되었습니다!");
      navigate("/studylist");
    } catch (error) {
      console.error("Error during POST request:", error);
    }
  };

  return (
    <StudyPostContainer>
      <StudyPostBody>
        <StudyPostTop>
          <span>스터디 등록</span>
          <input type="text" placeholder="제목을 입력하세요"></input>
        </StudyPostTop>

        <StudyPostMain>
          <StudyPostInfo>
            <span>일정</span>
            <input type="text"></input>
          </StudyPostInfo>
          <StudyPostInfo>
            <span>요일</span>
            <input type="text"></input>
          </StudyPostInfo>
          <StudyPostInfo>
            <span>시각</span>
            <input type="text"></input>
          </StudyPostInfo>
          <StudyPostInfo>
            <span>최대 인원</span>
            <input
              type="number"
              min="1"
              value={maxPeople}
              onChange={handleMaxPeople}
              required
            />
          </StudyPostInfo>
          <StudyPostInfo>
            <span>플랫폼</span>
            <input type="url" value={platform} onChange={handlePlatform} />
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
