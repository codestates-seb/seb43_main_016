import { useParams } from "react-router-dom";
import ManageStudyInfo from "../components/ManageStudyInfo";
import ManageCalendar from "../components/ManageCalendar";
import ManageMembers from "../components/ManageMembers";
import ManageRequestMembers from "../components/ManageRequestMembers";

const ProfileStudyManage = () => {
  const { studyId } = useParams<{ studyId: string }>();

  return (
    <div>
      <h1>StudyManage</h1>
      <ManageStudyInfo />
      <ManageCalendar />
      <ManageMembers />
      <ManageRequestMembers />
    </div>
  );
};

export default ProfileStudyManage;
