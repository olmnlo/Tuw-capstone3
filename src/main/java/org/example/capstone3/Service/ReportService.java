package org.example.capstone3.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Doctor;
import org.example.capstone3.Model.Patient;
import org.example.capstone3.Model.Report;
import org.example.capstone3.Repository.DoctorRepository;
import org.example.capstone3.Repository.PatientRepository;
import org.example.capstone3.Repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
//Aziz
public class ReportService {
    private final ReportRepository reportRepository;
    private final ReportPdfService reportPdfService;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public List<Report> findAllReports(){
        List<Report> reports = reportRepository.findAll();
        if (reports.isEmpty()){
            throw new ApiException("no report found !");
        }
        return reports;
    }


    public void addReport(Report report){
        reportRepository.save(report);
    }

    public void updateReport(Integer id, Report report){
        Report oldReport = reportRepository.findReportById(id);

        if (oldReport == null){
            throw new ApiException("no report found !");
        }


        reportRepository.save(oldReport);
    }

    public void deleteReport(Integer id){
        Report report = reportRepository.findReportById(id);

        if (report == null){
            throw new ApiException("no report found !");
        }

        reportRepository.delete(report);
    }


    //Mohammed
    @Transactional  // أو @Transactional(readOnly = true)
    public byte[] generatePdfReport(Integer patientId, Integer doctorId, Integer reportId){
        Report report = reportRepository.findReportById(reportId);
        if (report == null) throw new ApiException("report not found!");

        if (report.getPatient() == null || report.getDoctor() == null
                || !report.getPatient().getId().equals(patientId)
                || !report.getDoctor().getId().equals(doctorId)) {
            throw new ApiException("report does not belong to given patient/doctor");
        }

        return reportPdfService.generate(report);
    }



}