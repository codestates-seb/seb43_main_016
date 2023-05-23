import styled from "styled-components";
import ProfileImg from "../components/ProfileImg";
import {
  getMemberInfo,
  MemberInfoResponseDto,
  updateMemberDetail,
  MemberDetailDto,
  deleteMember,
  checkOauth2Member,
  checkMemberPassword,
  MemberPasswordCheckDto,
} from "../apis/MemberApi";
import { useState, useEffect, ChangeEvent } from "react";
import UserInfoEditModal from "../components/modal/UserInfoEditModal";
import { useRecoilValue } from "recoil";
import { LogInState } from "../recoil/atoms/LogInState";
import { useNavigate } from "react-router-dom";
import CheckPasswordModal from "../components/modal/CheckPasswordModal";

const ProfileInfo = () => {
  const isLoggedIn = useRecoilValue(LogInState);
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [memberInfo, setMemberInfo] = useState<MemberInfoResponseDto | null>(
    null
  ); // 멤버 정보의 조회 (서버 원천 데이터)
  const [introduceInfo, setIntroduceInfo] = useState<MemberDetailDto>({
    aboutMe: memberInfo?.aboutMe || "",
    withMe: memberInfo?.withMe || "",
  });
  // 멤버 정보 수정 (클라이언트에서 수정된 데이터)
  const [isIntroduceEdit, setIsIntroduceEdit] = useState<boolean>(false);
  const [passowrdCheckModalOpen, setPasswordCheckModalOpen] =
    useState<boolean>(false);
  const [passwordCheckResult, setPasswordCheckResult] =
    useState<MemberPasswordCheckDto>({ password: "" });
  const navigate = useNavigate();
  console.log(passwordCheckResult)

  // TODO 최초 페이지 진입 시 유저의 정보를 조회하는 코드
  useEffect(() => {
    const fetchMemberInfo = async () => {
      try {
        const info = await getMemberInfo(isLoggedIn);
        setMemberInfo(info);
      } catch (error) {
        alert("로그인이 필요합니다.");
        console.error(error);
      }
    };
    fetchMemberInfo();
  }, [isModalOpen, isLoggedIn]);

  // TODO Edit 버튼을 클릭 시, 유저의 닉네임, 비밀번호를 수정할 수 있도록 상태를 변경하는 코드
  // 현재 Modal 구현은 완료했으나 비동기 처리로 인해 계속된 오류 발생. 추가적인 최적화 작업 요함
  // Jest로 테스트할 필요! : why? 소셜 로그인은 자동으로 배포 서버로 리다이렉션 함!
  const handleEditClick = async () => {
    try {
      const data = await checkOauth2Member(isLoggedIn);
      if (data.provider !== "LOCAL") {
        alert("소셜 로그인 유저는 개인정보를 수정할 수 없습니다.");
      }
      if (data.provider === "LOCAL") {
        setPasswordCheckModalOpen(true);
      }
      checkMemberPassword(passwordCheckResult);
    } catch (error) {
      alert("비밀번호가 일치하지 않습니다.");
    }
    setIsModalOpen(true);
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
    try {
      await deleteMember();
      alert("회원탈퇴가 완료되었습니다.");
      localStorage.clear();
      navigate("/");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Wrapper>
      <ProfileBaseWrapper>
        <ProfileImage>
          <ProfileImg profileImage={memberInfo?.profileImage} />
        </ProfileImage>
        <ProfileBaseInfo>
          <ProfileInput disabled value={memberInfo?.nickName} />
          <ProfileInput disabled value={memberInfo?.email} />
          <ProfileInput disabled value={memberInfo?.roles} />
          <EditButton onClick={handleEditClick}>Edit</EditButton>
        </ProfileBaseInfo>
      </ProfileBaseWrapper>
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
      <CheckPasswordModal
        isOpen={passowrdCheckModalOpen}
        closeModal={() => setPasswordCheckModalOpen(false)}
        setPasswordCheckResult={setPasswordCheckResult}
      />
    </Wrapper>
  );
};

export default ProfileInfo;

const Wrapper = styled.div``;
const ProfileBaseWrapper = styled.div``;
const ProfileImage = styled.div``;
const ProfileBaseInfo = styled.div``;
const IntroduceAndDesired = styled.div``;
const ProfileInput = styled.input``;
const IntroduceAndDesiredInput = styled.input``;
const EditButton = styled.button``;
