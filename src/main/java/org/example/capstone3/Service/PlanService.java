package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Doctor;
import org.example.capstone3.Model.Patient;
import org.example.capstone3.Model.Plan;
import org.example.capstone3.Repository.DoctorRepository;
import org.example.capstone3.Repository.PatientRepository;
import org.example.capstone3.Repository.PlanRepository;
import org.springframework.stereotype.Service;

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

    public void addPlan(Integer patientId, Plan plan) {
        Patient patient = patientRepository.findPatientById(patientId);
        if (patient == null) {
            throw new ApiException("Patient not found");
        }

        throw new ApiException("Plan already exists");
    }

    public void updatePlan(Integer patientId, Integer planId, Plan plan) {
        Plan plan1 = planRepository.findPlanById(planId);
        if (plan1 == null) {
            throw new ApiException("Plan not found");
        }
        Patient patient = patientRepository.findPatientById(patientId);
        if (patient == null) {
            throw new ApiException("Patient not found");
        }
        if (!patient.getPlan().getId().equals(planId)) {
            throw new ApiException("Patient not found");
        }


        plan1.setDescription(plan.getDescription());
        planRepository.save(plan1);
    }

    public void deletePlan(Integer doctorID,Integer planId) {
        Plan plan = planRepository.findPlanById(planId);
        if (plan == null) {
            throw new ApiException("Plan not found");
        }
        Doctor doctor= doctorRepository.findDoctorById(doctorID);
        if(doctor.getId().equals(plan.getDoctor().getId())) {}
        planRepository.delete(plan);
    }

    public void assignPatientToPlan(Integer planId, Integer patientId) {
        Plan plan = planRepository.findPlanById(planId);
        Patient patient = patientRepository.findPatientById(patientId);
        if (plan == null || patient == null) {
            throw new ApiException("Plan or Patient not found");
        }
        patient.setPlan(plan);
        plan.setPatient(patient);
        planRepository.save(plan);
        patientRepository.save(patient);


    }

}
