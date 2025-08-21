package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.DTOin.PatientDTO;
import org.example.capstone3.Model.Patient;
import org.example.capstone3.Service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//Hussam created
@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    //Hussam
    //ENDPOINT 11
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients(){
        return ResponseEntity.status(HttpStatus.OK).body(patientService.findAllPatient());
    }


    //Hussam
    //ENDPOINT 12
    @PostMapping
    public ResponseEntity<ApiResponse> addNewPatient(@Valid@RequestBody PatientDTO patientDTO){
        patientService.addPatient(patientDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("patient added successfully"));
    }

    //Hussam
    //ENDPOINT 13
    @PutMapping("/{patient_id}")
    public ResponseEntity<ApiResponse> updatePatient(@PathVariable Integer patient_id,@Valid@RequestBody PatientDTO patientDTO){
        patientService.updatePatient(patient_id, patientDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("patient updated successfully"));
    }

    //Hussam
    //ENDPOINT 14
    @DeleteMapping("/{patient_id}")
    public ResponseEntity<ApiResponse> deletePatient(@PathVariable Integer patient_id){
        patientService.deletePatient(patient_id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("patient deleted successfully"));
    }

    //Mohammed
    @GetMapping("/{patient_id}/doctor/{doctor_id}/request-report")
    public ResponseEntity<ApiResponse> sendPdfReportToPatient(
            @PathVariable Integer patient_id,
            @PathVariable Integer doctor_id) {
        ApiResponse response = patientService.sendPdfReportToPatient(patient_id, doctor_id);
        return ResponseEntity.ok(response);
    }



  
}
