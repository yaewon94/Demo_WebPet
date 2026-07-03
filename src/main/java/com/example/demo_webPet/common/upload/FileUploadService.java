package com.example.demo_webPet.common.upload;

import com.example.demo_webPet.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class FileUploadService {

    private static final long MAX_FILE_SIZE = FileConstants.MAX_FILE_SIZE_GB * 1024 * 1024;

    ImageUploadResponse uploadImage(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new FileUploadException(ErrorCode.ERROR_FILE_REQUIRED);
        }

        // 파일타입 체크
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new FileUploadException(ErrorCode.ERROR_ONLY_IMAGE_FILE_REQUIRED);
        }

        // 파일크기 체크
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new FileUploadException(ErrorCode.ERROR_ONLY_IMAGE_FILE_REQUIRED);
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(FileConstants.UPLOAD_DIR + fileName);
        file.transferTo(path);

        return new ImageUploadResponse(FileConstants.UPLOAD_URI + fileName);
    }
}
