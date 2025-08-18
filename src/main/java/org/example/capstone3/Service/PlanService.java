package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Patient;
import org.example.capstone3.Model.Plan;
import org.example.capstone3.Repository.PatientRepository;
import org.example.capstone3.Repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final PatientRepository patientRepository;

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }
    public void addPlan(Plan plan) {
        planRepository.save(plan);
    }
    public void updatePlan(Integer planId,Plan plan) {
        Plan plan1=planRepository.findPlanById(planId);
        if(plan1==null) {
            throw new ApiException("Plan not found");
        }
        plan1.setName(plan.getName());
        plan1.setDescription(plan.getDescription());
        planRepository.save(plan1);
    }
    public void deletePlan(Integer planId) {
        Plan plan=planRepository.findPlanById(planId);
        if(plan==null) {
            throw new ApiException("Plan not found");
        }
        planRepository.delete(plan);
    }

    public void assignPatientToPlan(Integer planId, Integer patientId) {
        Plan plan=planRepository.findPlanById(planId);
        Patient patient=patientRepository.findPatientById(patientId);
        if(plan==null || patient==null) {
            throw new ApiException("Plan or Patient not found");
        }
        patient.setPlan(plan);
        plan.setPatient(patient);
        planRepository.save(plan);
        patientRepository.save(patient);


    }

}
