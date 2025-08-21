package org.example.capstone3.Service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class VirusScanService {
    private static final String VIRUSTOTAL_API_KEY = "";
    private static final String VIRUSTOTAL_URL = "https://www.virustotal.com/api/v3/files";

    private final WebClient webClient;

    public VirusScanService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl(VIRUSTOTAL_URL).build();
    }

    public boolean isFileClean(byte[] fileBytes, String filename) throws Exception {
        if (fileBytes.length > 32 * 1024 * 1024) { // 32MB limit for free API
            throw new IllegalArgumentException("File too large for VirusTotal scan");
        }

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", new ByteArrayResource(fileBytes) {
            @Override
            public String getFilename() {
                return filename;
            }
        });

        Map<String, Object> response = webClient.post()
                .header("x-apikey", VIRUSTOTAL_API_KEY)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(builder.build())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        // Parse response
        if (response == null || !response.containsKey("data")) {
            throw new Exception("VirusTotal scan failed");
        }

        Map<String, Object> data = (Map<String, Object>) response.get("data");
        Map<String, Object> attributes = (Map<String, Object>) data.get("attributes");
        Map<String, Object> lastAnalysisStats = (Map<String, Object>) attributes.get("last_analysis_stats");

        // If any engine detected a threat, mark as unsafe
        int malicious = (int) lastAnalysisStats.getOrDefault("malicious", 0);
        return malicious == 0;
    }
}