import { eduApi } from "./EduApi";
import { StudyInfoDto } from "./StudyGroupApi";

export async function getTagInfo() {
  const response = await eduApi.get<StudyInfoDto>(`/search/all`);
  return response.data.tags;
}
