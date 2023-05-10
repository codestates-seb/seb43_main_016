import styled from "styled-components";
import { Outlet, useNavigate } from "react-router-dom";

const Wrapper = styled.div``;
const Title = styled.div``;
const LNB = styled.button``;

const ProfileNav = () => {
  const navigate = useNavigate();
  return (
    <Wrapper>
      <Title>MyInfo</Title>
      <nav>
        <ul>
          <li>
            <LNB onClick={() => navigate("/profile")}>Profile</LNB>
          </li>
          <li>
            <LNB onClick={() => navigate("/manage-group")}>Manage Group</LNB>
          </li>
          <li>
            <LNB onClick={() => navigate("/calendar")}>Schedule</LNB>
          </li>
        </ul>
      </nav>
      <Outlet />
    </Wrapper>
  );
};

export default ProfileNav;
