package com.david.cv_screener_backend.service;


import com.david.cv_screener_backend.dto.AnalysisResponse;
import com.david.cv_screener_backend.dto.AnalysisResponse.SkillDTO;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class CvAnalysisService {

    private final WebClient webClient;

    public CvAnalysisService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8000").build(); // adjust URL if needed
    }

    public AnalysisResponse analyzeCv(MultipartFile file, String name, String email) throws IOException {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", file.getResource());
        bodyBuilder.part("candidate_name", name);
        bodyBuilder.part("email", email);

        MultiValueMap<String, HttpEntity<?>> multipartBody = bodyBuilder.build();

        return webClient.post()
                .uri("/analyze")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(multipartBody))
                .retrieve()
                .bodyToMono(AnalysisResponse.class)
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.empty();
                })
                .block();
    }
}
