package org.example.capstone3.Service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Model.Patient;
import org.example.capstone3.Model.Report;
import org.example.capstone3.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAiConnect {

    private final PatientRepository patientRepository;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1/chat/completions")
            .build();


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
        return report;
    }
}
