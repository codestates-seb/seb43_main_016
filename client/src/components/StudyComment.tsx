import styled from "styled-components";
//import { useParams } from "react-router-dom";
import { useState } from "react";
// import { Link } from "react-router-dom";
import { validateEmptyInput } from "../pages/utils/loginUtils";
import { postContent } from "../apis/CommentApi";

const StudyComment = () => {
  //let { id } = useParams();

  const [comment, setComment] = useState("");

  const handleComment = (e: React.ChangeEvent<HTMLInputElement>) => {
    setComment(e.target.value);
    //console.log(id);
  };

  const handleCommentButton = async () => {
    if (validateEmptyInput(comment)) {
      alert("댓글 내용을 입력해주세요");
    } else {
      console.log(comment);
      try {
        await postContent(comment);
        console.log("댓글이 성공적으로 등록되었습니다.");
      } catch (error) {
        console.log(error);
        console.log("댓글 등록에 실패했습니다.");
      }
    }
  };

  return (
    <StudyCommentContainer>
      <CommentInput>
        <input
          onChange={handleComment}
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
