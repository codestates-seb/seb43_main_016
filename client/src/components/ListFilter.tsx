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
    <>
      <select
        name="listFilter"
        value={selectedOption}
        onChange={handleOptionChange}
      >
        <option value="기본값">기본값</option>
        <option value="수정순">수정순</option>
        <option value="카테고리순">카테고리순</option>
        <option value="모집순">모집순</option>
      </select>
      {renderFilterOptions()}
    </>
  );
};
const CategoryOptions = styled.div`
  display: flex;
  align-items: baseline;
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
