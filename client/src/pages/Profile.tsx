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
            <div
              className="schedule-link"
              onClick={() => navigate("/calendar")}
            >
              Schedule
            </div>
          </li>
        </ul>
      </LnbWrapper>
      <Routes>
        <Route path="/" element={<ProfileInfo />} />
        <Route path="/manage-group" element={<ProfileStudyList />} />
        <Route path="/:id" element={<ProfileStudyManage />} />
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
  max-width: 960px;
  margin-left: auto;
  margin-right: auto;
`;

const LnbWrapper = styled.div`
  background-color: white;
  width: 160px;
  height: 270px;
  border-radius: 4px;
  margin: 100px 20px 0 0;
  padding: 30px 10px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;

  ul {
    width: 130px;
    list-style: none;
  }

  li {
    text-align: left;
    color: #1f1f1f;
    font-size: 16px;
    font-weight: 500;
    margin-top: 10px;
    padding: 10px 8px 0;
  }
  .schedule-link {
    color: #1f1f1f;
    cursor: pointer;
  }
  .schedule-link:hover {
    color: #2759a2;
  }
`;
const Title = styled.h2`
  width: 130px;
  padding: 10px 5px;
  text-align: left;
  border-bottom: 1px solid #ccc;
  color: #2759a2;
  font-size: 21px;
`;

const Lnb = styled(Link)`
  color: #1f1f1f;

  &:hover {
    color: #2759a2;
  }
`;
