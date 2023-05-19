import { useEffect, useState } from "react";
import TagDropdown from "./TagDropdown";
import { StudyInfoDto } from "../apis/StudyGroupApi";
import { eduApi } from "../apis/EduApi";

const TagInput = ({ selectedCategory }: { selectedCategory: string }) => {
  const [view, setView] = useState(false);

  const [tag, setTag] = useState<{ [key: string]: string }>({});

  useEffect(() => {
    setView(false);
    const fetchData = async () => {
      try {
        const response = await eduApi.get<StudyInfoDto>(
          `/search?key=${selectedCategory}`
        );
        const result = response.data.tags;
        setTag(result);
      } catch (error) {
        console.log(error);
      }
    };

    fetchData();
  }, []);

  return (
    <>
      <input type="text" /*value={tag} onChange={}*/ />

      <ul
        onClick={() => {
          setView(!view);
        }}
      >
        {selectedCategory} {view ? "⌃" : "⌄"}
        {view && tag && <TagDropdown tags={tag[selectedCategory]} />}
      </ul>
    </>
  );
};
export default TagInput;
