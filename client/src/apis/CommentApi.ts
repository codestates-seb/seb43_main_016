import tokenRequestApi from "./TokenRequestApi";
// import { eduApi } from "./EduApi";

// ====================== 댓글 등록 (post) ===========================

export interface CommentDto {
  content: string;
  studygroupId: number;
  commentId: number;
  nickName: string;
  isMyComment: boolean;
}
export const postComment = async (studyGroupId: number, data: string) => {
  try {
    await tokenRequestApi.post(`/studygroup/${studyGroupId}/comment`, {
      content: data,
    });
  } catch (error) {
    throw new Error("댓글 등록 실패");
  }
};

// ====================== 댓글 수정 (patch) ===========================
export const patchComment = async (
  studyGroupId: number,
  patchId: number,
  data: string
) => {
  try {
    await tokenRequestApi.patch(
      `/studygroup/${studyGroupId}/comment/${patchId}`,
      { content: data }
    );
  } catch (error) {
    throw new Error("댓글 수정 실패");
  }
}; //31 -> 변수로 나중에 바꿔야 함

// ====================== 댓글 전부 조회 (get) ===========================
export const getComments = async (
  studyGroupId: number
): Promise<CommentDto[]> => {
  try {
    const response = await tokenRequestApi.get<CommentDto[]>(
      `/studygroup/${studyGroupId}/comments`
    ); //31 -> 변수로 나중에 바꿔야 함
    return response.data;
  } catch (error) {
    throw new Error("댓글 전부 조회 실패");
  }
};

// ====================== 댓글 삭제 (DELETE) ===========================
export const deleteComment = async (studyGroupId: number, patchId: number) => {
  try {
    await tokenRequestApi.delete(
      `/studygroup/${studyGroupId}/comment/${patchId}`
    );
  } catch (error) {
    alert("댓글을 삭제하는데 실패했습니다. 권한을 확인하세요");
  }
};
