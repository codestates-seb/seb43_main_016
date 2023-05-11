import { useParams } from "react-router-dom";

const ProfileStudyManage = () => {
  const { studyId } = useParams<{ studyId: string }>();

  return (
    <div>
      <h1>StudyManage</h1>
    </div>
  );
};

export default ProfileStudyManage;
