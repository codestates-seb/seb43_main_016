const StudyListTag = ({ item }: { item: string[] }) => {
  return <>{item && item.map((tag) => <div>{tag}</div>)}</>;
};
export default StudyListTag;
