import styled from "styled-components";
import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import StudyListTag from "../components/StudyListTag";
import studyImage from "../assets/studyImage.webp";
import ListFilter from "../components/ListFilter";

const StudyList = () => {
  interface StudyListDto {
    id: number;
    title: string;
    tagValues: string[];
  }
  const initialState = [
    {
      id: 0,
      title: "",
      tagValues: [""],
    },
  ];

  const [fetching, setFetching] = useState(true);
  const [list, setList] = useState<StudyListDto[]>(initialState);
  const [filterData, setFilterData] = useState<StudyListDto[]>(initialState);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(
          `${import.meta.env.VITE_APP_API_URL}/studygroups?page=1&size=1000`
        );
        setList(response.data?.data);
        setFetching(false); // 데이터를 가져왔다는 걸 표시하는 플래그 함수, 렌더링했으면 undefined가 아니다
      } catch (error) {
        throw new Error("스터디 리스트 로딩에 실패했습니다.");
      }
    };
    fetchData();
  }, []);

  useEffect(() => {
    setFetching(true);
    setList(filterData);
    setFetching(false);
  }, ["", filterData]);

  return (
    <StudyListContainer>
      <StudyListBody>
        <StudyListTop>
          <div>
            <h2>여러분의 스터디를 만들어보세요!</h2>
          </div>
          <Link to="/studypost">
            <StudyPostButton>스터디 모집!</StudyPostButton>
          </Link>
        </StudyListTop>
        <ListFilterWrapper>
          <ListFilter setFilterData={setFilterData} />
        </ListFilterWrapper>
        <StudyListMain>
          {!fetching && (
            <StudyBoxContainer>
              {list?.map((item: StudyListDto) => (
                <StudyBox
                  key={item?.id}
                  onClick={() => navigate(`/studycontent/${item?.id}`)}
                >
                  <StudyListImage></StudyListImage>
                  <div>
                    <div className="studylist-title">
                      <h3>{item?.title}</h3>
                    </div>
                    <div className="studylist-tag">
                      <StudyListTag item={item.tagValues} />
                    </div>
                  </div>
                </StudyBox>
              ))}
            </StudyBoxContainer>
          )}
        </StudyListMain>
      </StudyListBody>
    </StudyListContainer>
  );
};

const StudyListContainer = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const StudyListBody = styled.div`
  width: 960px;
  height: 100%;
  padding: 100px 0;
  background-color: #fff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const StudyListTop = styled.div`
  width: 960px;
  display: flex;
  margin-bottom: 30px;
  justify-content: space-between;
  align-items: center;
  div {
    text-align: start;
    margin-left: 10px;
  }
  h2 {
    text-align: left;
    font-size: 2rem;
    color: #1f1f1f;
    font-weight: 700;
    margin-left: 10px;
  }
`;

const ListFilterWrapper = styled.div`
  width: 900px;
  margin-bottom: 30px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
`;

const StudyPostButton = styled.button`
  width: 160px;
  height: 48px;
  font-size: 1.2rem;
  color: #ffffff;
  background-color: #4994da;
  margin-right: 20px;

  &:hover {
    opacity: 85%;
  }
  &:active {
    opacity: 100%;
  }
`;

const StudyListMain = styled.div`
  width: 960px;
  display: flex;
  flex-flow: row wrap;
  justify-content: flex-start;
  align-items: center;
`;

const StudyBoxContainer = styled.div`
  display: flex;
  flex-flow: row wrap;
  justify-content: center;
  align-items: center;
`;

const StudyListImage = styled.div`
  width: 260px;
  height: 180px;
  background-image: url(${studyImage});
  background-size: cover;
  background-color: aliceblue;
`;

const StudyBox = styled.div`
  flex-basis: 280px;
  height: 320px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
  margin: 10px 20px;
  padding: 5px;
  display: flex;
  flex-flow: column wrap;
  justify-content: center;
  align-items: center;
  cursor: pointer;

  .studylist-title {
    width: 260px;
    padding: 10px 0;
    color: #1f1f1f;
    display: flex;
    justify-content: flex-start;
    align-items: center;
  }
  .studylist-title > h3 {
    font-size: 16px;
    height: 32px;
    text-align: left;
    font-weight: 700;
  }
  .studylist-tag {
    width: 260px;
    padding-top: 10px;
    display: flex;
    flex-flow: row wrap;
    justify-content: flex-end;
    align-items: center;
  }
  .studylist-tag > div {
    height: 24px;
    color: #39739d;
    font-size: 0.8125rem;
    border-radius: 4px;
    background-color: #e1ecf4;
    padding: 4.8px 6px;
    margin: 0 7px 4px 0;
    cursor: pointer;
  }
`;

export default StudyList;
