package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.Plan;
import org.example.capstone3.Service.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/PLAN")
public class PlanController {

    PlanService planService;
    @GetMapping
    public ResponseEntity<?> getAllPlans(){
        return ResponseEntity.ok().body(planService.getAllPlans());
    }


    @PostMapping("/ADD")
    public ResponseEntity<?> addPlan(@RequestBody @Valid Plan plan){
        planService.addPlan(plan);
        return ResponseEntity.ok().body(new ApiResponse("Course added successfully"));
    }
    @PutMapping("/UPDATE/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Integer id,@RequestBody @Valid Plan plan){
        planService.updatePlan(id, plan);
        return ResponseEntity.ok().body(new ApiResponse("plan updated successfully"));
    }
    @DeleteMapping("/DELETE/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer id){
       planService.deletePlan(id);
        return ResponseEntity.ok().body(new ApiResponse("plan deleted successfully"));
    }
    @PutMapping("/ASSIGN/PLAN/{planId}/{patientId}")
    public ResponseEntity<?> assignTeacherToCourse(@PathVariable Integer planId, @PathVariable Integer patientId) {
        planService.assignPatientToPlan(planId, patientId);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully assigned plan to patient"));
    }

}
