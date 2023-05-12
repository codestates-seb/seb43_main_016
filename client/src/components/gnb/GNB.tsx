import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import styled from "styled-components";
import logo from "../../assets/edusync-logo.png";
import User from "./User";
import { getAccessToken } from "../../pages/utils/Auth";
import { useRecoilState } from "recoil";
import { LogInState } from "../../recoil/atoms/LogInState";
//import { useRecoilState } from "recoil";
//import { myIdState } from "../../recoil/atoms/MyIdState";

const accessToken = getAccessToken();
const GNB = () => {
  const [profileImage, setProfileImage] = useState("");
  const [isLoggedIn, setIsLoggedIn] = useRecoilState(LogInState);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    if (isLoggedIn) {
      setIsLoading(true);
      axios
        .get(`${import.meta.env.VITE_APP_API_URL}/members`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        })
        .then((res) => {
          setProfileImage(res.data.profileImage);
          setIsLoading(false);
        });
    }
  }, [isLoggedIn]);

  return (
    <>
      {isLoading ? (
        <GNBDiv></GNBDiv>
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
