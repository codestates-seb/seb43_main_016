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
      <CardWrapper onClick={handleClick}>
        <Title>{title}</Title>
        <Tag>{tagValues.join(", ")}</Tag>
        <Image>{/* 이미지 표시 로직 추가 */}</Image>
      </CardWrapper>
    );
  };

  return (
    <Wrapper>
      <WaitingList />
      <h2>Leaders</h2>
      {studyList.leaders.map((leader) => (
        <StudyCard
          key={leader.id}
          id={leader.id}
          title={leader.title}
          tagValues={leader.tagValues}
        />
      ))}

      <h2>Members</h2>
      {studyList.members.map((member) => (
        <StudyCard
          key={member.id}
          id={member.id}
          title={member.title}
          tagValues={member.tagValues}
        />
      ))}
    </Wrapper>
  );
};

export default ProfileStudyList;

const Wrapper = styled.div`
  flex-direction: column;
`;
const CardWrapper = styled.div``;
const Title = styled.div``;
const Image = styled.div``;
const Tag = styled.div``;
