import styled from "styled-components";

const TagDropdown = ({
  defaultTags,
  tags,
  setTags,
  setIsInput,
}: {
  defaultTags: string[];
  tags: string[];
  setTags: React.Dispatch<React.SetStateAction<string[]>>;
  setIsInput: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  const handleTagClick = (item: string) => {
    if (tags.includes(item)) {
      alert("이미 존재하는 태그입니다.");
    } else {
      setTags([...tags, item]);
    }
  };
  return (
    <TagLiDiv>
      {defaultTags &&
        defaultTags.map((defaultTag) => {
          return (
            <li onClick={() => handleTagClick(defaultTag)} key={defaultTag}>
              {defaultTag}
            </li>
          );
        })}
      <button onClick={() => setIsInput(true)}>직접 입력하기</button>
    </TagLiDiv>
  );
};
const TagLiDiv = styled.div`
  width: 300px;
  display: flex;
  flex-flow: row wrap;
  gap: 3px;
  justify-content: center;
  align-items: center;
  list-style-type: none;
  background-color: white;
  border-radius: 5px;
  padding: 5px 15px;
  cursor: pointer;
  margin-top: 5px;
  font-size: 0.7rem;
  button {
    margin-top: 5px;
    background-color: #858da8;
    color: #ffffff;
    font-size: 10px;
  }
`;

export default TagDropdown;
