package com.example.managerteacher.API;

import com.example.managerteacher.entity.Teacher;
import com.example.managerteacher.service.TeacherService;
import com.example.managerteacher.utils.FileUploadUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class TeacherAPI {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/getAllTeacher")
    public ResponseEntity<?> getAllTeacher() {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("success", true);
            map.put("data", teacherService.findAllTeacher());
            map.put("message", "get all teacher successfully");
        } catch (Exception e) {
            map.put("success", false);
            map.put("data", null);
            map.put("message", "get all teacher fail");
            e.printStackTrace();
        }
        return ResponseEntity.ok(map);
    }

    @PostMapping("/saveTeacher")
    public ResponseEntity<?> saveTeacher(@RequestBody Teacher teacher) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("success", true);
            map.put("data", teacherService.insert(teacher));
            map.put("message", "save teacher successfully");
        } catch (Exception e) {
            map.put("success", false);
            map.put("data", null);
            map.put("message", "save teacher fail");
            e.printStackTrace();
        }
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/deleteTeacher")
    public ResponseEntity<?> deleteTeacher(@RequestParam String id) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("success", true);
            map.put("data", teacherService.delete(id));
            map.put("message", "delete teacher successfully");
        } catch (Exception e) {
            map.put("success", false);
            map.put("data", null);
            map.put("message", "delete teacher fail");
            e.printStackTrace();
        }
        return ResponseEntity.ok(map);
    }

    @PutMapping("/updateTeacher")
    public ResponseEntity<?> updateTeacher(@RequestBody Teacher teacher) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("success", true);
            map.put("data", teacherService.update(teacher));
            map.put("message", "update teacher successfully");
        } catch (Exception e) {
            map.put("success", false);
            map.put("data", null);
            map.put("message", "update teacher fail");
            e.printStackTrace();
        }
        return ResponseEntity.ok(map);
    }


    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file selected");
        }

        try {
            // Đường dẫn lưu trữ tệp
            String uploadDir = "D:/Project_ThayLiem/UI/Sreening/public/Image/";
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs(); // Tạo thư mục nếu chưa tồn tại
            }

            // Lưu tệp vào đường dẫn chỉ định
            File destinationFile = new File(uploadDir + file.getOriginalFilename());
            file.transferTo(destinationFile);

            return ResponseEntity.ok().body(Map.of("success", true, "data", file.getOriginalFilename()));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to store file", e);
        }
    }
}
