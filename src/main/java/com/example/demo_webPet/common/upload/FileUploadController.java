package com.example.demo_webPet.common.upload;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

// TODO : 대량요청, 핫링크 등 악의적 이용 방지 코드 추가
@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/upload/image")
    @ResponseBody
    public ImageUploadResponse uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        return fileUploadService.uploadImage(file);
    }
}
