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
      setTimeout(() => {
        tokenRequestApi
          .get("/members")
          .then((res) => {
            console.log(res);
            setProfileImage(res.data.profileImage);
            setIsLoading(false);
          })
          .catch((err) => {
            console.log(err);
          });
      }, 100);
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
  color: #2759a2;
`;
const GNBBlock = styled.div`
  width: 150px;
  display: flex;
`;
const HomeLink = styled(Link)`
  img {
    height: 38px;
    margin-top: 4px;
  }
`;

export default GNB;
