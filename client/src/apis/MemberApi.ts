import { useQuery } from "@tanstack/react-query";

interface UserProfile {
  email: string;
  profileImage: string;
  nickName: string;
  aboutMe: string;
  withMe: string;
  memberStatus: "MEMBER_ACTIVE" | "MEMBER_INACTIVE"; //
  roles: string[];
}

// ? 여기에 accessToken을 넣으려면 어떻게 해야할까?
function getUserProfileInfo() {
  const { isLoading, isError, data, isFetching } = useQuery<UserProfile>("userData", () =>
    fetch("http://ec2-43-202-20-65.ap-northeast-2.compute.amazonaws.com")
      .then((res) => res.json())
  );
  if (isLoading) {
    return "Loading";
  }
  if (isError) {
    return "Error";
  }
  if (isFetching) {
    return "Fetching";
  }
  return data;
}


