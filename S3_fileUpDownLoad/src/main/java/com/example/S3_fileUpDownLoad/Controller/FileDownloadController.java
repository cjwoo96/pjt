package com.example.S3_fileUpDownLoad.Controller;

import com.example.S3_fileUpDownLoad.Service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/S3")
public class FileDownloadController {
    private final FileService fileService;

    @Autowired
    public FileDownloadController(FileService fileService) {
        this.fileService = fileService;
    }
    @GetMapping("/download")
    public ResponseEntity<String> downloadFile(){
        String bucketName = "a305-project-bucket";
        String fileKey="check/KakaoTalk_20240123_124145653.mp4";
        String localFilePath="C:\\Users\\SSAFY\\kk\\n.mp4";
        try{
            fileService.downloadFile(bucketName,fileKey,localFilePath);
            return ResponseEntity.ok("파일이 성공적으로 다운되었습니다");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("에러 터짐");
        }

    }
/*
    public ResponseEntity<String> downloadFile(String bucketName,String fileKey,String localFilePath){
        try{
            fileService.downloadFile(bucketName,fileKey,localFilePath);
            ResponseEntity.ok("파일이 성공적으로 다운되었습니다");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("에러 터짐");
        }
    }

 */
}
