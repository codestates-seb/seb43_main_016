import { useState } from "react";
import styled from "styled-components";
import { StudyGroup, getStudyListOrder } from "../apis/StudyGroupApi";
const ListFilter = ({
  setFilterData,
}: {
  setFilterData: React.Dispatch<React.SetStateAction<StudyGroup[]>>;
}) => {
  const [selectedOption, setSelectedOption] = useState("기본값");
  const [isAscending, _setIsAscending] = useState(false);

  const handleOptionChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedOption(e.target.value);
    getStudyListOrder(selectedOption, isAscending).then((data) => {
      setFilterData(data);
    });
  };

  return (
    <ListFilterContainer>
      <select
        name="listFilter"
        value={selectedOption}
        onChange={handleOptionChange}
      >
        <option value="기본값">최신등록순</option>
        <option value="수정순">업데이트순</option>
        <option value="카테고리순">카테고리</option>
        <option value="모집순">모집중/완료</option>
      </select>
    </ListFilterContainer>
  );
};

const ListFilterContainer = styled.div`
  select {
    width: 100px;
    height: 30px;
    padding: 2px 3px;
    border: none;
    border-radius: 3px;
  }
`;

export default ListFilter;