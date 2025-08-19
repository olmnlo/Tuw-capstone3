package org.example.capstone3.Service;
import jakarta.transaction.Transactional;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Doctor;
import org.example.capstone3.Model.Patient;
import org.example.capstone3.Model.Report;
import org.example.capstone3.Repository.DoctorRepository;
import org.example.capstone3.Repository.PatientRepository;
import org.example.capstone3.Repository.ReportRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
//Aziz
public class ReportService {
    private final ReportRepository reportRepository;

    private final ReportPdfService reportPdfService;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;


    @Value("${openai.api.key}")
    private String openaiApiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1/chat/completions")
         //   .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + System.getenv("OPENAI_API_KEY"))
            .build();



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


    public Report generateReport(Integer patientId) {
        // 1. Fetch patient
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        // 2. Collect answers
        String answers = patient.getQuestion().stream()
                .map(q -> q.getQuestion() + ": " + q.getAnswer())
                .reduce("", (a, b) -> a + "\n" + b);

        // 3. Build prompt
        String prompt = "Summarize the following patient answers into a clear description for a physiotherapist:\n" + answers;

        // 4. Call ChatGPT API
        String summary = webClient.post()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + openaiApiKey)
                .bodyValue(Map.of(
                        "model", "gpt-4o-mini",
                        "messages", List.of(
                                Map.of("role", "system", "content", "You are a medical assistant helping physiotherapists."),
                                Map.of("role", "user", "content", prompt)
                        )
                ))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> json.at("/choices/0/message/content").asText())
                .block();

        // 5. Create report
        Report report = new Report();
        report.setDescription(summary);
        report.setPatient(patient);

        // 6. Save
        return reportRepository.save(report);
    }


    public List<Report> getReportsByPatient(Integer patientId) {
        return reportRepository.findByPatientId(patientId);
    }
}