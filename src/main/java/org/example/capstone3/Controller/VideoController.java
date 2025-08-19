package org.example.capstone3.Controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.Video;
import org.example.capstone3.Service.VideoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//Hussam created
@RestController
@RequestMapping("/api/v1/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;


    @GetMapping("/{video_id}")
    public ResponseEntity<byte[]> getVideoById(@PathVariable Integer video_id){
        Video video = videoService.getVideo(video_id);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(video.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + video.getFileName() + "\"")
                .body(video.getData());
    }


    @PostMapping("/upload/{plan_id}")
    public ResponseEntity<ApiResponse> uploadVideo(@PathVariable Integer plan_id, @RequestParam("file") MultipartFile file) throws Exception {
        videoService.saveVideo(plan_id,file);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("video uploaded successfully"));

    }
}
