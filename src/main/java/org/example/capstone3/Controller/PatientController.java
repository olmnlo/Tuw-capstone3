package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.DTO.PatientDTO;
import org.example.capstone3.Model.Patient;
import org.example.capstone3.Service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
