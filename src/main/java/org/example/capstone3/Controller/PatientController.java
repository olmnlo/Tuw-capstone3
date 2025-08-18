package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.DTO.PatientDTO;
import org.example.capstone3.Model.Patient;
import org.example.capstone3.Service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    //Hussam
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients(){
        return ResponseEntity.status(HttpStatus.OK).body(patientService.findAllPatient());
    }

    //Hussam
    @PostMapping
    public ResponseEntity<ApiResponse> addNewPatient(@Valid@RequestBody PatientDTO patientDTO){
        patientService.addPatient(patientDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("patient added successfully"));
    }


    @PutMapping("/{patient_id}")
    public ResponseEntity<ApiResponse> updatePatient(@PathVariable Integer patient_id,@Valid@RequestBody PatientDTO patientDTO){
        patientService.updatePatient(patient_id, patientDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("patient updated successfully"));
    }

    @DeleteMapping("/{patient_id}")
    public ResponseEntity<ApiResponse> deletePatient(@PathVariable Integer patient_id){
        patientService.deletePatient(patient_id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("patient deleted successfully"));
    }


}
