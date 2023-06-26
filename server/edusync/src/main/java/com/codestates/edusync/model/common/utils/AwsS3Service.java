package com.codestates.edusync.model.common.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AwsS3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
//    private final String bucketMember = "edusync.test/profile";
//    private final String bucketStrudy = "edusync.test/study";

    public String uploadImage(MultipartFile file, String bucketPath) {
        String fileName = createFileName(file.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            uploadFile(inputStream, objectMetadata, fileName, bucket.concat(bucketPath));
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("파일 변환 중 에러가 발생하였습니다 (%s)", file.getOriginalFilename()));
        }
        return getFileUrl(fileName, bucket.concat(bucketPath));
    }

    /**
     * S3에 저장할 파일명
     * @param originalFileName
     * @return
     */
    private String createFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    /**
     * 파일 포맷 유효성 검사
     * @param fileName
     * @return
     */
    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("잘못된 형식의 파일 (%s) 입니다", fileName));
        }
    }

    /**
     * S3에 이미지 업로드
     * @param inputStream
     * @param objectMetadata
     * @param fileName
     */
    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName, String bucketPath) {
        amazonS3.putObject(
                new PutObjectRequest(bucketPath, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

    }

    /**
     * 이미지 저장 경로
     * @param fileName
     * @param bucketPath
     * @return
     */
    public String getFileUrl(String fileName, String bucketPath) {
        return amazonS3.getUrl(bucketPath, fileName).toString();
    }

    /**
     * 이미지 삭제
     */
    public void deleteFile(String fileName, String bucketPath) {
        amazonS3.deleteObject(bucketPath, fileName);
    }
}