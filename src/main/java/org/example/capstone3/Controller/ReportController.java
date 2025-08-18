package org.example.capstone3.Controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.Doctor;
import org.example.capstone3.Model.Report;
import org.example.capstone3.Service.DoctorService;
import org.example.capstone3.Service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<?> getAllReports(){
        return ResponseEntity.status(200).body(reportService.findAllReports());
    }


    @PostMapping
    public ResponseEntity<?> addReport(Report report){
        reportService.addReport(report);
        return ResponseEntity.status(200).body(new ApiResponse("Report added successfully !"));
    }

    @PutMapping
    public ResponseEntity<?> updateReport(Integer id, Report report){
        reportService.updateReport(id, report);
        return ResponseEntity.status(200).body(new ApiResponse("Report updated successfully !"));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteReport(Integer id){
        reportService.deleteReport(id);
        return ResponseEntity.status(200).body(new ApiResponse("Report deleted successfully !"));
    }

}
