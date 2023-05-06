import { Link } from "react-router-dom";
import styled from "styled-components";

interface UserProps {
  isLogin: boolean;
  handleLogout: () => void;
}
const User = ({ isLogin, handleLogout }: UserProps) => {
  return (
    <>
      {isLogin ? (
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
