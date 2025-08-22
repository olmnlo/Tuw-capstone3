package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.DTOin.DoctorDTO;
import org.example.capstone3.DTOin.ScheduleDTO;
import org.example.capstone3.DTOout.DoctorDTOout;
import org.example.capstone3.Model.Doctor;
import org.example.capstone3.Service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Hussam general fix
@RestController
@RequestMapping("/api/v1/doctor")
@RequiredArgsConstructor
//Aziz
public class DoctorController {

    private final DoctorService doctorService;

    //ENDPOINT 7
    @GetMapping
    public ResponseEntity<?> getAllDoctors(){
        return ResponseEntity.status(200).body(doctorService.findAllDoctors());
    }


    //Hussam fix: make it DTO doctor
    //ENDPOINT 8
    @PostMapping
    public ResponseEntity<ApiResponse> addDoctors(@Valid@RequestBody DoctorDTO doctorDTO){
        doctorService.addDoctor(doctorDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Doctor added successfully !"));
    }

    //Hussam fix: make it DTO doctor
    //ENDPOINT 9
    @PutMapping("/{doctor_id}")
    public ResponseEntity<ApiResponse> updateDoctors(@PathVariable Integer doctor_id,@Valid@RequestBody  DoctorDTO doctorDTO){
        doctorService.updateDoctor(doctor_id, doctorDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Doctor updated successfully !"));
    }

    //ENDPOINT 10
    @DeleteMapping("/{doctor_id}")
    public ResponseEntity<ApiResponse> deleteDoctors(@PathVariable Integer doctor_id){
        doctorService.deleteDoctor(doctor_id);
        return ResponseEntity.status(200).body(new ApiResponse("Doctor deleted successfully !"));
    }

    //Hussam
    @GetMapping("/{key}")
    public ResponseEntity<List<DoctorDTOout>> findDoctorByNameContains(@PathVariable String key){
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.searchDoctorByNameContains(key));
    }

    //Aziz
    @GetMapping("/{scheduleDTO}")
    public ResponseEntity<List<DoctorDTOout>> findDoctorBySchedule(@PathVariable ScheduleDTO scheduleDTO){
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.getAvailableDoctors(scheduleDTO));
    }

}
