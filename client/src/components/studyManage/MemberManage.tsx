import styled from "styled-components";
import { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";
import { useNavigate } from "react-router-dom";
import { LogInState } from "../../recoil/atoms/LogInState";
import { useParams } from "react-router-dom";
import {
  StudyGroupMemberApprovalDto,
  delegateStudyGroupLeader,
  forceExitStudyGroup,
  getStudyGroupMemberList,
} from "../../apis/StudyGroupApi";
import { AiOutlineCrown, AiOutlineUserDelete } from "react-icons/ai";

// TODO: 스터디 그룹에 가입된 회원 리스트 타입
export interface StudyGroupMemberListDto {
  nickName: string[];
}

interface MemberManageProps {
  studyLeader: string | undefined;
}

const MemberManage = ({ studyLeader }: MemberManageProps) => {
  const isLoggedIn = useRecoilValue(LogInState);
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();
  const [memberList, setMemberList] = useState<StudyGroupMemberListDto | null>(
    null
  );
  console.log(studyLeader);

  // 스터디 그룹 멤버 리스트를 불러오는 함수
  const fetchMemberList = async () => {
    try {
      const response = await getStudyGroupMemberList(Number(id), isLoggedIn);
      if (response) {
        setMemberList(response);
      } else {
        setMemberList(null);
      }
    } catch (error) {
      alert("멤버 목록을 불러오는데 실패했습니다");
      navigate("/login");
    }
  };

  useEffect(() => {
    if (!isLoggedIn) {
      navigate("/login");
    }
    fetchMemberList();
  }, []);

  // TODO : 스터디 그룹장의 권한을 위임하는 함수
  const handlePrivileges = async (nickname: string) => {
    const data: StudyGroupMemberApprovalDto = {
      nickName: nickname,
    };
    try {
      await delegateStudyGroupLeader(Number(id), data);
      alert("권한 위임에 성공했습니다");
      location.reload();
    } catch (error) {
      alert("스터디장만이 권한을 위임할 수 있습니다");
    }
    fetchMemberList();
  };

  // TODO : 스터디 그룹에서 강제로 퇴출하는 함수
  const handleForcedKicked = async (nickname: string) => {
    const data: StudyGroupMemberApprovalDto = {
      nickName: nickname,
    };
    await forceExitStudyGroup(Number(id), data);
    location.reload();
  };

  return (
    <MemberManageContainer>
      <MemberManageTitle>
        <h3>회원 목록</h3>
      </MemberManageTitle>
      <>
        {memberList &&
          memberList.nickName.map((nickname, index) => (
            <MemberList key={index}>
              {nickname}
              <MemberButton>
                <button onClick={() => handlePrivileges(nickname)}>
                  <AiOutlineCrown
                    size="24"
                    color="#89920f"
                    title="스터디장 위임"
                  />
                </button>
                <button onClick={() => handleForcedKicked(nickname)}>
                  <AiOutlineUserDelete
                    size="24"
                    color="#bb2727"
                    title="회원 강제퇴장"
                  />
                </button>
              </MemberButton>
            </MemberList>
          ))}
      </>
    </MemberManageContainer>
  );
};

export default MemberManage;

export const MemberManageContainer = styled.div`
  width: 800px;
  margin: 0 0 30px 50px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
`;

export const MemberManageTitle = styled.div`
  width: 800px;
  display: flex;
  justify-content: flex-start;
  align-items: center;

  h3 {
    width: 650px;
    text-align: left;
    font-size: 18px;
    font-weight: 700;
    color: #2759a2;
    margin-bottom: 20px;
  }
`;

const MemberList = styled.div`
  width: 700px;
  height: 60px;
  background-color: #fff;
  box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
  border-radius: 4px;
  padding: 20px 30px;
  margin-bottom: 10px;
  color: #2759a2;
  font-size: 18px;
  font-weight: 700;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const MemberButton = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;
