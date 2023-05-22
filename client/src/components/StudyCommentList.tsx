import { useEffect, useState } from "react";
import { CommentDto, getComments } from "../apis/CommentApi";
const StudyCommentList = () => {
  const [comments, setComments] = useState<CommentDto[]>([]);
  useEffect(() => {
    const fetchData = async () => {
      try {
        const newComment = await getComments();
        setComments(newComment);
        console.log(newComment);
      } catch (error) {
        console.log(error);
      }
    };
    fetchData();
  }, []); // post시 바로 변경될 수 있도록 의존성 배열 추가 예정
  return (
    <>
      <ul>
        {comments.map((comment) => {
          return (
            <div key={comment.commentId}>
              <li>{comment.content}</li>

              <li>-{comment.nickName}-</li>
            </div>
          );
        })}
      </ul>
    </>
  );
};
export default StudyCommentList;
