import styled from "styled-components";

const StudyListTag = ({ children }: { children: React.ReactNode }) => {
  return (
    <>
      <StudyTag>{children}</StudyTag>
    </>
  );
};

const StudyTag = styled.div`
  height: 24px;
  color: #39739d;
  font-size: 0.8125rem;
  border-radius: 4px;
  background-color: #e1ecf4;
  padding: 2px 6px 5px 6px;
  margin-left: 7px;
  cursor: pointer;
`;
export default StudyListTag;
//width: 260px;
//padding-top: 10px;
//display: flex;
//justify-content: flex-end;
//align-items: center;
