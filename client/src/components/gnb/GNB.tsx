import { Link } from "react-router-dom";

import styled from "styled-components";
import logo from "../../assets/edusync-logo.png";
import User from "./User";

const GNB = () => {
  return (
    <>
      <GNBDiv>
        <GNBBlock>
          <HomeLink to="/">
            <img src={logo} />
          </HomeLink>
        </GNBBlock>

        <User />
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
