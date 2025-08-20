package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.DTOin.PlanDTO;
import org.example.capstone3.Model.Doctor;
import org.example.capstone3.Model.Patient;
import org.example.capstone3.Model.Plan;
import org.example.capstone3.Repository.DoctorRepository;
import org.example.capstone3.Repository.PatientRepository;
import org.example.capstone3.Repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    //Hussam: fixing
//    public void addPlan(Integer patientId,Integer doctor_id ,PlanDTO planDTO) {
//        Patient patient = patientRepository.findPatientById(patientId);
//        if (patient == null) {
//            throw new ApiException("Patient not found");
//        }
//        Doctor doctor = doctorRepository.findDoctorById(doctor_id);
//        if (doctor == null){
//            throw new ApiException("doctor not found");
//        }
//
//        if (planRepository.existsPlanByDoctor_IdAndPatient_Id(doctor_id, patientId)){
//            throw new ApiException("patient has plan");
//        }
//
//        Plan plan = new Plan(null, planDTO.getName(),planDTO.getDescription(),patient, doctor, new ArrayList<>());
//        planRepository.save(plan);
//
//    }
    //Hussam add
    public void addPlan(Integer patientId, Integer doctorId, PlanDTO planDTO) {
        Patient patient = patientRepository.findPatientById(patientId);
        if (patient == null) {
            throw new ApiException("Patient not found");
        }
        Doctor doctor = doctorRepository.findDoctorById(doctorId);
        if (doctor == null) {
            throw new ApiException("Doctor not found");
        }

        // Prevent duplicate plans
        if (planRepository.existsPlanByDoctor_IdAndPatient_Id(doctorId, patientId)) {
            throw new ApiException("Patient already has a plan from this doctor");
        }
        //******************************************************************************
        //Mohammed fix
        if (!patient.getBooking().get(patient.getBooking().size() - 1).getStatus().equalsIgnoreCase("go-to-train")
                || patient.getBooking().get(patient.getBooking().size() - 1).getStatus() == null
        ) {
            throw new ApiException("Patient does not have a booking");
        }
        //******************************************************************************


        //Hussam add
        Plan plan = new Plan(
                null, // Will be set to patientId because of @MapsId
                planDTO.getName(),
                planDTO.getDescription(),
                patient,
                doctor,
                new ArrayList<>()
        );

        planRepository.save(plan);
    }


    //Hussam fix
    public void updatePlan(Integer patientId, Integer planId, PlanDTO planDTO) {
        Plan oldPlan = planRepository.findPlanById(planId);
        if (oldPlan == null) {
            throw new ApiException("Plan not found");
        }
        Patient patient = patientRepository.findPatientById(patientId);
        if (patient == null) {
            throw new ApiException("Patient not found");
        }
        if (!patient.getPlan().getId().equals(planId)) {
            throw new ApiException("Patient not found");
        }

        //Hussam fix
        oldPlan.setName(planDTO.getName());
        oldPlan.setDescription(planDTO.getDescription());
        //Mohammed fix
        oldPlan.setVideo(new ArrayList<>());
        planRepository.save(oldPlan);
    }


//    public void deletePlan(Integer doctorID,Integer planId) {
//        Plan plan = planRepository.findPlanById(planId);
//        if (plan == null) {
//            throw new ApiException("Plan not found");
//        }
//        Doctor doctor= doctorRepository.findDoctorById(doctorID);
//        if(doctor.getId().equals(plan.getDoctor().getId())) {
//            throw new ApiException("doctor not authorised to change this plan");
//        }
//        planRepository.delete(plan);
//    }

    public void deletePlan(Integer doctorId, Integer planId) {
        Plan plan = planRepository.findPlanById(planId);
        if (plan == null) {
            throw new ApiException("Plan not found");
        }
        //Hussam add
        Doctor doctor = doctorRepository.findDoctorById(doctorId);
        if (doctor == null) {
            throw new ApiException("Doctor not found");
        }

        if (!doctor.getId().equals(plan.getDoctor().getId())) {
            throw new ApiException("Doctor not authorised to delete this plan");
        }

        planRepository.delete(plan);
    }

    //TODO remove this not important it is assigned directly when add
    //Hussam removed this
//    public void assignPatientToPlan(Integer planId, Integer patientId) {
//        Plan plan = planRepository.findPlanById(planId);
//        Patient patient = patientRepository.findPatientById(patientId);
//        if (plan == null || patient == null) {
//            throw new ApiException("Plan or Patient not found");
//        }
//        plan.setPatient(patient);
//        planRepository.save(plan);
//
//
//    }

}
