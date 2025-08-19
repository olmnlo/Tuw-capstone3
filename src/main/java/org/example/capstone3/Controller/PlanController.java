package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.Plan;
import org.example.capstone3.Service.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//Mohammed
@RestController
@RequestMapping("/api/v1/PLAN")
public class PlanController {

    PlanService planService;
    @GetMapping
    public ResponseEntity<?> getAllPlans(){
        return ResponseEntity.ok().body(planService.getAllPlans());
    }


    @PostMapping("/ADD/{patientId}")
    public ResponseEntity<?> addPlan(@PathVariable Integer patientId,@RequestBody @Valid Plan plan){
        planService.addPlan(patientId,plan);
        return ResponseEntity.ok().body(new ApiResponse("Course added successfully"));
    }
    @PutMapping("/UPDATE/{doctorId}/{planId}")
    public ResponseEntity<?> updatePlan(@PathVariable Integer doctorId,@PathVariable Integer planId,@RequestBody @Valid Plan plan){
        planService.updatePlan(doctorId,planId, plan);
        return ResponseEntity.ok().body(new ApiResponse("plan updated successfully"));
    }
    //Hussam : fixed the missed '}'
    @DeleteMapping("/DELETE/{doctorId}/{planId}")
    public ResponseEntity<?> deletePlan(@PathVariable Integer doctorId,@PathVariable Integer planId){
       planService.deletePlan(doctorId,planId);
        return ResponseEntity.ok().body(new ApiResponse("plan deleted successfully"));
    }
    @PutMapping("/ASSIGN/PLAN/{planId}/{patientId}")
    public ResponseEntity<?> assignPlanToPatient(@PathVariable Integer planId, @PathVariable Integer patientId) {
        planService.assignPatientToPlan(planId, patientId);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully assigned plan to patient"));
    }

}
