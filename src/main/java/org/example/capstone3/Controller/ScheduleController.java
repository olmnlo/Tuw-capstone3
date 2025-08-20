package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.DTOin.ScheduleDTO;
import org.example.capstone3.Model.Report;
import org.example.capstone3.Model.Schedule;
import org.example.capstone3.Service.ReportService;
import org.example.capstone3.Service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//Hussam general fix
@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
//Aziz
public class ScheduleController {

    private final ScheduleService scheduleService;

    //ENDPOINT 30
    @GetMapping
    public ResponseEntity<?> getAllSchedules(){
        return ResponseEntity.status(200).body(scheduleService.findAllSchedules());
    }


    //ENDPOINT 31
    @PostMapping
    public ResponseEntity<?> addSchedule(@RequestBody @Valid ScheduleDTO scheduleDTO){
        scheduleService.addSchedule(scheduleDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Schedule added successfully !"));
    }

    //Hussam: fix added path variable
    //ENDPOINT 32
    @PutMapping("/{schedule_id}")
    public ResponseEntity<?> updateSchedule(@PathVariable Integer schedule_id, @RequestBody @Valid ScheduleDTO scheduleDTO){
        scheduleService.updateSchedule(schedule_id, scheduleDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Schedule updated successfully !"));
    }

    //Hussam: fix added path variable
    //ENDPOINT 33
    @DeleteMapping("/{schedule_id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Integer schedule_id){
        scheduleService.deleteSchedule(schedule_id);
        return ResponseEntity.status(200).body(new ApiResponse("Schedule deleted successfully !"));
    }

}
