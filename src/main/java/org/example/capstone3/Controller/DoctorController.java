package org.example.capstone3.Controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.Doctor;
import org.example.capstone3.Service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public ResponseEntity<?> getAllDoctors(){
        return ResponseEntity.status(200).body(doctorService.findAllDoctors());
    }


    @PostMapping
    public ResponseEntity<?> addDoctors(Doctor doctor){
        doctorService.addDoctor(doctor);
        return ResponseEntity.status(200).body(new ApiResponse("Doctor added successfully !"));
    }

    @PutMapping
    public ResponseEntity<?> updateDoctors(Integer id, Doctor doctor){
        doctorService.updateDoctor(id, doctor);
        return ResponseEntity.status(200).body(new ApiResponse("Doctor updated successfully !"));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteDoctors(Integer id){
        doctorService.deleteDoctor(id);
        return ResponseEntity.status(200).body(new ApiResponse("Doctor deleted successfully !"));
    }

}
