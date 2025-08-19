package org.example.capstone3.Service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Answer;
import org.example.capstone3.Model.Doctor;
import org.example.capstone3.Model.Patient;
import org.example.capstone3.Model.Report;
import org.example.capstone3.Repository.DoctorRepository;
import org.example.capstone3.Repository.PatientRepository;
import org.example.capstone3.Repository.ReportRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAiConnect {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1/chat/completions")
            .build();

    //Hussam some fix
    public Report generateReport(Integer patientId, Integer doctor_id) {
        // 1. Fetch patient and doctor
        Patient patient = patientRepository.findPatientById(patientId);
        if (patient == null){
            throw new ApiException("patient not found");
        }
        Doctor doctor = doctorRepository.findDoctorById(doctor_id);
        if (doctor == null){
            throw new ApiException("doctor not found");
        }
        //Hussam some fix
        // 2. Collect answers
        List<Answer> patientAnswers = patient.getAnswers();
        for (Answer a : patientAnswers){
            if (a.getAnswer().isEmpty()){
                throw new ApiException("you must answer all questions");
            }
        }
        String answers = patient.getAnswers().stream()
                .map(q -> q.getQuestion() + ": " + q.getAnswer())
                .reduce("", (a, b) -> a + "\n" + b);
        //Hussam some fix
        // 3. Build prompt
        String prompt = """
                You are a professional physiotherapist in the most famous hospitals in the world. You have specific questions and your patient answers them through answers. You can describe the condition and explain the problem in short summary for other beginner physiotherapist to understand the problem
                This is questions with patient answer:
                \n
                """ + answers;

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
                .retryWhen(
                        Retry.backoff(5, Duration.ofSeconds(5)) // 5 retries, start at 5s
                                .maxBackoff(Duration.ofSeconds(30)) // max wait 30s
                                .filter(ex -> ex instanceof WebClientResponseException.TooManyRequests)
                                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                                        new ApiException("OpenAI rate limit exceeded, please try again later"))
                )
                .block();

        // 5. Create report
        Report generatedReport = new Report(null,summary, LocalDate.now(), patient,doctor);

        // 6. Save
        return generatedReport;
    }
}
