import styled from "styled-components";
// import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
// import { StudyInfoDto, getStudyGroupInfo } from "../apis/StudyGroupApi";

const StudyListBox = () => {
  // const [data, setData] = useState<any>("");

  // useEffect(() => {
  //   const fetchData = async () => {
  //     try {
  //       const result = await getStudyGroupInfo(2);
  //       console.log(result);
  //       setData(result);
  //     } catch (error) {
  //       console.error("Error during GET request:", error);
  //     }
  //   };

  //   fetchData();
  // }, []);

  return (
    <StudyListBoxContainer>
      <Link to="/studycontent">
        <StudyListImage></StudyListImage>
        <StudyListTitle>
          {/* {data.map((item: StudyInfoDto) => (
            <h3 key={item.id}>{item.studyName}</h3>
          ))} */}
          <h3>Dummy Title</h3>
        </StudyListTitle>
        <StudyListTag>
          <div>javascript</div>
          <div>typescript</div>
        </StudyListTag>
      </Link>
    </StudyListBoxContainer>
  );
};

const StudyListBoxContainer = styled.div`
  flex-basis: 280px;
  height: 310px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
  margin: 10px 20px;
  padding: 5px;
  display: flex;
  flex-flow: column wrap;
  justify-content: center;
  align-items: center;
`;

const StudyListImage = styled.div`
  width: 260px;
  height: 180px;
  background-color: aliceblue;
`;

const StudyListTitle = styled.div`
  width: 260px;
  padding: 15px 0;
  color: #1f1f1f;
  display: flex;
  justify-content: flex-start;
  align-items: center;

  h3 {
    font-size: 1.125rem;
    font-weight: 700;
  }
`;

const StudyListTag = styled.div`
  width: 260px;
  padding-top: 10px;
  display: flex;
  justify-content: flex-end;
  align-items: center;

  div {
    height: 24px;
    color: #39739d;
    font-size: 0.8125rem;
    border-radius: 4px;
    background-color: #e1ecf4;
    padding: 4.8px 6px;
    margin-left: 7px;
    cursor: pointer;
  }
`;

export default StudyListBox;
