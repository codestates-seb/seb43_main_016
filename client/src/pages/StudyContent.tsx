import styled from "styled-components";
import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { LogInState } from "../recoil/atoms/LogInState";
import {
  getStudyGroupInfo,
  deleteStudyGroupInfo,
  StudyInfoDto,
} from "../apis/StudyGroupApi";
import StudyComment from "../components/StudyComment";
import tokenRequestApi from "../apis/TokenRequestApi";

const StudyContent = () => {
  const initialTag = { [""]: [""] };
  const initialState = {
    id: 1,
    studyName: "",
    studyPeriodStart: "",
    studyPeriodEnd: "",
    daysOfWeek: [""],
    studyTimeStart: "",
    studyTimeEnd: "",
    memberCountMin: 1,
    memberCountMax: 1,
    memberCountCurrent: 1,
    platform: "",
    introduction: "",
    isRecruited: false,
    tags: initialTag,
    leaderNickName: "",
    leader: false,
  };

  const [fetching, setFetching] = useState(true);
  const [content, setContent] = useState<StudyInfoDto | null>(initialState);
  const [renderedIntroduction, setRenderedIntroduction] = useState<string | null>(null);
  const { id } = useParams(); // App.tsx의 Route url에 :id로 명시하면 그걸 가져옴
  const parsedId = Number(id);
  const navigate = useNavigate();
  const isLoggedIn = useRecoilValue(LogInState);
  const isRecruiting = content?.isRecruited;

  useEffect(() => {
    const fetchData = async () => {
      if (isNaN(parsedId)) {
        alert("잘못된 접근입니다");
        navigate("/studylist");
        return;
      }
      try {
        const content = await getStudyGroupInfo(parsedId, isLoggedIn);
        setContent(content);
        setFetching(false); // 데이터를 가져왔다는 걸 표시하는 플래그 함수, 렌더링했으면 undefined가 아니다
        console.log(content);
      } catch (error) {
        console.log(error);
        throw new Error("스터디 내용 로딩에 실패했습니다.");
      }
    };
    fetchData();
  }, [parsedId]);

  const handleIntroduction = (introduction: string) => {
    const createMarkup = () => {
      return { __html: introduction };
    };

    setRenderedIntroduction(introduction);
  };



  const handleDeleteButton = async () => {
    try {
      if (!window.confirm("스터디를 삭제하시겠습니까?")) return;
      await deleteStudyGroupInfo(parsedId, isLoggedIn);
      alert("스터디가 삭제되었습니다!");
      navigate("/studylist");
    } catch (error) {
      alert("스터디 삭제가 실패했습니다!");
      // 당신의 스터디가 아닙니다?
      console.error("Error during POST request:", error);
    }
  };

  const handleEditButton = async () => {
    try {
    } catch (error) {
      alert("스터디 수정이 실패했습니다!");
      // 당신의 스터디가 아닙니다?
      console.error("Error during POST request:", error);
    }
  };

  const handleJoinButton = async () => {
    try {
      const res = await tokenRequestApi.post(`/studygroup/${parsedId}/join`);
      console.log(res);
      alert("스터디 신청이 완료되었습니다!");
    } catch (error) {
      alert("스터디 신청이 실패했습니다!");
      console.error("Error during POST request:", error);
    }
    // 이미 등록한 스터디입니다 어떻게?
  };

  return (
    <StudyContentContainer>
      <StudyContentBody>
        {!fetching && (
          <div key={content?.id}>
            <StudyContentTop>
              {!isRecruiting ? <span>모집중</span> : <span>모집 완료</span>}
              <StudyContentTitle>
                <h2>{content?.studyName}</h2>
                <StudyContentEdit>
                  <button type="button" onClick={handleEditButton}>
                    수정
                  </button>
                  <button type="button" onClick={handleDeleteButton}>
                    삭제
                  </button>
                </StudyContentEdit>
              </StudyContentTitle>
            </StudyContentTop>
            <StudyContentMain>
              <StudyContentInfo>
                <div>날짜</div>
                <span>{`${content?.studyPeriodStart} ~ ${content?.studyPeriodEnd}`}</span>
              </StudyContentInfo>
              <StudyContentInfo>
                <div>요일, 시간</div>
                <span>{`${content?.daysOfWeek} ${content?.studyTimeStart} ~ ${content?.studyTimeEnd}`}</span>
              </StudyContentInfo>
              <StudyContentInfo>
                <div>인원</div>
                <span>{`${content?.memberCountMin} ~ ${content?.memberCountMax}`}</span>
              </StudyContentInfo>
              <StudyContentInfo>
                <div>플랫폼</div>
                <span>{content?.platform}</span>
              </StudyContentInfo>
              <StudyContentText>{`${content?.introduction}`}</StudyContentText>
              <StudyContentProfileWrapper>
                <StudyContentProfile>
                  <div className="profile-name">{`${content?.leaderNickName}`}</div>
                  <div>일반회원</div>
                </StudyContentProfile>
              </StudyContentProfileWrapper>
              <StudyContentTag>
                {content?.tags && (
                  <>
                    {Object.entries(content.tags).map(([category, tags]) => (
                      <div key={category}>
                        {category}:
                        {tags.map((tag) => (
                          <span key={tag}>{tag}</span>
                        ))}
                      </div>
                    ))}
                  </>
                )}
              </StudyContentTag>
              <StudyJoinButtonWrapper>
                <StudyJoinButton type="button" onClick={handleJoinButton}>
                  스터디 신청!
                </StudyJoinButton>
              </StudyJoinButtonWrapper>
            </StudyContentMain>
            <StudyComment />
          </div>
        )}
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
