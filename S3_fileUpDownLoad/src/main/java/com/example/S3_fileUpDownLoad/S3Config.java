package com.example.S3_fileUpDownLoad;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration -> 스프링의 구성 클래스 임을 나타냄. 이 클래스는 @bean을 제공.
@Configuration
public class S3Config {
    //@Value는 외부에서 값을 가져 올 때 사용한다.
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
    @Value("${cloud.aws.s3.region.static}")
    private String region;

    /*
    AmazonS3Client
    - 파일 업로드 및 다운로드
    - S3 버킷 관리 Amazon S3에 저장된 버킷을 생성, 삭제, 나열

    awsCredentials
    - 에쎄스 키와 비밀 에쎼스키 을 저장하고 관리.
    - .withRegion으로 지역도 설정해줌.
     */
    @Bean
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials awsCredentials= new BasicAWSCredentials(accessKey, secretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}