package com.example.S3_fileUpDownLoad.Controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.S3_fileUpDownLoad.Service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
// /upload 요청을 여기서 처리.
@RequestMapping("/S3")
// Lombok 라이브러리 어노테이션, final 필드에 대한 생성자 자동 생성.
// ? 생성자를 생성해줘야 하나??
@RequiredArgsConstructor
public class FileUploadController {

    private final FileService fileService;
    // AmazonS3Client를 주입받는 필드.
//    private final AmazonS3Client amazonS3Client;
    // propertie yml파일로부터 값을 받아옴
    // ? 근데 어떻게 알아서 application.yml 파일에 접근해??
//    @Value("${cloud.aws.s3.bucket}")
//    // S3 버킷이름 저장하는 필드
//    private String bucket;

//    @Value("${cloud.aws.s3.region.static}")
//    private String region;
    /* 요청을 받아 파일을 처리할때는 MultipartFile을 사용한다.
       /upload라는 url을 통한 요청을 받았기에 MultipartFile 사용.
    */
    @PostMapping("/upload") // /upload로 매핑. 동일 경로있으면 좀 더 상세하게 매핑.
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        /*
        @RequestParam("file")
        이 부분 내가 동영상 파일, 이미지 파일, 압축 파일 등 다양한 파일을 업로드 요청 할 수 있는데
        file이라는 이름으로 요청을 한다면 zip이든 이미지든 동영상이든 파일에 대한 상세정보가 MultipartFile에 저장됨.
        */

//        String fileName=file.getOriginalFilename(); // 파일의 이름 가져오기
//        String fileKey="test/"+fileName;
//        String fileUrl= "https://" + bucket + ".s3."+region+".amazonaws.com/" + fileKey;
        /*
        나는 내가 지정한 버킷에다가 저장할꺼야.
        근데 실제 버킷에다가 경로를 지정하면 https://+버킷이름+.지역.+amazonaaws.com+/~ 식으로 가는거아냐??
        /test처럼 내가 임의로 설정해도 되는지 모르곘어
         */
//        ObjectMetadata metadata= new ObjectMetadata();
//        metadata.setContentType(file.getContentType());
//        metadata.setContentLength(file.getSize());
        // 메타데이터 더 가져올 건 없어??
//        amazonS3Client.putObject(bucket,fileKey,file.getInputStream(),metadata);
        String filekey = fileService.uploadFile(file,"check");
        return ResponseEntity.ok(filekey);
    }

}