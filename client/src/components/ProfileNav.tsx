import styled from "styled-components";

const NavWrapper = styled.div``;
const Title = styled.div``;
const NavContents = styled.div``;

const ProfileNav = () => {
  return (
    <NavWrapper>
      <Title>MyInfo</Title>
      <NavContents>Profile</NavContents>
      <NavContents>Manage Group</NavContents>
      <NavContents>Schedule</NavContents>
    </NavWrapper>
  );
};

export default ProfileNav;
