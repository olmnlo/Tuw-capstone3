package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.Doctor;
import org.example.capstone3.Model.Report;
import org.example.capstone3.Service.DoctorService;
import org.example.capstone3.Service.ReportService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
//Aziz
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<?> getAllReports(){
        return ResponseEntity.status(200).body(reportService.findAllReports());
    }



    //Mohammed "Add RequistBody And Add Valid
    @PostMapping("/add/patient")
    public ResponseEntity<ApiResponse> addReport(@RequestBody@Valid Report report){
        reportService.addReport(report);
        return ResponseEntity.status(200).body(new ApiResponse("Report added successfully !"));
    }

    //Hussam: fix make path variable
    //Mohammed "Add RequistBody And Add Valid
    @PutMapping("/{report_id}")
    public ResponseEntity<ApiResponse> updateReport(@PathVariable Integer report_id,@RequestBody @Valid Report report){
        reportService.updateReport(report_id, report);
        return ResponseEntity.status(200).body(new ApiResponse("Report updated successfully !"));
    }

    //Hussam: fix make path variable
    @DeleteMapping("/{report_id}")
    public ResponseEntity<ApiResponse> deleteReport(@PathVariable Integer report_id){
        reportService.deleteReport(report_id);
        return ResponseEntity.status(200).body(new ApiResponse("Report deleted successfully !"));
    }


    //Mohammed
    @GetMapping(
            value = "/Patient/{patientId}/Doctor/{doctorId}/Report/{reportId}/pdf",


            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> generatePdfReport(@PathVariable Integer patientId,
                                                    @PathVariable Integer doctorId,
                                                    @PathVariable Integer reportId,
                                                    @RequestParam(defaultValue = "attachment") String disp) {

        byte[] pdf = reportService.generatePdfReport(patientId, doctorId, reportId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition.builder(disp).filename("report-" + reportId + ".pdf").build()
        );

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
  }


    // Generate a new report from patient answers
    @PostMapping("/patient/{patientId}")
    public ResponseEntity<Report> generateReport(@PathVariable Integer patientId) {
        Report report = reportService.generateReport(patientId);
        return ResponseEntity.ok(report);
    }

    // Get all reports for a patient (for physiotherapist view)
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Report>> getReportsByPatient(@PathVariable Integer patientId) {
        List<Report> reports = reportService.getReportsByPatient(patientId);
        return ResponseEntity.ok(reports);
    }
    

}
