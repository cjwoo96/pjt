package com.example.S3_fileUpDownLoad.Service;
import java.io.InputStream;
import java.net.URL;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@RequiredArgsConstructor
@Service
public class FileService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.s3.region.static}")
    private String region;


//    public String uploadFile(MultipartFile file) throws Exception{
//        String fileName=file.getOriginalFilename();
//        String fileKey="test/"+fileName;
//        String fileUrl= "https://" + bucket + ".s3."+region+".amazonaws.com/" + fileKey;
//        ObjectMetadata metadata=new ObjectMetadata();
//        metadata.setContentLength(file.getSize());
//        metadata.setContentType(file.getContentType());
//        amazonS3Client.putObject(bucket,fileKey,file.getInputStream(),metadata);
//
//        return fileUrl;
//    }


    public String uploadFile(MultipartFile file,String userid) throws Exception{
        String fileName=file.getOriginalFilename();
        String fileKey=userid+"/"+fileName;
        //String fileUrl= "https://" + bucket + ".s3."+region+".amazonaws.com/" + fileKey;
        ObjectMetadata metadata=new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        amazonS3Client.putObject(bucket,fileKey,file.getInputStream(),metadata);

        return fileKey;
    }

    public void downloadFile(String bucketName,String fileKey,String localFilePath) throws IOException {
        S3Object s3Object = amazonS3Client.getObject(bucketName, fileKey);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        Files.copy(inputStream, Paths.get(localFilePath), StandardCopyOption.REPLACE_EXISTING);
    }
//    public String uploadFile(MultipartFile file) throws IOException {
//        String key = file.getOriginalFilename();
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentLength(file.getSize());
//        s3Client.putObject(new PutObjectRequest(bucketName, key, file.getInputStream(), metadata));
//        return s3Client.getUrl(bucketName, key).toString();
//    }

//    public ResponseEntity<byte[]>

//
//    public void deleteFile(String fileName) {
//        s3Client.deleteObject(bucketName, fileName);
//    }
}
