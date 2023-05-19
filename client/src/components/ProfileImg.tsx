import { useState } from "react";
import styled from "styled-components";
import tokenRequestApi from "../apis/TokenRequestApi";

interface Props {
  profileImage: string | undefined;
}

const ProfileImg = ({ profileImage }: Props) => {
  const [imageUrl, setImageUrl] = useState<string>(profileImage || "");

  // TODO refator : if(undefiend || null === basic img)

  // TODO 프로필 이미지를 업로드하여 상태에 담아놓는 코드
  const checkImg = (e: React.ChangeEvent<HTMLInputElement>): any => {
    const selectedFile: File | undefined = e.target.files?.[0];

    if (selectedFile) {
      // 파일의 유효성 검사
      const allowedTypes: string[] = ["image/png", "image/jpeg", "image/jpg"]; // 파일의 타입은 png, jpeg, jpg만 허용
      if (!allowedTypes.includes(selectedFile.type)) {
        alert("프로필 이미지는 png, jpeg, jpg 파일만 업로드 가능합니다.");
        return;
      }
      const maxSize = 3 * 1024 * 1024; // 파일의 사이즈는 3MB를 넘을 수 없음
      if (selectedFile.size > maxSize) {
        alert("프로필 이미지는 3MB를 넘을 수 없습니다.");
        return;
      }

      const reader: FileReader = new FileReader();
      reader.readAsDataURL(selectedFile);
      reader.onloadend = () => {
        const base64Image: string = reader.result as string;
        setImageUrl(base64Image);
        console.log(base64Image); // ! 구현 완료 시 삭제되어야 할 코드
      };
    }
  };

  // TODO 업로드 된 이미지를 서버에 저장할 것을 요청하는 코드
  const updateImg = async () => {
    try {
      await tokenRequestApi.patch("/members/profile-image", {
        image: imageUrl,
      });
      console.log("프로필 사진을 업로드하는데 성공했습니다.");
      console.log(imageUrl);
    } catch (error) {
      console.error("프로필 사진을 업로드하는데 실패했습니다.", error);
      throw new Error("프로필 사진을 업로드하는데 실패했습니다.");
    }
  };

  // TODO 리턴문
  return (
    <ProfileImgWrapper>
      <ProfileImgSection>
        <img src={imageUrl} alt="Profile image" />
      </ProfileImgSection>
      <input type="file" accept=".png,.jpg,.jpeg" onChange={checkImg} />
      <button onClick={updateImg}>Profile Change</button>
    </ProfileImgWrapper>
  );
};

export default ProfileImg;

const ProfileImgWrapper = styled.div``;

const ProfileImgSection = styled.div``;
