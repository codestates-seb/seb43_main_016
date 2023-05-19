import { useState, useEffect } from "react";
import {
  StudyGroupMemberListDto,
  getStudyGroupMemberList,
} from "../../apis/StudyGroupApi";
import styled from "styled-components";
import { useRecoilValue } from "recoil";
import { LogInState } from "../../recoil/atoms/LogInState";

const GroupMemberManage = ({ id }: { id: number }) => {
  const [memberList, setMemberList] = useState<StudyGroupMemberListDto>(
    {} as StudyGroupMemberListDto
  );
  const isLoggedIn = useRecoilValue(LogInState);

  useEffect(() => {
    const fetchMemberList = async (id: number) => {
      try {
        const data = await getStudyGroupMemberList(id, isLoggedIn);
        if (data === undefined) {
          return <>텅..</>;
        }
        setMemberList(data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchMemberList(id);
  }, []);

  console.log(memberList);

  return (
    <Wrapper>
      {memberList && memberList.nickName ? (
        memberList.nickName.map((nickname, index) => (
          <Name key={index}>{nickname}</Name>
        ))
      ) : (
        <div>저는 텅텅 비어있고, 멘탈은 탈탈 나가있어요..</div>
      )}
    </Wrapper>
  );
};

export default GroupMemberManage;

const Wrapper = styled.div``;
const Name = styled.div``;
