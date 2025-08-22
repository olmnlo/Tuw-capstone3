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
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


//Hussam created
@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final PlanRepository planRepository;
    private final VirusScanService virusScanService;
    //-------------------------------------------------------------
    private final String uploadDir = "/home/olmnlo/Videos/";
    //-------------------------------------------------------------


    public List<Video> get(){
        return videoRepository.findAll();
    }

    //Mohammed
    public void saveVideoPatient(Integer patient_Id,Integer plan_id, String description ,MultipartFile file) throws Exception {
        Plan plan = planRepository.findPlanByIdAndPatient_Id(plan_id, patient_Id);
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
        //-------------------------------------------------------------
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = uploadDir + UUID.randomUUID() + "_" + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        //-------------------------------------------------------------

        Video video = new Video();
        video.setFileName(file.getOriginalFilename());
        video.setContentType(file.getContentType());
        video.setVideoType("patient");
        video.setFilePath(filePath);
        video.setDescription(description);
        video.setYoutubeLink("");
        video.setPlan(plan);

        plan.getVideo().add(video);
        planRepository.save(plan);


    }
    //Mohammed "Add method to upload video for doctor
    public void saveVideoDoctor(Integer doctor_id, Integer plan_id, String description, MultipartFile file) throws Exception {
        Plan plan = planRepository.findPlanByIdAndDoctor_Id(plan_id, doctor_id);
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

        //-------------------------------------------------------------
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = uploadDir + UUID.randomUUID() + "_" + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        //-------------------------------------------------------------
        Video video = new Video();
        video.setFileName(file.getOriginalFilename());
        video.setContentType(file.getContentType());
        video.setVideoType("doctor");
        video.setFilePath(filePath);
        video.setDescription(description);
        video.setYoutubeLink("");
        video.setPlan(plan);

        plan.getVideo().add(video);
        planRepository.save(plan);

    }

    public void saveLinkDoctor(Integer doctor_id,Integer plan_id, String youtubeLink, String description) {
        Plan plan = planRepository.findPlanByIdAndDoctor_Id(plan_id, doctor_id);
        if (plan == null){
            throw new ApiException("plan not found");
        }



        Video video = new Video();
        video.setFileName("youtube link");
        video.setContentType("youtube");
        video.setVideoType("doctor");
        video.setFilePath("");
        video.setDescription(description);
        video.setYoutubeLink(youtubeLink);
        video.setPlan(plan);

        plan.getVideo().add(video);
        planRepository.save(plan);

    }

    public Resource getVideo(Integer videoId) throws IOException {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        Path path = Paths.get(video.getFilePath()).toAbsolutePath();
        return new UrlResource(path.toUri()); // This is fine, UrlResource implements Resource

    }

    public List<VideoDTOout> getVideosData(String videoType, Integer planId, Integer doctorId) {
        List<Video> videos = videoRepository.findVideosByPlanAndDoctorAndType(planId, doctorId, videoType);

        if (videos.isEmpty()) {
            throw new ApiException("video not found");
        }

        return videos.stream()
                .map(v -> new VideoDTOout(v.getId(), v.getFileName(), v.getContentType(), v.getVideoType(), v.getDescription(),v.getYoutubeLink()))
                .toList();
    }
}
