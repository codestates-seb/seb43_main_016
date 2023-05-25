import styled from "styled-components";
import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { LogInState } from "../recoil/atoms/LogInState";
import TextEditor from "../components/TextEditor";
import DaysOfWeek from "../components/DaysOfWeek";
import TagInput from "../components/TagInput";
import {
  StudyGroupUpdateDto,
  getStudyGroupInfo,
  updateStudyGroupContentsInfo,
} from "../apis/StudyGroupApi";
/*try {
    } catch (error) {
      alert("스터디 수정이 실패했습니다!");
      // 당신의 스터디가 아닙니다?
      console.error("Error during POST request:", error);
    }
*/

const StudyUpdate = () => {
  const { id } = useParams();
  const studyGroupId = Number(id);
  useEffect(() => {
    getStudyGroupInfo(studyGroupId, isLoggedIn).then((data) => {
      const key = Object.keys(data.tags)[0];
      setStudyName(data.studyName);
      setStudyPeriodStart(data.studyPeriodStart);
      setStudyPeriodEnd(data.studyPeriodEnd);
      setChecked(data.daysOfWeek);
      setStudyTimeStart(data.studyTimeStart);
      setStudyTimeEnd(data.studyTimeEnd);
      setMemberCountMin(data.memberCountMin);
      setMemberCountMax(data.memberCountMax);
      setPlatform(data.platform);
      setIntroduction(data.introduction);
      setSelectedCategory(key);
      setTags(data.tags[key]);
    });
  }, []);

  const [studyName, setStudyName] = useState<string>("");
  const [studyPeriodStart, setStudyPeriodStart] = useState<string>("");
  const [studyPeriodEnd, setStudyPeriodEnd] = useState<string>("");
  const [checked, setChecked] = useState<string[]>([]);
  const [studyTimeStart, setStudyTimeStart] = useState<string>("00:00");
  const [studyTimeEnd, setStudyTimeEnd] = useState<string>("00:00");
  const [memberCountMin, setMemberCountMin] = useState<number>(1);
  const [memberCountMax, setMemberCountMax] = useState<number>(1);
  const [platform, setPlatform] = useState<string>("");
  const [tags, setTags] = useState<string[]>([]);
  const [viewTag, setViewTag] = useState(false);
  const [isInput, setIsInput] = useState(false);
  const [introduction, setIntroduction] = useState<string>("");
  const [selectedCategory, setSelectedCategory] =
    useState<string>("프론트엔드");
  const [selectedPeriodStart, setSelectedPeriodStart] = useState<string>("");
  const [selectedPeriodEnd, setSelectedPeriodEnd] = useState<string>("");
  const [selectedTimeStart, setSelectedTimeStart] = useState<string>("00:00");
  const [selectedTimeEnd, setSelectedTimeEnd] = useState<string>("00:00");
  const isLoggedIn = useRecoilValue(LogInState);

  const handleCategory = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedCategory(e.target.value);
    setViewTag(false);
    setIsInput(false);
  };

  const handleTitle = (e: React.ChangeEvent<HTMLInputElement>) => {
    setStudyName(e.target.value);
  };

  const handleStudyPeriodStart = (e: React.ChangeEvent<HTMLInputElement>) => {
    const startDateValue = e.target.value;
    const timeValue = "00:00";

    const formattedPeriodStart = `${startDateValue}T${timeValue}:00`;
    setSelectedPeriodStart(startDateValue);
    setStudyPeriodStart(formattedPeriodStart);
  };

  const handleStudyPeriodEnd = (e: React.ChangeEvent<HTMLInputElement>) => {
    const endDateValue = e.target.value;
    console.log(endDateValue);
    const timeValue = "00:00";

    const formattedPeriodEnd = `${endDateValue}T${timeValue}:00`;
    setSelectedPeriodEnd(endDateValue);
    setStudyPeriodEnd(formattedPeriodEnd);
    return formattedPeriodEnd

  };

  const handleStudyTimeStart = (e: React.ChangeEvent<HTMLInputElement>) => {
    const startTimeValue = e.target.value;
    const dateValue = "2023-05-04"; // This code seems unnecessary

    const formattedTimeStart = `${dateValue}T${startTimeValue}:00`;
    setSelectedTimeStart(startTimeValue);
    setStudyTimeStart(formattedTimeStart);
    return formattedTimeStart

  };

  const handleStudyTimeEnd = (e: React.ChangeEvent<HTMLInputElement>) => {
    const endTimeValue = e.target.value;
    const dateValue = "2023-05-04"; // This code seems unnecessary

    const formattedTimeEnd = `${dateValue}T${endTimeValue}:00`;
    setSelectedTimeEnd(endTimeValue);
    setStudyTimeEnd(formattedTimeEnd);
    return formattedTimeEnd
  };

  const handleMemberCountMin = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMemberCountMin(+e.target.value);
  };

  const handleMemberCountMax = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMemberCountMax(+e.target.value);
  };

  const handlePlatform = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPlatform(e.target.value);
  };

  const handlePostButton = async () => {
    const StudyPostDto: StudyGroupUpdateDto = {
      studyName,
      studyPeriodStart: studyPeriodStart,
      studyPeriodEnd: studyPeriodEnd,
      daysOfWeek: checked,
      studyTimeStart: studyTimeStart,
      studyTimeEnd: studyTimeEnd,
      memberCountMin,
      memberCountMax,
      platform,
      introduction,
      tags: {
        [selectedCategory]: tags,
      },
    };

    console.log(StudyPostDto);

    if (studyName === "") {
      alert("제목을 입력해주세요!");
      return;
    }
    if (!isLoggedIn) {
      alert("로그인한 사람만 스터디 등록이 가능합니다!");
      return;
    }
    if (memberCountMin > memberCountMax) {
      alert("최대 인원이 최소 인원보다 적습니다!");
      return;
    }

    if (studyPeriodStart > studyPeriodEnd) {
      alert("스터디 시작일이 종료일보다 늦습니다!");
      return;
    }

    if (checked.length === 0) {
      alert("요일을 선택해주세요!");
      return;
    }

    try {
      const res = await updateStudyGroupContentsInfo(
        StudyPostDto,
        isLoggedIn,
        studyGroupId
      );
      console.log(res);
      alert("스터디 수정이 완료되었습니다!");
      navigate("/studylist");
    } catch (error) {
      alert("스터디 수정이 실패했습니다!");
      console.error("Error during POST request:", error);
    }
  };

  const navigate = useNavigate();

  return (
    <StudyPostContainer>
      <StudyPostBody>
        <StudyPostTop>
          <span>스터디 수정</span>
          <input
            type="text"
            placeholder="제목을 입력하세요"
            value={studyName}
            onChange={handleTitle}
            required
          />
        </StudyPostTop>

        <StudyPostMain>
          <StudyPostInfo>
            <span>분야</span>
            <select
              name="category"
              value={selectedCategory}
              onChange={handleCategory}
            >
              <option value="프론트엔드">프론트엔드</option>
              <option value="백엔드">백엔드</option>
              <option value="알고리즘">알고리즘</option>
              <option value="인공지능">인공지능</option>
              <option value="기타">기타</option>
            </select>
          </StudyPostInfo>

          <StudyPostInfo>
            <span>날짜</span>
            <input
              type="date"
              value={selectedPeriodStart}
              onChange={handleStudyPeriodStart}
              required
            />
            <p>~</p>
            <input
              type="date"
              value={selectedPeriodEnd}
              onChange={handleStudyPeriodEnd}
              required
            />
          </StudyPostInfo>
          <StudyPostInfo>
            <span>요일</span>
            <div>
              <DaysOfWeek checked={checked} setChecked={setChecked} />
            </div>
          </StudyPostInfo>
          <StudyPostInfo>
            <span>시간</span>
            <input
              type="time"
              value={selectedTimeStart}
              onChange={handleStudyTimeStart}
              required
            />
            <p>~</p>
            <input
              type="time"
              value={selectedTimeEnd}
              onChange={handleStudyTimeEnd}
              required
            />
          </StudyPostInfo>
          <StudyPostInfo>
            <span>인원</span>
            <input
              type="number"
              min="1"
              value={memberCountMin}
              onChange={handleMemberCountMin}
              required
            />
            <p>~</p>
            <input
              type="number"
              min={memberCountMin}
              value={memberCountMax}
              onChange={handleMemberCountMax}
              required
            />
          </StudyPostInfo>
          <StudyPostInfo>
            <span>플랫폼</span>
            <input type="url" value={platform} onChange={handlePlatform} />
          </StudyPostInfo>
          <StudyPostInfo>
            <span>태그</span>
            <TagInput
              selectedCategory={selectedCategory}
              tags={tags}
              setTags={setTags}
              viewTag={viewTag}
              setViewTag={setViewTag}
              isInput={isInput}
              setIsInput={setIsInput}
            />
          </StudyPostInfo>
          <StudyPostInput>
            <TextEditor handleContentChange={setIntroduction} />
          </StudyPostInput>
          <StudyPostButtonWrapper>
            <StudyPostButton onClick={handlePostButton}>
              스터디 수정
            </StudyPostButton>
          </StudyPostButtonWrapper>
        </StudyPostMain>
      </StudyPostBody>
    </StudyPostContainer>
  );
};

// 최대인원이 최소인원보다 적으면 안 됨
// 끝나는 날짜가 시작하는 날짜보다 먼저면 안 됨

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

const StudyPostInfo = styled.form`
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
  ul {
    margin: 0 20px;
    padding: 7px;
    border-radius: 5px;
    cursor: pointer;
    background-color: #e9e9e9;
    font-size: 0.8rem;
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

export default StudyUpdate;
