import { useEffect, useState } from "react";
import { getTagInfo } from "../apis/tagApi";
import TagDropdown from "./TagDropdown";

const TagInput = () => {
  const [view, setView] = useState(false);

  const [tag, setTag] = useState<{ [key: string]: string }>({});

  useEffect(() => {
    setView(false);

    const fetchData = async () => {
      try {
        const result = await getTagInfo();
        setTag(result);
      } catch (error) {
        console.log(error);
      }
    };

    fetchData();
  }, []);
  return (
    <>
      <ul
        onClick={() => {
          setView(!view);
        }}
      >
        프론트엔드 {view ? "⌃" : "⌄"}
        {view && tag && <TagDropdown tags={tag.프론트엔드} />}
      </ul>
    </>
  );
};
export default TagInput;
