import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import styled from "styled-components";
import logo from "../../assets/edusync-logo.png";
import User from "./User";
import { useRecoilState } from "recoil";
import { myIdState } from "../../recoil/atoms/myIdState";

const GNB = () => {
  const [myId, setMyId] = useRecoilState(myIdState);
  const [profileImage, setProfileImage] = useState("");

  useEffect(() => {
    if (myId > 0) {
      axios
        .get(`${import.meta.env.VITE_APP_API_URL}/members/${myId}`)
        .then((res) => setProfileImage(res.data.profileImage));
    }
  }, [myId]);

  return (
    <>
      <GNBDiv>
        <GNBBlock>
          <HomeLink to="/">
            <img src={logo} />
          </HomeLink>
        </GNBBlock>

        <User setMyId={setMyId} profileImage={profileImage} />
      </GNBDiv>
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
