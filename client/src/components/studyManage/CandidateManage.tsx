import styled from "styled-components";
import { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";
import { LogInState } from "../../recoil/atoms/LogInState";
import {
  approveStudyGroupApplication,
  getStudyGroupMemberWaitingList,
  rejectStudyGroupApplication,
  StudyGroupMemberWaitingListDto,
} from "../../apis/StudyGroupApi";
import { useParams } from "react-router-dom";
import { BsCheckCircle, BsFillXCircleFill } from "react-icons/bs";
import { MemberManageContainer, MemberManageTitle } from "./MemberManage";

interface CandidateManageProps {
  studyLeader: string | undefined;
}

const CandidateManage = ({ studyLeader }: CandidateManageProps) => {
  const [waitingList, setWaitingList] =
    useState<StudyGroupMemberWaitingListDto | null>(null);
  const { id } = useParams<{ id: string }>();
  const isLoggedIn = useRecoilValue(LogInState);

  useEffect(() => {
    const fetchWaitingList = async () => {
      const data = await getStudyGroupMemberWaitingList(Number(id), isLoggedIn);
      setWaitingList(data);
    };

    if (id) {
      fetchWaitingList();
    }
  }, [id]);

  const handleApproveCandidate = async (nickname: string) => {
    // 스터디 원에게는 아예 대기리스트가 보이지 않기 때문에 예외처리 불필요
    if (nickname === studyLeader) {
      alert("스터디원은 새로운 스터디원의 허가여부를 결정할 수 없습니다");
    }
    await approveStudyGroupApplication(Number(id), nickname, isLoggedIn);
    location.reload();
  };

  const handleDenyCandidate = async (nickname: string) => {
    if (nickname === studyLeader) {
      alert("스터디원은 새로운 스터디원의 허가여부를 결정할 수 없습니다");
    }
    // 스터디 원에게는 아예 대기리스트가 보이지 않기 때문에 예외처리 불필요
    await rejectStudyGroupApplication(Number(id), nickname);
    location.reload();
  };

  return (
    <MemberManageContainer>
      <MemberManageTitle>
        <h3>가입 신청 대기 리스트</h3>
      </MemberManageTitle>
      <>
        {waitingList &&
          waitingList.nickName.map((nickname, index) => (
            <WaitingList key={index}>
              {nickname}
              <WaitingButton>
                <button onClick={() => handleApproveCandidate(nickname)}>
                  <BsCheckCircle size="18" color="#0e9220" />
                </button>
                <button onClick={() => handleDenyCandidate(nickname)}>
                  <BsFillXCircleFill size="18" color="#bb2727" />
                </button>
              </WaitingButton>
            </WaitingList>
          ))}
      </>
    </MemberManageContainer>
  );
};

export default CandidateManage;

const WaitingList = styled.div`
  width: 360px;
  height: 36px;
  background-color: #fff;
  box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
  border-radius: 4px;
  padding: 20px 30px;
  margin-bottom: 10px;
  color: #1f1f1f;
  font-size: 14px;
  font-weight: 300;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const WaitingButton = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;
