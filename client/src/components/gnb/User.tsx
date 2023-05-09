import { Link } from "react-router-dom";
import styled from "styled-components";
import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { getAccessToken, getRefreshToken } from "../../pages/utils/Auth";
import { removeTokens } from "../../pages/utils/Auth";
import { useRecoilState } from "recoil";
import { myIdState } from "../../recoil/atoms/myIdState";

const accessToken = getAccessToken();
const refreshToken = getRefreshToken();

const User = () => {
  const navigate = useNavigate();
  const [myId, setMyId] = useRecoilState(myIdState);
  const [profileImage, setProfileImage] = useState("");

  const handleLogout = (): void => {
    removeTokens();
    setMyId(0);
    navigate("/");
  };
  useEffect(() => {
    if (myId > 0) {
      axios
        .get(`${import.meta.env.VITE_APP_API_URL}/members/${myId}`)
        .then((res) => console.log(res.data.profileImage));
    }
  }, [myIdState]);
  return (
    <>
      {accessToken && refreshToken ? (
        <UserDiv>
          <ProfileLink to="/profile">
            <div>
              <img src="https://www.kocca.kr/cmm/fnw/getImage.do?atchFileId=FILE_000000001097008&fileSn=1" />
            </div>
          </ProfileLink>
          <button onClick={handleLogout}>로그아웃</button>
        </UserDiv>
      ) : (
        <UserDiv>
          <Link to="/login">
            <button>로그인</button>
          </Link>
          <Link to="signup">
            <button>회원가입</button>
          </Link>
        </UserDiv>
      )}
    </>
  );
};
const UserDiv = styled.div`
  display: flex;
  justify-content: flex-end;
  width: 150px;
  button {
    margin-left: 7px;
    color: #2759a2;
  }
`;
const ProfileLink = styled(Link)`
  div {
    width: 33px;
    height: 33px;
    overflow: hidden;
    margin: 0 auto;
    border-radius: 10px;
  }
  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
`;

export default User;
