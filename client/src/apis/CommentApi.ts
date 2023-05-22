//import { useRecoilValue } from "recoil";
//import { LogInState } from "../recoil/atoms/LogInState";
import tokenRequestApi from "./TokenRequestApi";
import { eduApi } from "./EduApi";

// ====================== 댓글 등록 (post) ===========================

//type Props = {
//  content: string;
//};

export const postContent = async (data: string) => {
  try {
    const jsonData = JSON.stringify({ content: data }); // 데이터를 JSON 문자열로 직렬화
    console.log(jsonData);
    const response = await tokenRequestApi.post(
      "/studygroup/31/comment",
      jsonData
    );
    console.log(response.data);
  } catch (error) {
    console.log(error);
    throw new Error("댓글 등록 실패");
  }
};
// ====================== 댓글 수정 (patch) ===========================
// TODO : StudyGroup의 정보를 조회할 때 데이터 타입 정의

// ====================== 댓글 전부 조회 (get) ===========================
// TODO : StudyGroup의 정보를 조회할 때 데이터 타입 정의
export const getContents = async () => {
  try {
    const response = await eduApi.get("/studygroup/1/comments");
    console.log(response.data);
  } catch (error) {
    console.log(error);
    throw new Error("댓글 전부 조회 실패");
  }
};

// ====================== 댓글 삭제 (DELETE) ===========================
// TODO : StudyGroup의 정보를 삭제하는 코드
