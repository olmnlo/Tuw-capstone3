package org.example.capstone3.Controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/video")
@RequiredArgsConstructor
public class VideoController {

    // Upload video
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(new ApiResponse("File is empty"));
            }

            // Save the video in a folder
            String uploadDir = "uploads/videos/";
            Path path = Paths.get(uploadDir + file.getOriginalFilename());
            Files.createDirectories(path.getParent()); // ensure directory exists
            Files.write(path, file.getBytes());

            return ResponseEntity.ok("Video uploaded successfully: " + path.toString());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload video: " + e.getMessage());
        }
    }
}
