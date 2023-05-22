import { useEffect, useState } from "react";
import WaitingList from "../components/WaitingList";
import {
  getStudyGroupList,
  StudyGroup,
  StudyGroupListDto,
} from "../apis/StudyGroupApi";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";

const ProfileStudyList = () => {
  const [studyList, setStudyList] = useState<StudyGroupListDto>({
    leaders: [],
    members: [],
  });
  const navigate = useNavigate();

  useEffect(() => {
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
    <>
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
    </>
  );
};

export default ProfileStudyList;

const CardWrapper = styled.div`
  // 카드 스타일 지정
`;
const Title = styled.div`
  // 제목 스타일 지정
`;
const Image = styled.div`
  // 이미지 스타일 지정
`;
const Tag = styled.div`
  // 태그 스타일 지정
`;
