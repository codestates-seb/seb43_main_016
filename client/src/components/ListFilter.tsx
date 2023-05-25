import { useState } from "react";
import styled from "styled-components";
const ListFilter = () => {
  const [selectedOption, setSelectedOption] = useState("기본값");

  const handleOptionChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedOption(e.target.value);
  };

  const renderFilterOptions = () => {
    if (selectedOption === "카테고리순") {
      return (
        <CategoryOptions>
          <CategoryLabel>
            <input type="checkbox" name="option1" value="Option 1" />
            프론트엔드
          </CategoryLabel>
          <CategoryLabel>
            <input type="checkbox" name="option2" value="Option 2" />
            백엔드
          </CategoryLabel>
          <CategoryLabel>
            <input type="checkbox" name="option3" value="Option 3" />
            알고리즘
          </CategoryLabel>
          <CategoryLabel>
            <input type="checkbox" name="option3" value="Option 3" />
            인공지능
          </CategoryLabel>
          <CategoryLabel>
            <input type="checkbox" name="option3" value="Option 3" />
            기타
          </CategoryLabel>
        </CategoryOptions>
      );
    }
    return null;
  };

  return (
    <ListFilterContainer>
      <select
        name="listFilter"
        value={selectedOption}
        onChange={handleOptionChange}
      >
        <option value="기본값">기본값</option>
        <option value="수정순">업데이트순</option>
        <option value="카테고리순">카테고리순</option>
        <option value="모집순">모집중/완료</option>
      </select>
      {renderFilterOptions()}
    </ListFilterContainer>
  );
};

const ListFilterContainer = styled.div`
  display: flex;
  justify-content: flex-start;
  align-items: center;

  select {
    width: 100px;
    height: 30px;
    padding: 2px 3px;
    border: none;
    border-radius: 3px;
  }
`;

const CategoryOptions = styled.div`
  display: flex;
  align-items: center;
`;

const CategoryLabel = styled.label`
  margin-top: 10px;
  font-size: 14px;
  margin-left: 20px;
  input {
    margin: 0 3px 0 0;
  }
`;
export default ListFilter;
