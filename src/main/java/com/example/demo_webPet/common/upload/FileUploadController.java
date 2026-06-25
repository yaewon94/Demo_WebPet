package com.example.demo_webPet.common.upload;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/upload/image")
    @ResponseBody
    public ImageUploadResponse uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        // TODO : 직접 URL 쳐서 들어올 경우 막아버리기
        return fileUploadService.uploadImage(file);
    }
}
