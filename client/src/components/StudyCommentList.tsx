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

const StudyCommentList = ({
  isLeader,
  studyGroupId,
  comments,
  setComments,
}: {
  isLeader: boolean;
  studyGroupId: number;
  comments: CommentDto[];
  setComments: React.Dispatch<React.SetStateAction<CommentDto[]>>;
}) => {
  const isLoggedIn = useRecoilValue(LogInState);
  const navigate = useNavigate();

  const [comment, setComment] = useState("");
  const [patchId, setPatchId] = useState<number | null>(null);
  const [isUpdateMode, setIsUpdateMode] = useState(false);
  const [isEnterPressed, setIsEnterPressed] = useState(false);

  const handleComment = (e: React.ChangeEvent<HTMLInputElement>) => {
    setComment(e.target.value);
    //console.log(id);
  };

  const handleEnter = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter" && !isEnterPressed) {
      setIsEnterPressed(true);

      handleUpdateButton();

      setTimeout(() => {
        setIsEnterPressed(false);
      }, 1000); // enter로 입력시 발생하는 이중 입력 방지
    }
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
      await deleteComment(studyGroupId, patchId);
      fetchCommentsData();
    } catch (error) {
      console.log("댓글 삭제 실패", error);
    }
  };

  const fetchCommentsData = async () => {
    try {
      const newComment = await getComments(studyGroupId);
      setComments(newComment);
    } catch (error) {
      console.log(error);
    }
  };
  useEffect(() => {
    fetchCommentsData();
  }, [!isUpdateMode]);
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
                        onKeyDown={handleEnter}
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
                  </>
                )}

                {(isLeader || comment.isMyComment) && (
                  <button onClick={() => handleDelete(comment.commentId)}>
                    삭제
                  </button>
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
