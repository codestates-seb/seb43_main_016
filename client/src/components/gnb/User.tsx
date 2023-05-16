import { Link } from "react-router-dom";
import styled from "styled-components";
import tokenRequestApi from "../../apis/TokenRequestApi";
import { useNavigate } from "react-router-dom";
import { removeTokens } from "../../pages/utils/Auth";

type GNB = {
  profileImage: string;
  isLoggedIn: boolean;
  setIsLoggedIn: (value: boolean) => void;
};

const User = ({ profileImage, isLoggedIn, setIsLoggedIn }: GNB) => {
  const navigate = useNavigate();

  const handleLogout = (): void => {
    tokenRequestApi.setAccessToken(null);
    removeTokens();
    setIsLoggedIn(false);
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
