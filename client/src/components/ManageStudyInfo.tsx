import { useEffect, useState } from "react";
import styled from "styled-components";

interface Event {
  id: number;
  studyName: string;
  studyPeriodStart: string;
  studyPeriodEnd: string;
  daysOfWeek: number[];
  studyTimeStart: string;
  studyTimeEnd: string;
  memberCountMin: number;
  memberCountMax: number;
  platform: string;
  introduction: string;
  tags: Tag[];
}

interface Tag {
  tagKey: string;
  tagValue: string;
}

const ManageStudyInfo = () => {

  const [_info, setInfo] = useState<Event | null>(null);

  useEffect(() => {
    fetch(`http://${import.meta.env.VITE_APP_API_UR}/studygroup`)
      .then((response) => response.json())
      .then((data) => setInfo(data))
      .catch((error) => console.log(error));
  }, []);

  return (
    <Wrapper>
      <Title></Title>
      <PersonCount></PersonCount>
      <Platform></Platform>
    </Wrapper>
  );
};

export default ManageStudyInfo;

const Wrapper = styled.div``;
const Title = styled.div``;
const PersonCount = styled.div``;
const Platform = styled.div``;
