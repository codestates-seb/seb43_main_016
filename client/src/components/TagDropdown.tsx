import styled from "styled-components";

const TagDropdown = ({
  defaultTags,
  tags,
  setTags,
}: {
  defaultTags: string[];
  tags: string[];
  setTags: React.Dispatch<React.SetStateAction<string[]>>;
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
      {defaultTags.map((defaultTag) => {
        return (
          <li onClick={() => handleTagClick(defaultTag)} key={defaultTag}>
            {defaultTag}
          </li>
        );
      })}
    </TagLiDiv>
  );
};
const TagLiDiv = styled.div`
  list-style-type: none;
  background-color: white;
  border-radius: 5px;
  padding: 5px 15px;
  cursor: pointer;
  margin-top: 5px;
  font-size: 0.7rem;
`;
export default TagDropdown;
