import { Link } from "react-router-dom";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { isLoggedInSelector } from "../../recoil/selectors/IsLoggedInSelector";
import { googleLogout } from "@react-oauth/google";
import { removeTokens } from "../../pages/utils/Auth";

const User = ({ setMyId, profileImage }: any) => {
  const navigate = useNavigate();
  const isLoggedIn = useRecoilValue(isLoggedInSelector);

  const handleLogout = (): void => {
    removeTokens();
    setMyId(0);
    googleLogout();
    navigate("/");
  };

  return (
    <>
      {isLoggedIn ? (
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