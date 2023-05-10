import styled from "styled-components";
import ProfileDetail from "./ProfileDetail";
import ProfileStudyList from "./ProfileStudyList";
import Calender from "./Calender";
import { Link, Route, Routes } from "react-router-dom";

const NavWrapper = styled.div``;
const Title = styled.div``;
const NavContents = styled(Link)``;

const ProfileNav = () => {
  return (
    <NavWrapper>
      <Title>MyInfo</Title>
      <nav>
        <ul>
          <li>
            <NavContents to="profile">Profile</NavContents>
          </li>
          <li>
            <NavContents to="manage-group">Manage Group</NavContents>
          </li>
          <li>
            <NavContents to="calendar">Schedule</NavContents>
          </li>
        </ul>
      </nav>
      <Routes>
        <Route path="profile" element={<ProfileDetail />} />
        <Route path="manage-group" element={<ProfileStudyList />} />
        <Route path="calendar" element={<Calender />} />
      </Routes>
    </NavWrapper>
  );
};

export default ProfileNav;
