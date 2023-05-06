import { useState, useEffect, FormEvent } from "react";
import ProfileNav from "../components/ProfileNav";
import ProfileImg from "../components/ProfileImg";
import styled from "styled-components";
import axios from "axios";

const ProfileWrapper = styled.div``;
const ProfileContentsWrapper = styled.div``;
const ProfileContents = styled.div``;
const ProfileContentsInput = styled.input``;
const ProfileDetailWrapper = styled.div``;
const ProfileDetail = styled.div``;
const ProfileDetailInput = styled.input``;

interface UserInfoResponseDto {
  id: number;
  email: string;
  profileImage: string;
  nickName: string;
  aboutMe: string;
  withMe: string;
  memberStatus: string;
}

type UserInfo = Omit<UserInfoResponseDto, "memberStatus">;

const Profile = () => {
  const [userInfo, setUserInfo] = useState<UserInfo | null>(null);
  const [isEdit, setIsEdit] = useState<boolean>(false);

  // Edit 버튼 클릭 시, isEdit 상태를 업데이트하는 코드
  const handleEditBtn = (e: FormEvent<HTMLButtonElement>) => {
    e.preventDefault();
    const enterPassword = prompt("수정할 비밀번호를 입력해주세요");
    const userPassword = "" // ? JWT 토큰으로 어떻게 유저정보를 가져올 수 있을까?
    if (enterPassword === userPassword) {
      setIsEdit(!isEdit);
    }
  };

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const res = await axios.get("http://localhost:3001/member/1");
        const data = res.data;
        setUserInfo(data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchUserInfo();
  }, []);

  // input 태그의 값이 변경될 때, userInfo 상태를 업데이트하는 코드
  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setUserInfo((prevUserInfo: any) => ({
      // any 말고 쓸 수 있는 형식이 뭐가 있을까?ㅠㅠ
      ...prevUserInfo,
      [name]: value,
    }));
  };

  // 회원탈퇴 버튼 클릭 시, 회원정보 삭제를 요청하는 코드
  const handleDeleteBtn = () => {
    const deleteUserInfo = async () => {
      try {
        const res = await axios.delete("http://localhost:3001/member/1");
        const data = res.data;
        console.log(data);
      } catch (error) {
        console.error(error);
      }
    };
    deleteUserInfo();
  };

  // ! userInfo 상태가 변경될 때, 콘솔에 userInfo를 출력하는 코드 => 구현 후에는 반드시 삭제.
  useEffect(() => {
    console.log(userInfo);
  }, [userInfo]);

  return (
    <ProfileWrapper>
      <form>
        <ProfileNav />
        <ProfileImg />
        <ProfileContentsWrapper>
          <ProfileContents>
            <ProfileContentsInput
              type="text"
              placeholder="닉네임을 입력해주세요"
              value={userInfo?.nickName}
              onChange={handleInputChange}
              disabled={!isEdit}
              required
            />
          </ProfileContents>
          <ProfileContents>
            <ProfileContentsInput
              type="text"
              placeholder="E-mail을 입력해주세요"
              value={userInfo?.email}
              onChange={handleInputChange}
              disabled={!isEdit}
              required
            />
          </ProfileContents>
          {isEdit ? (
            <ProfileContents>
              <ProfileContentsInput
                type="text"
                placeholder="수정할 비밀번호를 입력해주세요"
                value={userInfo?.email}
                onChange={handleInputChange}
                disabled={!isEdit}
                required
              />
            </ProfileContents>
          ) : (
            <></>
          )}
          <ProfileContents>
            <ProfileContentsInput type="text" placeholder="등급" disabled />
          </ProfileContents>
        </ProfileContentsWrapper>
        <ProfileDetailWrapper>
          <ProfileDetail>
            <ProfileDetailInput
              type="text"
              placeholder="자기소개"
              onChange={handleInputChange}
              value={userInfo?.aboutMe}
              disabled={!isEdit}
            />
          </ProfileDetail>
          <ProfileDetail>
            <ProfileDetailInput
              type="text"
              placeholder="함께 하고 싶은 동료"
              value={userInfo?.withMe}
              onChange={handleInputChange}
              disabled={!isEdit}
            />
          </ProfileDetail>
        </ProfileDetailWrapper>
        {!isEdit ? (
          <button onClick={handleEditBtn}>Edit</button>
        ) : (
          <button onClick={handleEditBtn}>Sumbit</button>
        )}
        <button onClick={handleDeleteBtn}>회원탈퇴</button>
      </form>
    </ProfileWrapper>
  );
};

export default Profile;
