import { MouseEventHandler, useEffect, useState } from "react";
import styled from "styled-components";
import WaitingList from "../components/WaitingList";
import { Link } from "react-router-dom";
import { getStudyGroupList, getStudyGroupInfo } from "../apis/StudyGroupApi";

interface StudyGroupListDto {
  id: number;
  title: string;
  tagValue: string[];
}

interface StudyInfoResponseDto {
  studyId: number;
  studyName: string;
  tags: string[];
  studyProfileImage: string;
}

const convertStudyGroupListToStudyInfoList = (studyGroupList: StudyGroupListDto[]): StudyInfoResponseDto[] => {
  return studyGroupList.map((studyGroup) => {
    return {
      studyId: studyGroup.id,
      studyName: studyGroup.title,
      tags: studyGroup.tagValue,
      studyProfileImage: "",
    };
  });
};

interface ProfileStudyListCardProps {
  study: StudyInfoResponseDto;
}

const ProfileStudyListCard = ({
  study,
  onClick,
}: ProfileStudyListCardProps & {
  onClick?: MouseEventHandler<HTMLDivElement>;
}) => {
  return (
    <Box onClick={onClick}>
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
  const [selectedStudyGroup, setSelectedStudyGroup] = useState<StudyInfoResponseDto | null | undefined>(null);

  useEffect(() => {
    const fetchStudyList = async () => {
      try {
        const data = await getStudyGroupList();
        const convertedData = convertStudyGroupListToStudyInfoList(data);
        setStudyList(convertedData);
      } catch (error) {
        console.error("스터디 그룹 리스트를 불러오는데 실패했습니다.", error);
      }
    };

    fetchStudyList();
  }, []);

  useEffect(() => {
    const fetchStudyGroupInfo = async () => {
      if (selectedStudyGroup !== undefined && selectedStudyGroup !== null) {
        try {
          const studyInfo = await getStudyGroupInfo(selectedStudyGroup.studyId);
          console.log(studyInfo);
          // 가져온 스터디 그룹 정보를 처리하거나 원하는 작업을 수행
        } catch (error) {
          console.log(error)
        }
      }
    };

    fetchStudyGroupInfo();
  }, [selectedStudyGroup]);

  const handleStudyGroupClick = (studyId: number) => {
    const selectedStudy = studyList.find((study) => study.studyId === studyId);
    setSelectedStudyGroup(selectedStudy);
  };

  return (
    <>
      <WaitingList />
      {studyList.map((study) => (
        <ProfileStudyListCard
          key={study.studyId}
          study={study}
          onClick={() => handleStudyGroupClick(study.studyId)}
        />
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
