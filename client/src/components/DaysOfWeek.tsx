import styled from "styled-components";

interface DaysOfWeekProps {
  checked: string[];
  setChecked: React.Dispatch<React.SetStateAction<string[]>>;
}

const DaysOfWeek = ({ checked, setChecked }: DaysOfWeekProps) => {
  const daysOfWeekList: string[] = ["일", "월", "화", "수", "목", "금", "토"] ;

  const handleCheck = (e: { target: { checked: any; value: string } }) => {
    let updatedList = [...checked];
    if (e.target.checked) {
      updatedList = [...checked, e.target.value];
    } else {
      updatedList.splice(checked.indexOf(e.target.value), 1);
    }
    setChecked(
      updatedList.sort(
        (a, b) => daysOfWeekList.indexOf(a) - daysOfWeekList.indexOf(b)
      )
    );
  };

  return (
    <DaysOfWeekContainer>
      {daysOfWeekList.map((item: string, index: number) => {
        return (
          <div className="checkbox" key={index}>
            <input
              value={item}
              type="checkbox"
              onChange={handleCheck}
              checked={checked.includes(item)}
            />
            <label id={item}>{item}</label>
          </div>
        );
      })}
    </DaysOfWeekContainer>
  );
};

const DaysOfWeekContainer = styled.div`
  width: 640px;
  display: flex;
  justify-content: flex-start;
  align-items: center;

  .checkbox {
    width: 42px;
    display: flex;
    justify-content: flex-start;
    align-items: center;
  }

  .checkbox > label {
    font-size: 15px;
    color: #1f1f1f;
    padding-left: 4px;
  }

  .checkbox > input {
    width: 14px;
    height: 14px;
    border: none;
  }
`;

export default DaysOfWeek;
