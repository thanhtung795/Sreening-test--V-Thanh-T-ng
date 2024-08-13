package com.example.managerteacher.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploadUtils {

    private static final String UPLOAD_DIR = "D:/Project_ThayLiem/UI/Sreening/public/Image";

    public static String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File không hợp lệ.");
        }

        // Tạo đường dẫn lưu tệp
        Path path = Paths.get(UPLOAD_DIR + File.separator + file.getOriginalFilename());

        // Tạo thư mục nếu chưa tồn tại
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Lưu tệp vào thư mục
        Files.write(path, file.getBytes());

        return file.getOriginalFilename();
    }
}
