import styled from "styled-components";
import ProfileImg from "../components/ProfileImg";
import {
  getMemberInfo,
  MemberInfoResponseDto,
  updateMemberDetail,
  MemberDetailDto,
  deleteMember,
  MemberPasswordCheckDto,
  checkMemberPassword,
} from "../apis/MemberApi";
import { useState, useEffect, ChangeEvent } from "react";
import UserInfoEditModal from "../components/modal/UserInfoEditModal";

const ProfileInfo = () => {
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [memberInfo, setMemberInfo] = useState<MemberInfoResponseDto | null>(
    null
  ); // 멤버 정보의 조회 (서버 원천 데이터)
  const [introduceInfo, setIntroduceInfo] = useState<MemberDetailDto | null>(
    null
  );
  const [isIntroduceEdit, setIsIntroduceEdit] = useState<boolean>(false);

  // TODO 최초 페이지 진입 시 유저의 정보를 조회하는 코드
  useEffect(() => {
    const fetchMemberInfo = async () => {
      try {
        //const accessToken = localStorage.getItem("accessToken");
        const info = await getMemberInfo();
        setMemberInfo(info);
      } catch (error) {
        alert("로그인이 필요합니다.");
        console.error(error);
      }
    };
    fetchMemberInfo();
  }, []);

  // TODO Edit 버튼을 클릭 시, 유저의 닉네임, 비밀번호를 수정할 수 있도록 상태를 변경하는 코드
  const handleEditClick = async () => {
    const enteredPassword = prompt(
      "개인정보 수정 전 비밀번호를 확인해야 합니다."
    ); // ! 해당 부분 추후 모달창으로 대체
    //const accessToken = localStorage.getItem("accessToken");

    if (!enteredPassword) return; // 비밀번호 입력을 취소하면 함수 종료
    // 비밀번호 검증
    try {
      const passwordCheckDto: MemberPasswordCheckDto = {
        password: enteredPassword,
      };
      await checkMemberPassword(passwordCheckDto);
      setIsModalOpen(true); // 비밀번호 검증이 성공하면 모달 열기
    } catch (error) {
      alert("비밀번호가 일치하지 않습니다.");
    }
  };

  // TODO Edit 버튼을 클릭 시, 유저의 자기소개, 원하는 동료상을 수정할 수 있도록 상태를 변경하는 코드
  const handleIntroduceEditClick = () => {
    setIsIntroduceEdit(true);
  };

  // TODO input 창의 유저의 자기소개 및 원하는 동료상을 수정할 수 있도록 상태를 변경하는 코드
  const handleIntroduceChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { value } = e.target;
    setIntroduceInfo({
      aboutMe: value,
      withMe: value,
    });
  };

  // TODO Save 버튼을 클릭 시, 유저의 자기소개 및 원하는 동료상을 서버에 PATCH하는 코드
  const handleSaveClick = async () => {
    //const accessToken = localStorage.getItem("accessToken");
    try {
      const memberDetailDto: MemberDetailDto = {
        aboutMe: introduceInfo?.aboutMe || "",
        withMe: introduceInfo?.withMe || "",
      };
      await updateMemberDetail(memberDetailDto);
      setIsIntroduceEdit(false);
    } catch (error) {
      console.error(error);
      setIsIntroduceEdit(false);
    }
  };

  // TODO DELETE 버튼을 클릭 시, 유저의 자기소개 및 원하는 동료상을 서버에서 DELETE하는 코드
  const handleDeleteClick = async () => {
    //const accessToken = localStorage.getItem("accessToken");
    try {
      await deleteMember();
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Wrapper>
      {/* 유저의 프로필 사진이 입력되는 자리 ===> 별도의 컴포넌트로 관리 중! components/ProfileImg */}
      <ProfileImage>
        <ProfileImg profileImage={memberInfo?.profileImage} />
      </ProfileImage>
      {/* 유저의 기본정보가 입력되는 자리 */}
      <ProfileBaseInfo>
        <ProfileInput disabled value={memberInfo?.nickName} />
        <ProfileInput disabled value={memberInfo?.email} />
        <ProfileInput disabled value={memberInfo?.roles} />
        <EditButton onClick={handleEditClick}>Edit</EditButton>
      </ProfileBaseInfo>
      {/* 유저의 자기소개와 원하는 유형의 팀원을 정리하는 자리 */}
      <IntroduceAndDesired>
        {!isIntroduceEdit ? (
          <>
            <IntroduceAndDesiredInput value={memberInfo?.aboutMe} />
            <IntroduceAndDesiredInput value={memberInfo?.withMe} />
          </>
        ) : (
          <>
            <IntroduceAndDesiredInput
              type="text"
              placeholder={memberInfo?.aboutMe}
              onChange={handleIntroduceChange}
            />
            <IntroduceAndDesiredInput
              type="text"
              placeholder={memberInfo?.withMe}
              onChange={handleIntroduceChange}
            />
          </>
        )}
        {!isIntroduceEdit ? (
          <EditButton onClick={handleIntroduceEditClick}>Edit</EditButton>
        ) : (
          <EditButton onClick={handleSaveClick}>Save</EditButton>
        )}
        <EditButton onClick={handleDeleteClick}>Delete</EditButton>
      </IntroduceAndDesired>
      <UserInfoEditModal
        isOpen={isModalOpen}
        closeModal={() => setIsModalOpen(false)}
      />
    </Wrapper>
  );
};

export default ProfileInfo;

const Wrapper = styled.div``;
const ProfileImage = styled.div``;
const ProfileBaseInfo = styled.div``;
const IntroduceAndDesired = styled.div``;
const ProfileInput = styled.input``;
const IntroduceAndDesiredInput = styled.input``;
const EditButton = styled.button``;
