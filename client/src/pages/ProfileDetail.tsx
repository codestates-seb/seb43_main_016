import { useState, useEffect, FormEvent } from "react";
import ProfileImg from "../components/ProfileImg";
import styled from "styled-components";
import axios from "axios";

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

  // TODO 최초 페이지 렌더링 시 유저 정보를 가져오는 코드
  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const token = localStorage.getItem("accessToken");
        const res = await axios.get(
          `${import.meta.env.VITE_APP_API_URL}/members/`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        const data = res.data;
        setUserInfo(data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchUserInfo();
  }, []);

  // TODO Edit 버튼 클릭 시, 유저 비밀번호를 검증하고 isEdit 상태를 업데이트하는 코드
  const handleEditBtn = async (e: FormEvent<HTMLButtonElement>) => {
    e.preventDefault();

    const token = localStorage.getItem("accessToken");
    const enterPassword = prompt(
      "개인정보를 수정하려면 비밀번호를 확인이 필요합니다"
    );

    try {
      const {
        data: { password },
      } = await axios.get(
        `${import.meta.env.VITE_APP_API_URL}/members/${userInfo?.id}/password`,
        {
          // ? id가 추가되는 부분이 이해되지 않아 문의드림
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (enterPassword === password) {
        setIsEdit(true);
      } else {
        alert("비밀번호를 확인하세요");
      }
    } catch (error) {
      alert("비밀번호를 확인하세요");
    }
  };

  // TODO input 태그의 값이 변경될 때, userInfo 상태를 업데이트하는 코드
  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setUserInfo((prevUserInfo: any) => ({
      // any 말고 쓸 수 있는 형식이 뭐가 있을까?ㅠㅠ
      ...prevUserInfo,
      [name]: value,
    }));
  };

  // TODO save 버튼 클릭 시 유저 정보를 수정하는 코드
  const handleSaveBtn = async (e: FormEvent<HTMLButtonElement>) => {
    e.preventDefault();

    const token = localStorage.getItem("accessToken");

    try {
      const res = await axios.put(
        `${import.meta.env.VITE_APP_API_URL}/members/${userInfo?.id}`,
        userInfo,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      const updatedUserInfo = res.data;
      setUserInfo(updatedUserInfo);
      setIsEdit(false);
    } catch (error) {
      alert("개인정보 수정에 실패했습니다.");
    }
  };

  // TODO 회원탈퇴 버튼 클릭 시, 회원정보 삭제를 요청하는 코드
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
        <ProfileImg profileImage={userInfo?.profileImage || ""} />
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
              disabled
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
          <button onClick={handleSaveBtn}>Save</button>
        )}
        <button onClick={handleDeleteBtn}>회원탈퇴</button>
      </form>
    </ProfileWrapper>
  );
};

export default Profile;

const ProfileWrapper = styled.div``;
const ProfileContentsWrapper = styled.div``;
const ProfileContents = styled.div``;
const ProfileContentsInput = styled.input``;
const ProfileDetailWrapper = styled.div``;
const ProfileDetail = styled.div``;
const ProfileDetailInput = styled.input``;
