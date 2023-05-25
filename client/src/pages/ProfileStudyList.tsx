import { useEffect, useState } from "react";
import WaitingList from "../components/WaitingList";
import {
  getStudyGroupList,
  StudyGroup,
  StudyGroupListDto,
} from "../apis/StudyGroupApi";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { LogInState } from "../recoil/atoms/LogInState";
import studyImage from "../assets/studyImage.webp";

const ProfileStudyList = () => {
  const isLoggedIn = useRecoilValue(LogInState);
  const [studyList, setStudyList] = useState<StudyGroupListDto>({
    leaders: [],
    members: [],
  });
  const navigate = useNavigate();

  useEffect(() => {
    if (!isLoggedIn) {
      alert("로그인이 필요합니다");
      navigate("/login");
    }
    const fetchStudyList = async () => {
      const { data, status } = await getStudyGroupList();
      if (status < 299) {
        setStudyList(data);
      }
    };
    // response.data도 항상 있음.
    fetchStudyList();
  }, []);

  const StudyCard = ({ id, title, tagValues }: StudyGroup) => {
    const handleClick = () => {
      navigate(`/profile/${id}`);
    };

    return (
      <CardProfileStudyListContainer onClick={handleClick}>
        <Image>{/* 이미지 표시 로직 추가 */}</Image>
        <Title>
          <h3>{title}</h3>
        </Title>
        <Tag>{tagValues.join(", ")}</Tag>
      </CardProfileStudyListContainer>
    );
  };

  return (
    <MyStudyListContainer>
      <WaitingList />
      <ListTitle>운영중인 스터디</ListTitle>
      <StudyCardContainer>
        {studyList.leaders.map((leader) => (
          <StudyCard
            key={leader.id}
            id={leader.id}
            title={leader.title}
            tagValues={leader.tagValues}
          />
        ))}
      </StudyCardContainer>

      <ListTitle>가입된 스터디</ListTitle>
      <StudyCardContainer>
        {studyList.members.map((member) => (
          <StudyCard
            key={member.id}
            id={member.id}
            title={member.title}
            tagValues={member.tagValues}
          />
        ))}
      </StudyCardContainer>
    </MyStudyListContainer>
  );
};

export default ProfileStudyList;

const MyStudyListContainer = styled.div`
  width: 960px;
  height: 100%;
  margin-top: 100px;
  padding: 40px 0 200px;
  background-color: #fff;
  border-radius: 4px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const StudyCardContainer = styled.div`
  width: 900px;
  display: flex;
  flex-flow: row wrap;
  justify-content: center;
  align-items: center;
`;

const ListTitle = styled.h2`
  width: 700px;
  margin: 24px 0 20px;
  font-size: 20px;
  font-weight: 700;
  text-align: left;
  color: #2759a2;
`;

const CardProfileStudyListContainer = styled.div`
  flex-basis: 260px;
  height: 320px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
  margin: 10px 20px;
  padding: 5px;
  display: flex;
  flex-flow: column wrap;
  justify-content: center;
  align-items: center;
  cursor: pointer;
`;

const Title = styled.div`
  width: 240px;
  padding: 10px 0;
  color: #1f1f1f;
  display: flex;
  justify-content: flex-start;
  align-items: center;

  h3 {
    font-size: 16px;
    height: 32px;
    text-align: left;
    font-weight: 700;
  }
`;
const Image = styled.div`
  width: 240px;
  height: 180px;
  background-image: url(${studyImage});
  background-size: cover;
  background-color: aliceblue;
`;
const Tag = styled.div`
  width: 240px;
  padding-top: 10px;
  display: flex;
  flex-flow: row wrap;
  justify-content: flex-end;
  align-items: center;

  div {
    height: 24px;
    color: #39739d;
    font-size: 0.8125rem;
    border-radius: 4px;
    background-color: #e1ecf4;
    padding: 4.8px 6px;
    margin: 0 7px 4px 0;
    cursor: pointer;
  }
`;
