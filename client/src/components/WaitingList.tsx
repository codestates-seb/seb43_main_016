import styled from "styled-components";
import { useEffect, useState } from "react";
import {
  WaitingStudyGroupItemDto,
  getWaitingStudyGroupList,
  cancelStudyGroupApplication,
} from "../apis/StudyGroupApi";
import { GiCancel } from "react-icons/gi";
import { useRecoilValue } from "recoil";
import { LogInState } from "../recoil/atoms/LogInState";

const WaitingList = () => {
  const [waitingList, setWaitingList] = useState<WaitingStudyGroupItemDto[]>(
    []
  );
  const isLoggedIn = useRecoilValue(LogInState);

  useEffect(() => {
    const fetchWaitingList = async () => {
      try {
        const data = await getWaitingStudyGroupList();
        if (data === null) {
          return;
        }
        setWaitingList(data.beStudys);
      } catch (error) {
        console.error("스터디 그룹 리스트를 불러오는데 실패했습니다.", error);
      }
    };
    fetchWaitingList();
  }, []);

  const handleCancelButton = async (id: number) => {
    try {
      await cancelStudyGroupApplication(id, isLoggedIn);
      setWaitingList(waitingList.filter((study) => study.id !== id));
    } catch (error) {
      console.error("가입 신청 철회에 실패했습니다", error);
    }
  };

  const WaitingStudyGroupItem = ({ id, title }: WaitingStudyGroupItemDto) => {
    return (
      <ItemWrapper key={id}>
        <ItemTitle>{title}</ItemTitle>
        <CancelButton onClick={() => handleCancelButton(id)}>
          <GiCancel />
        </CancelButton>
      </ItemWrapper>
    );
  };

  return (
    <WaitingListWrapper>
      <WaitingListTitle>신청중인 스터디</WaitingListTitle>
      <ItemList>
        {waitingList.map((study) => (
          <WaitingStudyGroupItem
            key={study.id}
            id={study.id}
            title={study.title}
          />
        ))}
      </ItemList>
    </WaitingListWrapper>
  );
};

export default WaitingList;

const WaitingListWrapper = styled.div``;

const WaitingListTitle = styled.div`
  margin-bottom: 10px;
  font-weight: bold;
  color: #416bac;
`;

const ItemList = styled.div`
  width: 600px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const ItemWrapper = styled.div`
  width: 600px;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 10px;
  margin-bottom: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const ItemTitle = styled.div`
  margin-bottom: 5px;
  color: #416bac;
`;

const CancelButton = styled.button`
  background-color: #416bac;
  color: #ffffff;
  border: none;
  border-radius: 4px;
  padding: 5px;
  cursor: pointer;
`;
