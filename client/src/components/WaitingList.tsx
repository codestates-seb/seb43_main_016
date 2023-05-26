import styled from "styled-components";
import { useEffect, useState } from "react";
import {
  WaitingStudyGroupItemDto,
  getWaitingStudyGroupList,
  cancelStudyGroupApplication,
} from "../apis/StudyGroupApi";
import { FiDelete } from "react-icons/fi";
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
          <FiDelete size="21" />
        </CancelButton>
      </ItemWrapper>
    );
  };

  return (
    <WaitingListWrapper>
<<<<<<< HEAD
      <WaitingListTitle>스터디 가입 신청</WaitingListTitle>
=======
      <WaitingListTitle>대기중인 가입 신청</WaitingListTitle>
>>>>>>> d6e29db2b9e5868d24be2a5b05aef6ebc6fb46a3
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

<<<<<<< HEAD
const WaitingListWrapper = styled.div``;

const WaitingListTitle = styled.h2`
  width: 700px;
=======
const WaitingListWrapper = styled.div`
  padding-left: 20px;
`;

const WaitingListTitle = styled.h2`
  width: 900px;
>>>>>>> d6e29db2b9e5868d24be2a5b05aef6ebc6fb46a3
  margin: 24px 0 20px;
  font-size: 20px;
  font-weight: 700;
  text-align: left;
  color: #2759a2;
`;

const ItemList = styled.div`
<<<<<<< HEAD
  width: 700px;
=======
  width: 900px;
>>>>>>> d6e29db2b9e5868d24be2a5b05aef6ebc6fb46a3
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const ItemWrapper = styled.div`
  width: 700px;
  height: 60px;
  background-color: #fff;
<<<<<<< HEAD
  border: 1px solid #ccc;
=======
  box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
>>>>>>> d6e29db2b9e5868d24be2a5b05aef6ebc6fb46a3
  border-radius: 4px;
  padding: 20px 30px;
  margin-bottom: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const ItemTitle = styled.div`
  margin-bottom: 5px;
  color: #1f1f1f;
  font-size: 18px;
  font-weight: 700;
  text-align: left;
`;

const CancelButton = styled.button`
  width: 27px;
  height: 27px;
  background-color: #999;
  color: #ffffff;
  border: none;
  border-radius: 4px;
  padding: 5px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
`;
