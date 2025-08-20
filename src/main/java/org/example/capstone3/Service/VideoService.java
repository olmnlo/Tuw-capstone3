package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Plan;
import org.example.capstone3.Model.Video;
import org.example.capstone3.Repository.DoctorRepository;
import org.example.capstone3.Repository.PatientRepository;
import org.example.capstone3.Repository.PlanRepository;
import org.example.capstone3.Repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
//Hussam created
@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final PlanRepository planRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    //Mohammed
    public void saveVideoPatient(Integer patient_Id,Integer plan_id, MultipartFile file) throws Exception {
        Plan plan = planRepository.findPlanById(plan_id);
        if (plan == null){
            throw new ApiException("plan not found");
        }
        Video video = new Video();
        video.setFileName(file.getOriginalFilename());
        video.setContentType(file.getContentType());
        video.setData(file.getBytes());

        plan.getPatientVideo().add(video);
        planRepository.save(plan);


    }
    //Mohammed "Add method to upload video for doctor
    public void saveVideoDoctor(Integer patient_Id,Integer plan_id, MultipartFile file) throws Exception {
        Plan plan = planRepository.findPlanById(plan_id);
        if (plan == null){
            throw new ApiException("plan not found");
        }
        Video video = new Video();
        video.setFileName(file.getOriginalFilename());
        video.setContentType(file.getContentType());
        video.setData(file.getBytes());

        plan.getDoctorVideo().add(video);
        planRepository.save(plan);

    }

    public Video getVideo(Integer video_id) {
        Video video = videoRepository.findVideoById(video_id);
        if (video == null) {
            throw new ApiException("video not found");
        }
        return video;
    }
}
