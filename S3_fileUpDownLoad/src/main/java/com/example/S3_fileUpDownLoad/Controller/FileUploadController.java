package com.example.S3_fileUpDownLoad.Controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.S3_fileUpDownLoad.Service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
// /upload 요청을 여기서 처리.
@RequestMapping("/S3")
public class FileUploadController {

    private final FileService fileService;
    @Autowired
    public FileUploadController(FileService fileService) {
        this.fileService = fileService;
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String filekey = fileService.uploadFile(file,"testing");
        return ResponseEntity.ok(filekey);
    }


    //curl -X PUT -T "C:\Users\SSAFY\kk\n.mp4"(Local Path) "생성된 URL"
    @GetMapping("/generate-url")
    public ResponseEntity<String> generatePresignedUrl(@RequestParam String fileKey) {
        long durationMillis = 100000 * 60; // 예: 30초
        URL url = fileService.generatePresignedUrl(fileKey, durationMillis);
        return ResponseEntity.ok(url.toString());
    }
}