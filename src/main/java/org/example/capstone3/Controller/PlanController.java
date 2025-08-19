package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.Plan;
import org.example.capstone3.Service.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//Hussam General fixies
//Mohammed
@RestController
@RequestMapping("/api/v1/plan")
public class PlanController {

    PlanService planService;
    @GetMapping
    public ResponseEntity<?> getAllPlans(){
        return ResponseEntity.ok().body(planService.getAllPlans());
    }


    //Hussam: make it small case
    @PostMapping("/add/{patientId}")
    public ResponseEntity<?> addPlan(@PathVariable Integer patientId,@RequestBody @Valid Plan plan){
        planService.addPlan(patientId,plan);
        return ResponseEntity.ok().body(new ApiResponse("Course added successfully"));
    }
    //Hussam: make it small case
    @PutMapping("/{plan_id}/doctor/{doctor_id}")
    public ResponseEntity<?> updatePlan(@PathVariable Integer doctor_id,@PathVariable Integer plan_id,@RequestBody @Valid Plan plan){
        planService.updatePlan(doctor_id,plan_id, plan);
        return ResponseEntity.ok().body(new ApiResponse("plan updated successfully"));
    }
    //Hussam: fixed the missed '}'
    //Hussam: make it small case
    @DeleteMapping("/{plan_id}/doctor/{doctor_id}")
    public ResponseEntity<?> deletePlan(@PathVariable Integer doctor_id,@PathVariable Integer plan_id){
       planService.deletePlan(doctor_id,plan_id);
        return ResponseEntity.ok().body(new ApiResponse("plan deleted successfully"));
    }
    //Hussam: make it small case
    @PutMapping("/{plan_id}/assign/{patientId}")
    public ResponseEntity<?> assignPlanToPatient(@PathVariable Integer plan_id, @PathVariable Integer patientId) {
        planService.assignPatientToPlan(plan_id, patientId);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully assigned plan to patient"));
    }

}
