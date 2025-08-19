package org.example.capstone3.Controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.Report;
import org.example.capstone3.Model.Schedule;
import org.example.capstone3.Service.ReportService;
import org.example.capstone3.Service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<?> getAllSchedules(){
        return ResponseEntity.status(200).body(scheduleService.findAllSchedules());
    }


    @PostMapping
    public ResponseEntity<?> addSchedule(Schedule schedule){
        scheduleService.addSchedule(schedule);
        return ResponseEntity.status(200).body(new ApiResponse("Schedule added successfully !"));
    }

    //Hussam: fix added path variable
    @PutMapping("/{schedule_id}")
    public ResponseEntity<?> updateSchedule(@PathVariable Integer schedule_id, Schedule schedule){
        scheduleService.updateSchedule(schedule_id, schedule);
        return ResponseEntity.status(200).body(new ApiResponse("Schedule updated successfully !"));
    }

    //Hussam: fix added path variable
    @DeleteMapping("/{schedule_id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Integer schedule_id){
        scheduleService.deleteSchedule(schedule_id);
        return ResponseEntity.status(200).body(new ApiResponse("Schedule deleted successfully !"));
    }

}
