import { Link } from "react-router-dom";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { getAccessToken, getRefreshToken } from "../../pages/utils/Auth";
import { removeTokens } from "../../pages/utils/Auth";

const accessToken = getAccessToken();
const refreshToken = getRefreshToken();

const User = ({ myId, setMyId, profileImage }: any) => {
  const navigate = useNavigate();

  const handleLogout = (): void => {
    removeTokens();
    setMyId(0);
    navigate("/");
  };

  return (
    <>
      {accessToken && refreshToken && myId > 0 ? (
        <UserDiv>
          <ProfileLink to="/profile">
            <div>
              <img src={profileImage} />
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
