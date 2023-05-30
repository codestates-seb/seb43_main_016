import { useEffect, useState } from "react";
import styled from "styled-components";
import TagDropdown from "./TagDropdown";
import { StudyInfoDto } from "../apis/StudyGroupApi";
import { eduApi } from "../apis/EduApi";

const TagInput = ({
  selectedCategory,
  tags,
  setTags,
  viewTag,
  setViewTag,
  isInput,
  setIsInput,
}: {
  selectedCategory: string;
  tags: string[];
  setTags: React.Dispatch<React.SetStateAction<string[]>>;
  viewTag: boolean;
  setViewTag: React.Dispatch<React.SetStateAction<boolean>>;
  isInput: boolean;
  setIsInput: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  const [defaultTag, setDefaultTag] = useState<{ [key: string]: string[] }>({});
  const [createdTag, setCreatedTag] = useState("");

  const handleTag = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCreatedTag(e.target.value);
  };

  const handleTagPost = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      e.preventDefault();
      if (tags.includes(createdTag)) {
        alert("이미 존재하는 태그입니다.");
      } else if (createdTag) {
        setTags([...tags, createdTag]);
      }
      setCreatedTag("");
    }
  };

  const handleDelete = (tag: string) => {
    const updatedTags = tags.filter((clickedTag) => clickedTag !== tag);
    setTags(updatedTags);
  };

  useEffect(() => {
    setTags([]);
    setCreatedTag("");
    const fetchData = async () => {
      try {
        const response = await eduApi.get<StudyInfoDto>(
          `/search?key=${selectedCategory}`
        );
        const result = response.data.tags;
        setDefaultTag(result);
      } catch (error) {}
    };

    fetchData();
  }, [selectedCategory]);

  return (
    <TagInputContainer>
      <TagInputBox>
        <ul
          onClick={() => {
            setViewTag(!viewTag);
          }}
        >
          {selectedCategory} {viewTag ? "⌃" : "⌄"}
          {viewTag && defaultTag && (
            <TagDropdown
              defaultTags={defaultTag[selectedCategory]}
              setTags={setTags}
              tags={tags}
              setIsInput={setIsInput}
            />
          )}
        </ul>
        {isInput && (
          <input
            className="tag-write-input"
            type="text"
            value={createdTag}
            onChange={handleTag}
            onKeyDown={handleTagPost}
          />
        )}
      </TagInputBox>
      <SelectedTagWrapper>
        <div className="selected-tag">
          {tags &&
            tags.map((tag) => {
              if (tag === "") return [];
              return (
                <StudyTag onClick={() => handleDelete(tag)} key={tag}>
                  {tag} x
                </StudyTag>
              );
            })}
        </div>
      </SelectedTagWrapper>
    </TagInputContainer>
  );
};
const StudyTag = styled.div`
  height: 24px;
  color: #39739d;
  font-size: 13px;
  border-radius: 4px;
  background-color: #e1ecf4;
  padding: 2px 6px 5px 6px;
  margin: 0 3px 3px;
  cursor: pointer;
`;
const TagInputContainer = styled.div`
  width: 800px;
  display: flex;
  flex-flow: row wrap;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
`;

const TagInputBox = styled.div`
  display: flex;
  justify-content: flex-start;
  align-items: center;

  .tag-write-input {
    width: 120px;
    display: flex;
  }
`;

const SelectedTagWrapper = styled.div`
  display: flex;
  justify-content: flex-start;
  align-items: center;

  .selected-tag {
    width: 500px;
    margin-top: 12px;
    display: flex;
    flex-flow: row wrap;
    justify-content: flex-start;
    align-items: center;
  }
`;

export default TagInput;
