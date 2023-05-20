import { useEffect, useState } from "react";
import TagDropdown from "./TagDropdown";
import { StudyInfoDto } from "../apis/StudyGroupApi";
import { eduApi } from "../apis/EduApi";
import StudyListTag from "./StudyListTag";

const TagInput = ({ selectedCategory }: { selectedCategory: string }) => {
  const [view, setView] = useState(false);

  const [defaultTag, setDefaultTag] = useState<{ [key: string]: string }>({});
  const [createdTag, setCreatedTag] = useState<string>("");
  const [tags, setTags] = useState<string[]>([]);

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
        setCreatedTag("");
      }
    }
  };

  useEffect(() => {
    setView(false);
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
      <input
        type="text"
        value={createdTag}
        onChange={handleTag}
        onKeyDown={handleTagPost}
      />

      <ul
        onClick={() => {
          setView(!view);
        }}
      >
        {selectedCategory} {view ? "⌃" : "⌄"}
        {view && defaultTag && (
          <TagDropdown
            defaultTags={[defaultTag[selectedCategory]]}
            setTags={setTags}
            tags={tags}
          />
        )}
      </ul>
      <div>
        {tags.map((tag) => {
          return <StudyListTag key={tag}>{tag}</StudyListTag>;
        })}
      </div>
    </>
  );
};

export default TagInput;
