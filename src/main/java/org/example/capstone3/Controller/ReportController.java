package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.DTOin.ReportDTO;
import org.example.capstone3.Model.Doctor;
import org.example.capstone3.Model.Report;
import org.example.capstone3.Service.DoctorService;
import org.example.capstone3.Service.ReportService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//Hussam general fix
@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
//Aziz
public class ReportController {

    private final ReportService reportService;

    //ENDPOINT 23
    @GetMapping
    public ResponseEntity<?> getAllReports(){
        return ResponseEntity.status(200).body(reportService.findAllReports());
    }



    //Mohammed "Add RequistBody And Add Valid
    //ENDPOINT 24
    @PostMapping("/add/patient")
    public ResponseEntity<ApiResponse> addReport(@RequestBody@Valid ReportDTO reportDTO){
        reportService.addReport(reportDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Report added successfully !"));
    }

    //Hussam: fix make path variable
    //Mohammed "Add RequistBody And Add Valid
    //ENDPOINT 25
    @PutMapping("/{report_id}")
    public ResponseEntity<ApiResponse> updateReport(@PathVariable Integer report_id,@RequestBody @Valid ReportDTO reportDTO){
        reportService.updateReport(report_id, reportDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Report updated successfully !"));
    }

    //Hussam: fix make path variable
    //ENDPOINT 26
    @DeleteMapping("/{report_id}")
    public ResponseEntity<ApiResponse> deleteReport(@PathVariable Integer report_id){
        reportService.deleteReport(report_id);
        return ResponseEntity.status(200).body(new ApiResponse("Report deleted successfully !"));
    }


    //Mohammed
    //ENDPOINT 27
    @GetMapping(
            value = "/Patient/{patientId}/Doctor/{doctorId}/pdf",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> generatePdfReport(@PathVariable Integer patientId,
                                                    @PathVariable Integer doctorId,
                                                    @RequestParam(defaultValue = "attachment") String disp) {

        byte[] pdf = reportService.generatePdfReport(patientId, doctorId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition.builder(disp).filename("report-" + patientId + ".pdf").build()
        );

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
  }

    // Get all reports for a patient (for physiotherapist view)
    //ENDPOINT 28
    @GetMapping("/patient/{patientId}/reports")
    public ResponseEntity<List<Report>> getReportsByPatient(@PathVariable Integer patientId) {
        List<Report> reports = reportService.getReportsByPatient(patientId);
        return ResponseEntity.ok(reports);
    }

    //ENDPOINT 29

    @PostMapping("/generate-report-ai/patient/{patient_id}/doctor/{doctor_id}")
    public ResponseEntity<ApiResponse> generateReportAi(@PathVariable Integer patient_id, @PathVariable Integer doctor_id){
        reportService.generateReport(patient_id, doctor_id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("report generated successfully"));
    }

    

}
