//import { useRecoilValue } from "recoil";
//import { LogInState } from "../recoil/atoms/LogInState";
import tokenRequestApi from "./TokenRequestApi";
import { eduApi } from "./EduApi";

// ====================== 댓글 등록 (post) ===========================

export interface CommentDto {
  content: string;
  studygroupId: number;
  commentId: number;
  nickName: string;
}

export const postComment = async (data: string) => {
  try {
    const jsonData = JSON.stringify({ content: data }); // 데이터를 JSON 문자열로 직렬화
    await tokenRequestApi.post(
      "/studygroup/31/comment", //31 -> 변수로 나중에 바꿔야 함
      jsonData
    );
  } catch (error) {
    console.log(error);
    throw new Error("댓글 등록 실패");
  }
};
// ====================== 댓글 수정 (patch) ===========================

// ====================== 댓글 전부 조회 (get) ===========================
export const getComments = async (): Promise<CommentDto[]> => {
  try {
    const response = await eduApi.get<CommentDto[]>("/studygroup/31/comments"); //31 -> 변수로 나중에 바꿔야 함
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.log(error);
    throw new Error("댓글 전부 조회 실패");
  }
};

// ====================== 댓글 삭제 (DELETE) ===========================
