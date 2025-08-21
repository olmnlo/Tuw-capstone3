package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.DTOout.VideoDTOout;
import org.example.capstone3.Model.Plan;
import org.example.capstone3.Model.Video;
import org.example.capstone3.Repository.DoctorRepository;
import org.example.capstone3.Repository.PatientRepository;
import org.example.capstone3.Repository.PlanRepository;
import org.example.capstone3.Repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


//Hussam created
@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final PlanRepository planRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final VirusScanService virusScanService;


    public List<Video> get(){
        return videoRepository.findAll();
    }

    //Mohammed
    public void saveVideoPatient(Integer patient_Id,Integer plan_id, MultipartFile file) throws Exception {
        Plan plan = planRepository.findPlanById(plan_id);
        if (plan == null){
            throw new ApiException("plan not found");
        }

        try {
            boolean clean = virusScanService.isFileClean(file.getBytes(), file.getOriginalFilename());
            if (!clean) {
                throw new ApiException("file not clean");
            }
        } catch (Exception e) {
            throw new ApiException("error process file scan");
        }

        Video video = new Video();
        video.setFileName(file.getOriginalFilename());
        video.setContentType(file.getContentType());
        video.setVideoType("patient");
        video.setData(file.getBytes());
        video.setPlan(plan);

        plan.getVideo().add(video);
        planRepository.save(plan);


    }
    //Mohammed "Add method to upload video for doctor
    public void saveVideoDoctor(Integer patient_Id,Integer plan_id, MultipartFile file) throws Exception {
        Plan plan = planRepository.findPlanById(plan_id);
        if (plan == null){
            throw new ApiException("plan not found");
        }
        try {
            boolean clean = virusScanService.isFileClean(file.getBytes(), file.getOriginalFilename());
            if (!clean) {
                throw new ApiException("file not clean");
            }
        } catch (Exception e) {
            throw new ApiException("error process file scan");
        }
        Video video = new Video();
        video.setFileName(file.getOriginalFilename());
        video.setContentType(file.getContentType());
        video.setVideoType("doctor");
        video.setData(file.getBytes());
        video.setPlan(plan);

        plan.getVideo().add(video);
        planRepository.save(plan);

    }

    public Video getVideo(Integer videoId) {
        Video video = videoRepository.findVideoById(videoId);
        if (video == null) {
            throw new ApiException("video not found");
        }
        return video;
    }

    public List<VideoDTOout> getVideosData(String videoType, Integer planId, Integer doctorId) {
        List<Video> videos = videoRepository.findVideosByPlanAndDoctorAndType(planId, doctorId, videoType);

        if (videos.isEmpty()) {
            throw new ApiException("video not found");
        }

        return videos.stream()
                .map(v -> new VideoDTOout(v.getId(), v.getFileName(), v.getContentType(), v.getVideoType()))
                .toList();
    }
}
