import styled from "styled-components";
import { useState } from "react";

const DaysOfWeek = () => {
  const daysOfWeekList: string[] = ["월", "화", "수", "목", "금", "토", "일"];

  const [checked, setChecked] = useState<string[]>([]);

  const handleCheck = (event: { target: { checked: any; value: string } }) => {
    let updatedList = [...checked];
    if (event.target.checked) {
      updatedList = [...checked, event.target.value];
      //   console.log(checked);
    } else {
      updatedList.splice(checked.indexOf(event.target.value), 1);
    }
    setChecked(updatedList);
  };

  return (
    <DaysOfWeekContainer>
      {daysOfWeekList.map((item: string, index: number) => {
        return (
          <CheckBox key={index}>
            <input value={item} type="checkbox" onChange={handleCheck} />
            <label id={item}>{item}</label>
          </CheckBox>
        );
      })}
    </DaysOfWeekContainer>
  );
};

const DaysOfWeekContainer = styled.div`
  width: 700px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
`;

const CheckBox = styled.div`
  width: 45px;
  display: flex;
  justify-content: center;
  align-items: center;

  label {
    font-size: 16px;
    color: #1f1f1f;
    padding-left: 2px;
  }

  input {
    width: 18px;
    height: 18px;
    border: none;
  }
`;

export default DaysOfWeek;
