import { useState, useRef } from "react";
import styled from "styled-components";
import { updateMemberProfileImage } from "../apis/MemberApi";

interface Props {
  profileImage: string | undefined;
}

const ProfileImg = ({ profileImage }: Props) => {
  const fileInputRef = useRef<HTMLInputElement>(null);
  const [imageUrl, setImageUrl] = useState<string>(profileImage || "");
  const [isEditing, setIsEditing] = useState<boolean>(false);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const file: File | undefined = e.target.files?.[0];
    if (file) {
      const reader: FileReader = new FileReader();
      reader.readAsDataURL(file);
      reader.onloadend = () => {
        const base64Image: string = reader.result as string;
        setImageUrl(base64Image);
      };
      setIsEditing(true);
    }
  };

  const cancelUpload = (): void => {
    setIsEditing(false);
    setImageUrl(profileImage || "");
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
        <label htmlFor="profile-image">
          {!isEditing ? (
            <img src={profileImage} alt="Profile image" />
          ) : (
            <img src={imageUrl} alt="Profile image" />
          )}
        </label>
        <input
          ref={fileInputRef}
          id="profile-image"
          type="file"
          accept=".png,.jpg,.jpeg"
          onChange={handleFileChange}
          style={{ display: "none" }}
        />
      </ProfileImgSection>
      {isEditing && (
        <>
          <ButtonGroup>
            <CancelButton onClick={cancelUpload}>Cancel</CancelButton>
            <ConfirmButton onClick={updateImg}>Upload</ConfirmButton>
          </ButtonGroup>
        </>
      )}
    </ProfileImgWrapper>
  );
};

export default ProfileImg;

const ProfileImgWrapper = styled.div``;

const ProfileImgSection = styled.div`
  width: 200px;
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  border-radius: 50%;
  border: 2px solid #ccc;
  margin-bottom: 10px;
  position: relative;
  cursor: pointer;

  label {
    width: 100%;
    height: 100%;
    position: relative;

    &:hover {
      background-color: #ccc;
      border: 2px solid #3383fa;

      &::after {
        content: "프로필 수정";
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        background-color: rgba(0, 0, 0, 0.5);
        color: #fff;
        padding: 8px 16px;
        border-radius: 4px;
        font-size: 13px;
        cursor: pointer;
      }
    }

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      cursor: pointer;
    }
  }
`;

const ButtonGroup = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 10px;
`;

const CancelButton = styled.button`
  background-color: #ccc;
  color: #fff;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  margin-right: 8px;
  cursor: pointer;

  &:hover {
    background-color: #999;
  }
`;

const ConfirmButton = styled.button`
  background-color: #337ab7;
  color: #fff;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;

  &:hover {
    background-color: #23527c;
  }
`;
