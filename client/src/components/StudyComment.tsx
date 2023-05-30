import styled from "styled-components";
import { useRecoilValue } from "recoil";
import { LogInState } from "../recoil/atoms/LogInState";
import { useState } from "react";
import { CommentDto } from "../apis/CommentApi";
import { getComments } from "../apis/CommentApi";
import { validateEmptyInput } from "../pages/utils/loginUtils";
import { postComment } from "../apis/CommentApi";
import { useNavigate } from "react-router-dom";

const StudyComment = ({
  studyGroupId,
  setComments,
}: {
  studyGroupId: number;
  setComments: React.Dispatch<React.SetStateAction<CommentDto[]>>;
}) => {
  const isLoggedIn = useRecoilValue(LogInState);
  const navigate = useNavigate();

  const [comment, setComment] = useState("");

  const [isEnterPressed, setIsEnterPressed] = useState(false);

  const handleEnter = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter" && !isEnterPressed) {
      setIsEnterPressed(true);

      handleCommentButton();

      setTimeout(() => {
        setIsEnterPressed(false);
      }, 1000); // enter로 입력시 발생하는 이중 입력 방지
    }
  };

  const handleComment = (e: React.ChangeEvent<HTMLInputElement>) => {
    setComment(e.target.value);
  };

  const handleCommentButton = async () => {
    if (!isLoggedIn) navigate("/login");
    else if (validateEmptyInput(comment)) {
      alert("댓글 내용을 입력해주세요");
    } else {
      try {
        await postComment(studyGroupId, comment);
        setComment("");
        const fetchData = async () => {
          try {
            const newComment = await getComments(studyGroupId);
            setComments(newComment);
          } catch (error) {}
        };
        fetchData();
      } catch (error) {
        alert("댓글 등록 실패했습니다.");
      }
    }
  };

  return (
    <StudyCommentContainer>
      <CommentInput>
        <input
          value={comment}
          onChange={handleComment}
          onKeyDown={handleEnter}
          type="text"
          placeholder="댓글을 입력하세요."
          required
        />
      </CommentInput>
      <CommentButtonWrapper>
        <CommentButton onClick={handleCommentButton}>댓글 작성</CommentButton>
      </CommentButtonWrapper>
    </StudyCommentContainer>
  );
};

const StudyCommentContainer = styled.div`
  width: 800px;
  height: 200px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
  margin: 10px 20px;
  padding: 20px 0 10px;
  display: flex;
  flex-flow: column wrap;
  justify-content: center;
  align-items: center;
`;

const CommentInput = styled.div`
  input {
    width: 720px;
    height: 90px;
  }
`;

const CommentButtonWrapper = styled.div`
  width: 720px;
  margin: 15px 0;
  display: flex;
  justify-content: flex-end;
`;

const CommentButton = styled.button`
  width: 130px;
  height: 42px;
  font-size: 1.125rem;
  color: #ffffff;
  background-color: #4994da;

  &:hover {
    opacity: 85%;
  }
  &:active {
    opacity: 100%;
  }
`;

export default StudyComment;
