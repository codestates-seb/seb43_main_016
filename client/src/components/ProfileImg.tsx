import { useState } from "react";
import styled from "styled-components";
import { updateMemberProfileImage } from "../apis/MemberApi";

interface Props {
  profileImage: string | undefined;
}

const ProfileImg = ({ profileImage }: Props) => {
  const [imageUrl, setImageUrl] = useState<string>(profileImage || "");

  const checkImg = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const selectedFile: File | undefined = e.target.files?.[0];

    if (selectedFile) {
      const allowedTypes: string[] = ["image/png", "image/jpeg", "image/jpg"];
      if (!allowedTypes.includes(selectedFile.type)) {
        alert("프로필 이미지는 png, jpeg, jpg 파일만 업로드 가능합니다.");
        return;
      }
      const maxSize = 3 * 1024 * 1024;
      if (selectedFile.size > maxSize) {
        alert("프로필 이미지는 3MB를 넘을 수 없습니다.");
        return;
      }

      const reader: FileReader = new FileReader();
      reader.readAsDataURL(selectedFile);
      reader.onloadend = () => {
        const base64Image: string = reader.result as string;
        setImageUrl(base64Image);
      };
    }
  };

  const updateImg = async (): Promise<void> => {
    try {
      await updateMemberProfileImage({ profileImage: imageUrl });
      alert("프로필 이미지가 변경되었습니다.");
    } catch (error) {
      alert("프로필 이미지 변경에 실패하였습니다.");
    }
  };

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
