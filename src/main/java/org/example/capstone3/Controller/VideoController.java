package org.example.capstone3.Controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.DTOout.VideoDTOout;
import org.example.capstone3.Model.Video;
import org.example.capstone3.Service.VideoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//Hussam created
@RestController
@RequestMapping("/api/v1/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;


    //ENDPOINT 34
    @GetMapping("/{video_id}")
    public ResponseEntity<byte[]> getVideoById(@PathVariable Integer video_id) {
        Video video = videoService.getVideo(video_id);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(video.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + video.getFileName() + "\"")
                .body(video.getData());
    }


    //Mohammed add end point to user to upload vid
    @PostMapping("/upload/{plan_id}/patient/{patient_Id}")
    public ResponseEntity<ApiResponse> uploadVideoForPatient(@PathVariable Integer patient_Id, @PathVariable Integer plan_id, @RequestParam("file") MultipartFile file) throws Exception {
        videoService.saveVideoPatient(patient_Id, plan_id, file);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("video uploaded successfully"));

    }

    //Mohammed adding end point to doctor to upload vid
    @PostMapping("/upload/{plan_id}/doctor/{doctor_Id}")
    public ResponseEntity<ApiResponse> uploadVideoForDoctor(@PathVariable Integer doctor_Id, @PathVariable Integer plan_id, @RequestParam("file") MultipartFile file) throws Exception {
        videoService.saveVideoDoctor(doctor_Id, plan_id, file);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("video uploaded successfully"));
    }

    @GetMapping("/data/{doctor_id}/plan/{plan_id}/video/{video_type}")
    public ResponseEntity<List<VideoDTOout>> getVideoData(@PathVariable String video_type,@PathVariable Integer plan_id,@PathVariable Integer doctor_id){
        return ResponseEntity.status(HttpStatus.OK).body(videoService.getVideosData(video_type,plan_id,doctor_id));
    }

    @GetMapping
    public ResponseEntity<?> getVideoData(){
        return ResponseEntity.status(HttpStatus.OK).body(videoService.get());
    }



    //mm

}