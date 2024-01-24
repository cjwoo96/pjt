package com.example.S3_fileUpDownLoad.Service;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;


@Service
public class FileService {

    private final AmazonS3Client amazonS3Client;

    /*
        @Autowired
        @Bean에서 관리하고 있던 AmazonS3Client 인스턴스가 주입됨.
        필드 주입보다 생성자 주입이 불변성과 테스트 용이성 측면에서 나음.

     */
    @Autowired
    public FileService(AmazonS3Client amazonS3Client){
        this.amazonS3Client=amazonS3Client;
    }

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.s3.region.static}")
    private String region;

    // filekey = 사용자 ID/파일이름
    // 파일 저장될 위치, url이 유효할 시간
    public URL generatePresignedUrl(String fileKey, long durationMillis) {
        // Date -> java에서 현재시간을 알려주는 객체.
        Date expiration = new Date();
        // 만료시간 설정
        expiration.setTime(expiration.getTime() + durationMillis);
        /*
        GeneratePresignedUrlRequest
        객체 생성.
        generatePresignedUrlRequest.setBucketName(bucket);
        generatePresignedUrlRequest.setKey(fileKey);
        -> 버킷과 파일키 지정
        generatePresignedUrlRequest.setMethod(HttpMethod.PUT);
        -> HttpMethod 생성해 메소드를 put으로 설정
        generatePresignedUrlRequest.setExpiration(expiration);
        만료시간 설정.
         */
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileKey)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(expiration);

        return amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
    }


    public String uploadFile(MultipartFile file,String userid) throws Exception{
        String fileName=file.getOriginalFilename();
        String fileKey=userid+"/"+fileName;
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

}
