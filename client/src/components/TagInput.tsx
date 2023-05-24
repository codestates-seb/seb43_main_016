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
        console.log([...tags, createdTag]);
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
      } catch (error) {
        console.log(error);
      }
    };

    fetchData();
  }, [selectedCategory]);

  return (
    <>
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
        <TagWriteInput
          type="text"
          value={createdTag}
          onChange={handleTag}
          onKeyDown={handleTagPost}
        />
      )}
      <div>
        {tags.map((tag) => {
          return (
            <StudyTag onClick={() => handleDelete(tag)} key={tag}>
              {tag} x
            </StudyTag>
          );
        })}
      </div>
    </>
  );
};
const StudyTag = styled.div`
  height: 24px;
  color: #39739d;
  font-size: 0.8125rem;
  border-radius: 4px;
  background-color: #e1ecf4;
  padding: 2px 6px 5px 6px;
  margin-right: 7px;
  cursor: pointer;
`;
const TagWriteInput = styled.input`
  width: 150px;
`;

export default TagInput;
