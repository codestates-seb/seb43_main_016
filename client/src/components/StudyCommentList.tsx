import { useEffect, useState } from "react";
import styled from "styled-components";
import { CommentDto, getComments, patchComment } from "../apis/CommentApi";
import { validateEmptyInput } from "../pages/utils/loginUtils";

const StudyCommentList = ({}) => {
  const [comments, setComments] = useState<CommentDto[]>([]);
  const [comment, setComment] = useState("");
  const [patchId, setPatchId] = useState<number | null>(null);
  const [isUpdateMode, setIsUpdateMode] = useState(false);

  const handleUpdate = (id: number | null, content: string) => {
    setIsUpdateMode(!isUpdateMode);
    setPatchId(id);
    setComment(content);
  };
  const handleComment = (e: React.ChangeEvent<HTMLInputElement>) => {
    setComment(e.target.value);
    //console.log(id);
  };

  const handleUpdateButton = async () => {
    if (validateEmptyInput(comment)) {
      alert("댓글 내용을 입력해주세요");
    } else {
      try {
        const studyGroupId = 31;
        if (patchId !== null) {
          await patchComment(studyGroupId, patchId, comment);
          setIsUpdateMode(false);
        }

        console.log("댓글이 성공적으로 등록되었습니다.");
      } catch (error) {
        console.log(error);
        console.log("댓글 등록에 실패했습니다.");
      }
    }
  };
  useEffect(() => {
    const fetchData = async () => {
      try {
        const studyGroupId = 31;
        const newComment = await getComments(studyGroupId);
        setComments(newComment);
      } catch (error) {
        console.log(error);
      }
    };
    fetchData();
  }, [!isUpdateMode]); // post시 바로 변경될 수 있도록 의존성 배열 추가 예정
  return (
    <>
      <ul>
        {comments.map((comment) => {
          return (
            <CommentItemDiv key={comment.commentId}>
              <div>
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
              </div>
              <ButtonDiv>
                <button
                  onClick={() =>
                    handleUpdate(comment.commentId, comment.content)
                  }
                >
                  수정
                </button>
                <button>삭제</button>
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
  div {
    text-align: left;
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
