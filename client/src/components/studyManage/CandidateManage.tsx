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
  console.log(studyLeader);

  useEffect(() => {
    if (!isLoggedIn) {
      throw new Error("로그인 상태를 확인해주세요");
    }

    const fetchWaitingList = async () => {
      const data = await getStudyGroupMemberWaitingList(Number(id), isLoggedIn);
      setWaitingList(data);
    };

    if (id) {
      fetchWaitingList();
    }
  }, [id, isLoggedIn]);

  const handleApproveCandidate = async (nickname: string) => {
    await approveStudyGroupApplication(Number(id), nickname, isLoggedIn);
    location.reload();
  };

  const handleDenyCandidate = async (nickname: string) => {
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
            <div key={index}>
              {nickname}
              <button onClick={() => handleApproveCandidate(nickname)}>
                <BsCheckCircle />
              </button>
              <button onClick={() => handleDenyCandidate(nickname)}>
                <BsFillXCircleFill />
              </button>
            </div>
          ))}
      </>
    </MemberManageContainer>
  );
};

export default CandidateManage;
