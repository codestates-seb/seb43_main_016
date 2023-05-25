const StudyListTag = ({ item }: { item: string[] }) => {
  return <>{item && item.map((tag) => <div key={tag}>{tag}</div>)}</>;
};
export default StudyListTag;
