import { useEffect, useState } from "react";
import { getTagInfo } from "../apis/tagApi";
import TagInput from "../components/TagInput";

const Home = () => {
  const [tag, setTag] = useState<{ [key: string]: string } | null>(null);

  useEffect(() => {
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
      <TagInput />
    </>
  );
};

export default Home;
