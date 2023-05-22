import styled from "styled-components";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

const StudyListBox = () => {
  interface StudyListDto {
    id: number;
    title: string;
    tagValues: {
      [key: string]: string;
    };
  }
  const initialState = {
    id: 1,
    title: "",
    tagValues: { [""]: "" },
  };

  const [fetching, setFetching] = useState(true);
  const [list, setList] = useState<StudyListDto>(initialState);

  useEffect(() => {
    const fetchData = async () => {
      const response = await axios.get(
        `${import.meta.env.VITE_APP_API_URL}/studygroups?page=1&size=6`
      );
      if (response) setList(response.data);
      setFetching(false); // 데이터를 가져왔다는 걸 표시하는 플래그 함수, 렌더링했으면 undefined가 아니다
      console.log(list?.title);
    };
    // try catch는 명시적인 exeption이 없을 때만 뜬다, if로 명시적 예외처리 했으면 안쓴다?
    // throw를 했을 때 잡는다? 안하는 게 좋다
    fetchData();
  }, []);

  return (
    <>
      {!fetching && (
        <StudyListBoxContainer>
          <Link to="/studycontent">
            <StudyListImage></StudyListImage>
            <StudyListTitle>
              <h3 key={list?.id}>{list?.title}</h3>
              {/* 요소 렌더링하는 게 하나일 땐 map을 쓸 수 없다 */}
              {/* <h3>Dummy Title</h3> */}
            </StudyListTitle>
            <StudyListTag>
              <div>javascript</div>
              <div>typescript</div>
            </StudyListTag>
          </Link>
        </StudyListBoxContainer>
      )}
    </>
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
