import ProfileInfo from "./ProfileInfo";
import styled from "styled-components";
import { Routes, Route, useNavigate, Link } from "react-router-dom";
import ProfileStudyList from "./ProfileStudyList";
import ProfileCalendar from "./ProfileCalendar";
import ProfileStudyManage from "./ProfileStudyManage";

const Profile = () => {
  const navigate = useNavigate();
  return (
    <Wrapper>
      <LnbWrapper>
        <Title>My Info</Title>
        <ul>
          <li>
            <Lnb to="./">Profile</Lnb>
          </li>
          <li>
            <Lnb to="./manage-group">Manage Group</Lnb>
          </li>
          <li>
            <div onClick={() => navigate("/calendar")}>Schedule</div>
          </li>
        </ul>
      </LnbWrapper>
      <Routes>
        <Route path="/" element={<ProfileInfo />} />
        <Route path="/manage-group" element={<ProfileStudyList />} />
        <Route path="/:studyId" element={<ProfileStudyManage id={1} />} />
        <Route path="/calendar" element={<ProfileCalendar />} />
      </Routes>
    </Wrapper>
  );
};

export default Profile;

const Wrapper = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 50px;
`;

const LnbWrapper = styled.div`
  background-color: white;
  margin-right: 20px;
`;
const Title = styled.div``;
const Lnb = styled(Link)`
  &:hover {
    cursor: pointer;
    text-decoration: underline;
  }
`;
