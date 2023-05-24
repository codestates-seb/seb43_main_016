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
  const [selectedFile, setSelectedFile] = useState<File | null>(null);

  const checkImg = (): void => {
    if (fileInputRef.current) {
      fileInputRef.current.click();
    }
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const file: File | undefined = e.target.files?.[0];
    if (file) {
      setSelectedFile(file);
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
    setSelectedFile(null);
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
        {!isEditing ? (
          <label htmlFor="profile-image">
            <img src={profileImage} alt="Profile image" />
          </label>
        ) : (
          <img src={imageUrl} alt="Profile image" />
        )}
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
      {!isEditing && (
        <UploadButton onClick={checkImg}>Change Profile Image</UploadButton>
      )}
    </ProfileImgWrapper>
  );
};

export default ProfileImg;

const ProfileImgWrapper = styled.div``;

const ProfileImgSection = styled.div`
  width: 150px;
  height: 150px;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  `

const ButtonGroup = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 10px;

`;

const UploadButton = styled.button``;

const CancelButton = styled.button``;

const ConfirmButton = styled.button``;
