import styled from "styled-components";

interface DaysOfWeekProps {
  checked: string[];
  setChecked: React.Dispatch<React.SetStateAction<string[]>>;
}

const DaysOfWeek = ({ checked, setChecked }: DaysOfWeekProps) => {
  const daysOfWeekList: string[] = ["월", "화", "수", "목", "금", "토", "일"];

  const handleCheck = (e: { target: { checked: any; value: string } }) => {
    let updatedList = [...checked];
    if (e.target.checked) {
      updatedList = [...checked, e.target.value];
    } else {
      updatedList.splice(checked.indexOf(e.target.value), 1);
    }
    setChecked(updatedList);
  };

  return (
    <DaysOfWeekContainer>
      {daysOfWeekList.map((item: string, index: number) => {
        return (
          <CheckBox key={index}>
            <input
              value={item}
              type="checkbox"
              onChange={handleCheck}
              checked={checked.includes(item)}
            />
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
    font-size: 15px;
    color: #1f1f1f;
    padding-left: 2px;
  }

  input {
    width: 14px;
    height: 14px;
    border: none;
  }
`;

export default DaysOfWeek;
