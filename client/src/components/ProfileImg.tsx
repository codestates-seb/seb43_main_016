import { useState, useEffect } from "react";
import axios from "axios";
import styled from "styled-components";

interface Props {
  profileImage: string | undefined;
}

const ProfileImg = ({ profileImage }: Props) => {
  const [imageUrl, setImageUrl] = useState<string>(profileImage || "");

  // TODO 이미지가 잘 업로드 되는지 확인하는 코드
  const checkImg = (e: React.ChangeEvent<HTMLInputElement>): any => {
    const selectedFile: File | undefined = e.target.files?.[0];

    if (selectedFile) {
      console.log(selectedFile); // ! 구현 완료 시 삭제되어야 할 코드

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
        const imageUrl: string = reader.result as string;
        setImageUrl(imageUrl);
        console.log(imageUrl); // ! 구현 완료 시 삭제되어야 할 코드
      };
    }
  };

  // TODO 업로드 된 이미지를 S3에 저장할 것을 요청하는 코드
  const updateImg = async () => {
    const id = 1; // ! 임시로 1로 설정
    const formData = new FormData();
    formData.append("image", imageUrl);
    try {
      const res = await fetch(
        `http://localhost:3001/members/${id}/profile-image`,
        {
          method: "POST",
          body: formData,
        }
      );
      const data = await res.json();
      console.log(data);
    } catch (err) {
      console.log(err);
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
