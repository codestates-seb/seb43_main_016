// import axios from "axios";


  import { useEffect, useState } from "react";
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
          <Tags>{study.tags.join(", ")}</Tags>
        </Link>
      </Box>
    );
  };

  const ProfileStudyList = () => {
    const [studyList, setStudyList] = useState<StudyInfoResponseDto[]>([]);

    useEffect(() => {

        // ! 엔드포인트가 나오면 추후 적용 or apis 폴더로 이동
        // useEffect(() => {
        //   axios.get("http://localhost:3001/members/").then((response) => {
        //     const studyLists = response.data.studyLists;
        //     setStudyList(studyLists);
        //   });
        // }, []);

      // TODO 임시로 더미 데이터 생성
      const dummyData: StudyInfoResponseDto[] = [
        {
          studyId: 1,
          studyName: "Study 1",
          tags: ["Tag 1", "Tag 2", "Tag 3"],
          studyProfileImage: "https://example.com/image1.jpg",
        },
        {
          studyId: 2,
          studyName: "Study 2",
          tags: ["Tag 4", "Tag 5"],
          studyProfileImage: "https://example.com/image2.jpg",
        },
      ];

      setStudyList(dummyData);
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
