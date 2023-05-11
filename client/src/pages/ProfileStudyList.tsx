import { useEffect, useState } from "react";
import axios from "axios";
import styled from "styled-components";
import WaitingList from "../components/WaitingList";
import { Link } from "react-router-dom";

interface StudyInfoResponseDto {
  studyId: number;
  studyName: string;
  tags: string[];
  studyProfileImage: string;
}

interface ProfileStudyListCardProps {
  study: StudyInfoResponseDto;
}

const ProfileStudyListCard = ({ study }: ProfileStudyListCardProps) => {
  return (
    <Box>
      <Link to={`/profile/${study.studyId}`}>
        <ImageWrapper>
          <img src={study.studyProfileImage} alt={study.studyName} />
        </ImageWrapper>
        <Title>{study.studyName}</Title>
        <Tags>{study.tags}</Tags>
      </Link>
    </Box>
  );
};

const ProfileStudyList = () => {
  const [studyList, setStudyList] = useState<StudyInfoResponseDto[]>([]);

  useEffect(() => {
    axios.get("http://localhost:3001/members/2").then((response) => {
      const studyLists = response.data.studyLists;
      setStudyList(studyLists);
    });
  }, []);

  return (
    <>
      <WaitingList />
      {studyList.map((study) => (
        <ProfileStudyListCard key={study.studyId} study={study} />
      ))}
    </>
  );
};

export default ProfileStudyList;

const Box = styled.div`
  box-sizing: border-box;
  position: relative;
  width: 340px;
  height: 340px;
  margin: 0 16px 16px 0;
  padding: 16px;
  background: #ffffff;
  border: 1px solid #285aa3;
  border-radius: 5px;
`;

const Title = styled.h3`
  position: absolute;
  left: 24px;
  top: 28px;
  width: 78px;
  height: 22px;
  font-family: Inter;
  font-style: bold;
  font-size: 18px;
  line-height: 22px;
  text-align: center;
  color: #000000;
`;

const Tags = styled.p`
  position: absolute;
  right: 24px;
  top: 28px;
  width: 77px;
  height: 19px;
  font-family: Inter;
  font-style: regular;
  font-size: 16px;
  line-height: 19px;
  text-align: center;
  color: #1f1f1f;
`;

const ImageWrapper = styled.div`
  position: absolute;
  top: 97px;
  left: 0;
  width: 100%;
  height: 71.3%; // height 값을 조정하여 이미지가 넘치지 않도록 만듦
  border-radius: 5px;
  overflow: hidden;

  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
`;
