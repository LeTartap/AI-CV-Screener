package com.david.cv_screener_backend.controller;

import com.david.cv_screener_backend.dto.AnalysisResponse;
import com.david.cv_screener_backend.model.Candidate;
import com.david.cv_screener_backend.service.CandidateSaveService;
import com.david.cv_screener_backend.service.CvAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    private final CvAnalysisService analysisService;
    private final CandidateSaveService saveService;

    public CandidateController(CvAnalysisService analysisService, CandidateSaveService saveService) {
        this.analysisService = analysisService;
        this.saveService = saveService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Candidate> uploadCv(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("email") String email) throws IOException {

        // 1️⃣ Save CV locally (optional, for reference)
        Path uploadDir = Path.of("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        Path filePath = uploadDir.resolve(file.getOriginalFilename());
        Files.write(filePath, file.getBytes());

        // 2️⃣ Call Python microservice for analysis
        AnalysisResponse analysis = analysisService.analyzeCv(file, name, email);

        // 3️⃣ Save results to PostgreSQL
        Candidate savedCandidate = saveService.saveFromAnalysis(
                analysis,
                email,
                filePath.toString() // store path in DB
        );

        // 4️⃣ Return saved entity
        return ResponseEntity.ok(savedCandidate);
    }
    @GetMapping("/ranking")
    public ResponseEntity<List<Candidate>> getRanking() {
        List<Candidate> ranking = saveService.getRanking();
        return ResponseEntity.ok(ranking);
    }


}
