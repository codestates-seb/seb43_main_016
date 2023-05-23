import { useRecoilValue } from "recoil";
import { LogInState } from "../recoil/atoms/LogInState";
import { useEffect, useState } from "react";
import styled from "styled-components";
import {
  CommentDto,
  deleteComment,
  getComments,
  patchComment,
} from "../apis/CommentApi";
import { validateEmptyInput } from "../pages/utils/loginUtils";
import { useNavigate } from "react-router-dom";

const StudyCommentList = ({}) => {
  const isLoggedIn = useRecoilValue(LogInState);
  const navigate = useNavigate();

  const [comments, setComments] = useState<CommentDto[]>([]);
  const [comment, setComment] = useState("");
  const [patchId, setPatchId] = useState<number | null>(null);
  const [isUpdateMode, setIsUpdateMode] = useState(false);

  const handleComment = (e: React.ChangeEvent<HTMLInputElement>) => {
    setComment(e.target.value);
    //console.log(id);
  };

  const handleUpdate = (id: number, content: string) => {
    if (!isLoggedIn) navigate("/login");
    setIsUpdateMode(!isUpdateMode);
    setPatchId(id);
    setComment(content);
  };

  const handleUpdateButton = async () => {
    if (!isLoggedIn) navigate("/login");

    if (validateEmptyInput(comment)) {
      alert("댓글 내용을 입력해주세요.");
    } else {
      try {
        const studyGroupId = 31;
        if (patchId) {
          await patchComment(studyGroupId, patchId, comment);
          setIsUpdateMode(false);
          setPatchId(null);
          setComment("");
        }
      } catch (error) {
        console.log("댓글 등록 실패", error);
      }
    }
  };

  const handleDelete = async (patchId: number) => {
    if (!isLoggedIn) navigate("/login");
    try {
      const studyGroupId = 31;
      await deleteComment(studyGroupId, patchId);
      fetchData();
    } catch (error) {
      console.log("댓글 삭제 실패", error);
    }
  };
  const fetchData = async () => {
    try {
      const studyGroupId = 31;
      const newComment = await getComments(studyGroupId);
      setComments(newComment);
    } catch (error) {
      console.log(error);
    }
  };
  useEffect(() => {
    fetchData();
  }, [!isUpdateMode]); // post시 바로 변경될 수 있도록 의존성 배열 추가 예정
  return (
    <>
      <ul>
        {comments.map((comment) => {
          return (
            <CommentItemDiv key={comment.commentId}>
              <ContentItem>
                <p>{comment.nickName}</p>
                <>
                  {isUpdateMode && patchId === comment.commentId ? (
                    <>
                      <input
                        defaultValue={comment.content}
                        onChange={handleComment}
                      ></input>
                      <button onClick={handleUpdateButton}>완료</button>
                    </>
                  ) : (
                    <span>{comment.content}</span>
                  )}
                </>
              </ContentItem>
              <ButtonDiv>
                {comment.isMyComment && (
                  <>
                    <button
                      onClick={() =>
                        handleUpdate(comment.commentId, comment.content)
                      }
                    >
                      수정
                    </button>
                    <button onClick={() => handleDelete(comment.commentId)}>
                      삭제
                    </button>
                  </>
                )}
              </ButtonDiv>
            </CommentItemDiv>
          );
        })}
      </ul>
    </>
  );
};

const CommentItemDiv = styled.div`
  width: 80%;
  height: 70px;
  padding: 10px 10px 10px 10px;
  background-color: #ffffff;
  display: flex;
  justify-content: space-between;
  border-bottom: solid #e9e9e9;
`;
const ContentItem = styled.div`
  text-align: left;
  button {
    margin-left: 10px;
    background-color: #858da8;
    font-size: 13px;
    padding: 3px;
    color: #ffffff;
  }
  p {
    font-size: 16px;
    font-weight: bold;
    color: #2759a2;
  }
  span {
    font-size: 12px;
  }
`;

const ButtonDiv = styled.div`
  height: 100%;
  display: flex;
  align-items: flex-end;
  button {
    font-size: 12px;
    padding: 3px;
    color: #858da8;
  }
`;
export default StudyCommentList;
