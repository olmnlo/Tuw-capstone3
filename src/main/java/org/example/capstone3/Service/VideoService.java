//package org.example.capstone3.Service;
//
//import lombok.RequiredArgsConstructor;
//import org.example.capstone3.Api.ApiException;
//import org.example.capstone3.Model.Plan;
//import org.example.capstone3.Model.Video;
//import org.example.capstone3.Repository.VideoRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//@Service
//@RequiredArgsConstructor
//public class VideoService {
//
//    private final VideoRepository videoRepository;
//
//    public Video saveVideo(Integer plan_id, MultipartFile file) throws Exception {
////        Plan plan =
//        Video video = new Video();
//        video.setFileName(file.getOriginalFilename());
//        video.setContentType(file.getContentType());
//        video.setData(file.getBytes());
//        video.getPlan().getVideo().add(video);
//
////        return videoRepository.save(video);
//        return video;
//    }
//
//    public Video getVideo(Integer video_id) {
//        Video video = videoRepository.findVideoById(video_id);
//        if (video == null) {
//            throw new ApiException("video not found");
//        }
//        return video;
//    }
//}
