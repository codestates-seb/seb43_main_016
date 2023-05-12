import { useState, useEffect, FormEvent } from "react";
import ProfileImg from "../components/ProfileImg";
import styled from "styled-components";
import axios from "axios";
import { myIdState } from "../recoil/atoms/MyIdState";
import { useRecoilValue } from "recoil";

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

const ProfileInfo = () => {
  const [userInfo, setUserInfo] = useState<UserInfo | null>(null);
  const [isEdit, setIsEdit] = useState<boolean>(false);
  const myId = useRecoilValue(myIdState);

  // TODO 최초 페이지 렌더링 시 유저 정보를 가져오는 코드
  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const token = localStorage.getItem("accessToken");
        const res = await axios.get(
          `${import.meta.env.VITE_APP_API_URL}/member/${myId}`,
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

  // TODO 중복이 잦은 비밀번호 검증 부분을 따로 정의한 코드
  const validatePassword = async (
    enteredPassword: string
  ): Promise<boolean> => {
    const token = localStorage.getItem("accessToken");

    try {
      const {
        data: { password },
      } = await axios.get(
        `${import.meta.env.VITE_APP_API_URL}/members/${myId}/password`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      return enteredPassword === password;
    } catch (error) {
      console.error(error);
      return false;
    }
  };

  // TODO Edit 버튼 클릭 시, 유저 비밀번호를 검증하고 isEdit 상태를 업데이트하는 코드
  const handleEditBtn = async (e: FormEvent<HTMLButtonElement>) => {
    e.preventDefault();

    const enterPassword = prompt(
      "개인정보를 수정하려면 비밀번호를 확인이 필요합니다"
    );

    try {
      const isValidPassword = await validatePassword(
        userInfo?.id || 0,
        enterPassword || ""
      );
      if (isValidPassword) {
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
  const handleDeleteBtn = async () => {
    const enterPassword = prompt(
      "회원탈퇴를 진행하시려면 비밀번호를 입력해주세요."
    );
    const isValidPassword = await validatePassword(
      userInfo?.id || 0,
      enterPassword || ""
    );

    if (isValidPassword) {
      try {
        const token = localStorage.getItem("accessToken");
        const res = await axios.delete(
          `${import.meta.env.VITE_APP_API_URL}/members/${userInfo?.id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        const data = res.data;
        console.log(data);
        alert("회원탈퇴가 완료되었습니다.");
      } catch (error) {
        console.error(error);
      }
    } else {
      alert("비밀번호를 확인하세요.");
    }
  };

  // ! userInfo 상태가 변경될 때, 콘솔에 userInfo를 출력하는 코드 => 구현 후에는 반드시 삭제.
  useEffect(() => {
    console.log(userInfo);
  }, [userInfo]);

  return (
    <ProfileWrapper>
      <form>
        {/* // TODO ProfileImag 코드 구현 부분 */}
        <ProfileImg profileImage={userInfo?.profileImage || ""} />
        {/* // TODO ProfileContents 코드 구현 부분 */}
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
        {/* // TODO ProfileDetail 코드 구현 부분 */}
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

export default ProfileInfo;

const ProfileWrapper = styled.div``;
const ProfileContentsWrapper = styled.div``;
const ProfileContents = styled.div``;
const ProfileContentsInput = styled.input``;
const ProfileDetailWrapper = styled.div``;
const ProfileDetail = styled.div``;
const ProfileDetailInput = styled.input``;
