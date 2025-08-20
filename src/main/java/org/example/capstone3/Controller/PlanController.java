package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.DTOin.PlanDTO;
import org.example.capstone3.Model.Plan;
import org.example.capstone3.Service.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//Hussam General fixies
//Mohammed
@RestController
@RequestMapping("/api/v1/plan")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;
    //ENDPOINT 15
    @GetMapping
    public ResponseEntity<?> getAllPlans(){
        return ResponseEntity.ok().body(planService.getAllPlans());
    }


    //Hussam: make it small case
    //ENDPOINT 16
    @PostMapping("/patient/{patient_id}/doctor/{doctor_id}")
    public ResponseEntity<?> addPlan(@PathVariable Integer patient_id, @PathVariable Integer doctor_id,@RequestBody @Valid PlanDTO planDTO){
        planService.addPlan(patient_id, doctor_id ,planDTO);
        return ResponseEntity.ok().body(new ApiResponse("Course added successfully"));
    }
    //Hussam: make it small case
    //ENDPOINT 17
    @PutMapping("/{plan_id}/doctor/{doctor_id}")
    public ResponseEntity<?> updatePlan(@PathVariable Integer doctor_id,@PathVariable Integer plan_id,@RequestBody @Valid PlanDTO planDTO){
        planService.updatePlan(doctor_id,plan_id, planDTO);
        return ResponseEntity.ok().body(new ApiResponse("plan updated successfully"));
    }
    //Hussam: fixed the missed '}'
    //Hussam: make it small case
    //ENDPOINT 18
    @DeleteMapping("/{plan_id}/doctor/{doctor_id}")
    public ResponseEntity<?> deletePlan(@PathVariable Integer doctor_id,@PathVariable Integer plan_id){
       planService.deletePlan(doctor_id,plan_id);
        return ResponseEntity.ok().body(new ApiResponse("plan deleted successfully"));
    }

}
