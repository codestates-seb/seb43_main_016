import styled from "styled-components";
import ProfileImg from "../components/ProfileImg";
import {
  getMemberInfo,
  MemberInfoResponseDto,
  updateMemberDetail,
  MemberDetailDto,
  deleteMember,
} from "../apis/MemberApi";
import { useState, useEffect, ChangeEvent } from "react";
import UserInfoEditModal from "../components/modal/UserInfoEditModal";

// TODO accessToken에 접근하는 방법 문의하기.

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
        const accessToken = localStorage.getItem("accessToken");
        const info = await getMemberInfo(accessToken);
        setMemberInfo(info);
      } catch (error) {
        alert("로그인이 필요합니다.");
      }
    };
    fetchMemberInfo();
  }, []);

  // TODO Edit 버튼을 클릭 시, 유저의 닉네임, 비밀번호를 수정할 수 있도록 상태를 변경하는 코드
  const handleEditClick = () => {
    setIsModalOpen(true);
  };

  // TODO Edit 버튼을 클릭 시, 유저의 자기소개, 원하는 동료상을 수정할 수 있도록 상태를 변경하는 코드
  const handleIntroduceEditClick = () => {
    setIsIntroduceEdit(true);
  };

  // TODO Edit 버튼을 클릭 시, 유저의 자기소개 및 원하는 동료상을 수정할 수 있도록 상태를 변경하는 코드
  const handleIntroduceChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { value } = e.target;
    setIntroduceInfo({
      aboutMe: value,
      withMe: value,
    });
  };

  // TODO Save 버튼을 클릭 시, 유저의 자기소개 및 원하는 동료상을 서버에 PATCH하는 코드
  const handleSaveClick = async () => {
    const accessToken = localStorage.getItem("accessToken");
    try {
      const memberDetailDto: MemberDetailDto = {
        aboutMe: introduceInfo?.aboutMe || "",
        withMe: introduceInfo?.withMe || "",
      };
      await updateMemberDetail(accessToken, memberDetailDto);
      setIsIntroduceEdit(false);
    } catch (error) {
      console.error(error);
    }
  };

  // TODO DELETE 버튼을 클릭 시, 유저의 자기소개 및 원하는 동료상을 서버에서 DELETE하는 코드
  const handleDeleteClick = async () => {
    const accessToken = localStorage.getItem("accessToken");
    try {
      await deleteMember(accessToken);
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
        <ProfileInput disabled>{memberInfo?.nickName}</ProfileInput>
        <ProfileInput disabled>{memberInfo?.email}</ProfileInput>
        <ProfileInput disabled>{memberInfo?.roles}</ProfileInput>
        <EditButton onClick={handleEditClick}>Edit</EditButton>
      </ProfileBaseInfo>
      {/* 유저의 자기소개와 원하는 유형의 팀원을 정리하는 자리 */}
      <IntroduceAndDesired>
        {isIntroduceEdit ? (
          <>
            <IntroduceAndDesiredInput>
              {memberInfo?.aboutMe}
            </IntroduceAndDesiredInput>
            <IntroduceAndDesiredInput>
              {memberInfo?.withMe}
            </IntroduceAndDesiredInput>
          </>
        ) : (
          <>
            <IntroduceAndDesiredInput
              placeholder={memberInfo?.aboutMe}
              onChange={handleIntroduceChange}
            />
            <IntroduceAndDesiredInput
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
