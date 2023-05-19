import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import styled from "styled-components";
import logo from "../../assets/edusync-logo.png";
import User from "./User";
import tokenRequestApi from "../../apis/TokenRequestApi";
import { useRecoilState } from "recoil";
import { LogInState } from "../../recoil/atoms/LogInState";

const GNB = () => {
  const [profileImage, setProfileImage] = useState("");
  const [isLoggedIn, setIsLoggedIn] = useRecoilState(LogInState);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    setIsLoading(true);
    if (isLoggedIn) {
      tokenRequestApi
        .get("/members")
        .then((res) => {
          setProfileImage(res.data.profileImage);
          setIsLoading(false);
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }
    setIsLoading(false);
  }, [isLoggedIn]);
  return (
    <>
      {isLoading ? (
        <GNBDiv>
          <GNBBlock>
            <HomeLink to="/">
              <img src={logo} />
            </HomeLink>
          </GNBBlock>
        </GNBDiv>
      ) : (
        <GNBDiv>
          <GNBBlock>
            <HomeLink to="/">
              <img src={logo} />
            </HomeLink>
          </GNBBlock>
          <GNBMenuBlock>
            <Link to="/studylist">
              <span>스터디리스트</span>
            </Link>
            <Link to="/studycontent">
              <span>스터디컨텐츠</span>
            </Link>
            <Link to="/studypost">
              <span>포스트</span>
            </Link>
            <Link to="/calendar">
              <span>캘린더</span>
            </Link>
          </GNBMenuBlock>
          <User
            profileImage={profileImage}
            isLoggedIn={isLoggedIn}
            setIsLoggedIn={setIsLoggedIn}
          />
        </GNBDiv>
      )}
    </>
  );
};
const GNBDiv = styled.div`
  background-color: #ffffff;
  font-size: 13px;
  width: 100%;
  padding: 4px;
  box-shadow: 0px 2px 5px #adb3cd;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const GNBBlock = styled.div`
  width: 150px;
  display: flex;
`;
const GNBMenuBlock = styled(GNBBlock)`
  width: 370px;
  justify-content: space-between;
  span:hover {
    opacity: 80%;
  }
  span:active {
    opacity: 100%;
  }
`;

const HomeLink = styled(Link)`
  img {
    height: 38px;
    margin-top: 4px;
  }
`;

export default GNB;
